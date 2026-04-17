package com.aml.app.config.errorManager;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.aml.app.config.response.ApiResponse;
import com.aml.app.security.exceptions.EmpresaBloqueada;
import com.aml.app.security.exceptions.UsuarioBloqueado;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
        @Value("${app.debug:false}")
        private boolean debugMode;

        /**
         * errores lanzados por la aplicación (negocio), creramos estas excepciones
         * manualmente
         */
        @ExceptionHandler(RiseException.class)
        public ResponseEntity<ApiResponse<Void>> handleBusiness(RiseException ex) {
                return ResponseEntity.status(ex.getError().getStatus())
                                .body(ApiResponse.error(
                                                ex.getMessage(),
                                                ex.getError().name(),
                                                ex.getError().getStatus().value(),
                                                null));
        }

        /**
         * errores de validacion con @Valid en los controladores
         */
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(MethodArgumentNotValidException ex) {
                Map<String, String> errors = new HashMap<>();
                ex.getBindingResult().getFieldErrors().forEach(f -> errors.put(f.getField(), f.getDefaultMessage()));

                return ResponseEntity.badRequest().body(
                                ApiResponse.<Map<String, String>>builder()
                                                .success(false)
                                                .message("Error en los campos enviados")
                                                .code(ErrorCatalog.INVALID_PARAMS.name())
                                                .status(400)
                                                .data(errors)
                                                .timestamp(LocalDateTime.now())
                                                .build());
        }

        /**
         * errores de integridad de datos, como violaciones de clave única o
         * restricciones de base de datos
         */
        @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
        public ResponseEntity<ApiResponse<Void>> handleDataIntegrity(Exception ex) {
                log.error("Integrity Violation: ", ex);
                return ResponseEntity.status(409).body(
                                ApiResponse.error("El registro ya existe o viola una restricción",
                                                ErrorCatalog.DATA_INTEGRITY_VIOLATION.name(), 409,
                                                debugMode ? ex.getMessage() : null));
        }

        /**
         * errores no manejados, para capturar cualquier excepción que no hayamos
         * previsto
         */
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiResponse<Void>> handleAll(Exception ex) {
                // 1. Buscamos la causa raíz real (pela las capas de Spring)
                Throwable rootCause = getRootCause(ex);

                // 2. Resolvemos el Status HTTP (401, 403, 500, etc.)
                HttpStatus status = resolveHttpStatus(rootCause);

                // 3. Generamos el código (ej: EMPRESA_BLOQUEADA)
                String errorCode = resolveErrorCode(rootCause);

                log.error("Managed Exception [{}]: {}", errorCode, rootCause.getMessage());

                String technicalDetail = rootCause.getClass().getSimpleName();

                return ResponseEntity.status(status).body(
                                ApiResponse.error(
                                                rootCause.getMessage(),
                                                errorCode,
                                                status.value(),
                                                technicalDetail));
        }

        /**
         * Método auxiliar para navegar hasta la excepción real en errores no manejados
         */
        private Throwable getRootCause(Throwable throwable) {
                Throwable cause = throwable;
                while (cause.getCause() != null && cause != cause.getCause()) {
                        cause = cause.getCause();
                }
                return cause;
        }

        /**
         * Método auxiliar para convertir los mensajes de error en Status HTTP
         */
        private String resolveErrorCode(Throwable ex) {
                // 1. EL ÚNICO QUE DISPARA REFRESH
                if (ex instanceof io.jsonwebtoken.ExpiredJwtException)
                        return "TOKEN_EXPIRED";

                // 2. TOKEN BASURA -> LOGIN DIRECTO
                if (ex instanceof io.jsonwebtoken.MalformedJwtException ||
                                ex instanceof io.jsonwebtoken.security.SignatureException ||
                                ex instanceof io.jsonwebtoken.JwtException ||
                                ex instanceof JsonProcessingException)
                        return "TOKEN_INVALID";

                // 3. BLOQUEOS -> MENSAJE CRÍTICO Y LOGIN
                if (ex instanceof EmpresaBloqueada)
                        return "TENANT_BLOCKED";
                if (ex instanceof UsuarioBloqueado)
                        return "USER_DISABLED";

                // 4. ACCESO DENEGADO
                if (ex instanceof org.springframework.security.access.AccessDeniedException) {
                        // Caso: El token es válido, pero el permiso fue revocado o no existe
                        // Redireccionamos al dashboard
                        return "ACCESS_DENIED";
                }

                // 5. RECURSO NO ENCONTRADO
                if (ex instanceof jakarta.persistence.EntityNotFoundException)
                        return "RESOURCE_NOT_FOUND";

                return "SERVER_ERROR";
        }

        /**
         * errores por falta de parámetros en la URL, como @PathVariable o @RequestParam
         */
        @ExceptionHandler(org.springframework.web.bind.MissingPathVariableException.class)
        public ResponseEntity<ApiResponse<Void>> handleMissingPathVariable(
                        org.springframework.web.bind.MissingPathVariableException ex) {
                return ResponseEntity.status(400).body(
                                ApiResponse.error(
                                                "Falta un parámetro necesario en la URL: " + ex.getVariableName(),
                                                "MISSING_PATH_VARIABLE",
                                                400,
                                                null));
        }

        /**
         * error de recurso no encontrado, para capturar casos donde se accede a una
         * ruta o recurso que no existe
         */
        @ExceptionHandler(NoResourceFoundException.class)
        public ResponseEntity<ApiResponse<Void>> handleNoResourceFoundException(NoResourceFoundException ex) {
                ErrorCatalog error = ErrorCatalog.RESOURCE_NOT_FOUND;

                return ResponseEntity.status(error.getStatus())
                                .body(ApiResponse.error(
                                                error.getMessage(),
                                                error.name(),
                                                error.getStatus().value(),
                                                "No se encontró el recurso para la ruta: " + ex.getResourcePath()));
        }

        /**
         * Error de Cuerpo de Petición Faltante o Mal Formado (JSON) - Captura casos
         * donde el cuerpo de la petición es obligatorio pero no se envía, o se envía un
         * JSON mal formado. Ejemplo: @RequestBody(required = true) MyDto dto
         */
        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
                ErrorCatalog error = ErrorCatalog.JSON_PARSE_ERROR;

                // Obtenemos el mensaje de la causa raíz
                String fullMessage = ex.getMostSpecificCause().getMessage();
                String technicalDetail = fullMessage;

                // Truncamos si contiene los dos puntos (donde Spring suele meter la firma del
                // método)
                if (fullMessage != null && fullMessage.contains(":")) {
                        technicalDetail = fullMessage.split(":")[0];
                }

                return ResponseEntity.status(error.getStatus())
                                .body(ApiResponse.error(
                                                error.getMessage(),
                                                error.name(),
                                                error.getStatus().value(),
                                                technicalDetail // Ahora solo dirá "Required request body is missing"
                                ));
        }

        /**
         * Error de Parámetro Faltante (Solo si en el Controller pones required = true)
         * Ejemplo: @RequestParam(required = true) String name
         */
        @ExceptionHandler(MissingServletRequestParameterException.class)
        public ResponseEntity<ApiResponse<Void>> handleMissingParams(MissingServletRequestParameterException ex) {
                ErrorCatalog error = ErrorCatalog.INVALID_PARAMS;

                return ResponseEntity.status(error.getStatus())
                                .body(ApiResponse.error(
                                                error.getMessage(),
                                                error.name(),
                                                error.getStatus().value(),
                                                String.format("El parámetro '%s' es obligatorio",
                                                                ex.getParameterName())));
        }

        /**
         * Resolver el status code de la excepción paa errores genericos
         */
        private HttpStatus resolveHttpStatus(Throwable ex) {
                // RECURSO NO ENCONTRADO
                if (ex instanceof jakarta.persistence.EntityNotFoundException) {
                        return HttpStatus.NOT_FOUND; // 404
                }

                // EL USUARO NO TIENE PERMISOS PARA EL RECURSO
                if (ex instanceof org.springframework.security.access.AccessDeniedException) {
                        return HttpStatus.FORBIDDEN;
                }

                // USUARIO BLOQUEADO o EMPRESA BLOQUEADA
                if (ex instanceof EmpresaBloqueada ||
                                ex instanceof UsuarioBloqueado) {
                        return HttpStatus.UNAUTHORIZED;
                }

                // Cualquier problema con el Token -> 401 Unauthorized
                if (ex instanceof io.jsonwebtoken.JwtException ||
                                ex instanceof JsonProcessingException) {
                        return HttpStatus.UNAUTHORIZED;
                }

                // Problemas de Permisos/Roles -> 403 Forbidden
                if (ex instanceof org.springframework.security.access.AccessDeniedException) {
                        return HttpStatus.FORBIDDEN;
                }

                // Todo lo demás -> 500
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        /**
         * Manejar el error de credenciales incorrectas, contraseña incorrecta en el
         * login
         */
        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<ApiResponse<Void>> handleBadCredentials(BadCredentialsException ex) {
                return ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .body(ApiResponse.error(
                                                "Correo o contraseña incorrectos",
                                                "INVALID_CREDENTIALS",
                                                HttpStatus.UNAUTHORIZED.value(),
                                                ex.getClass().getSimpleName()));
        }

        /**
         * Manejar el error de usuario no encontrado
         */
        @ExceptionHandler(UsernameNotFoundException.class)
        public ResponseEntity<ApiResponse<Void>> handleUsernameNotFound(UsernameNotFoundException ex) {
                return ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .body(ApiResponse.error(
                                                "Correo electronico incorrecto",
                                                "USER_NOT_FOUND",
                                                HttpStatus.UNAUTHORIZED.value(),
                                                ex.getClass().getSimpleName()));
        }
}
