package com.aml.app.config.errorManager;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCatalog {
        // --- ERRORES DE NEGOCIO / DOMINIO ---
    RESOURCE_NOT_FOUND("El recurso solicitado no existe", HttpStatus.NOT_FOUND),
    BUSINESS_RULE_VIOLATION("No se puede realizar la operación por reglas de negocio", HttpStatus.BAD_REQUEST),
    INVALID_OPERATION("Operación no permitida en el estado actual", HttpStatus.CONFLICT),
    DUPLICATE_RESOURCE("El recurso ya existe en el sistema", HttpStatus.CONFLICT),

    // --- ERRORES DE INFRAESTRUCTURA / SPRING ---
    INVALID_PARAMS("Los parámetros de la solicitud son incorrectos o faltan", HttpStatus.BAD_REQUEST),
    IO_ERROR("Error al procesar la entrada/salida de datos", HttpStatus.INTERNAL_SERVER_ERROR),
    GENERIC_ERROR("Ocurrió un error interno inesperado", HttpStatus.INTERNAL_SERVER_ERROR),
    JSON_PARSE_ERROR("El formato del JSON enviado es inválido", HttpStatus.BAD_REQUEST),

    // --- ERRORES DE BASE DE DATOS ---
    DATABASE_ERROR("Error de comunicación con la base de datos", HttpStatus.SERVICE_UNAVAILABLE),
    DATA_INTEGRITY_VIOLATION("Error de integridad de datos (llaves duplicadas o nulas)", HttpStatus.CONFLICT),

    // --- ERRORES DE SEGURIDAD ---
    UNAUTHORIZED("No tiene los permisos necesarios para esta acción", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("Acceso denegado al recurso", HttpStatus.FORBIDDEN),
    EXPIRED_TOKEN("La sesión ha expirado, por favor inicie sesión nuevamente", HttpStatus.UNAUTHORIZED);

    private final String message;
    private final HttpStatus status;

    ErrorCatalog(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
