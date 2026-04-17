package com.aml.app.modules.usuarioAdmin;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aml.app.config.response.ApiResponse;
import com.aml.app.modules.usuarioAdmin.dto.ActualizarUsuarioAdminRequest;
import com.aml.app.modules.usuarioAdmin.dto.UsuarioAdminResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/admin/usuario")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioAdminController {

    private final UsuarioAdminService usuarioAdminService;

    @GetMapping("/{usuarioAdminId}")
    @PreAuthorize("hasRole('ROLE_SAAS_ADMIN')")
    public ResponseEntity<ApiResponse<UsuarioAdminResponse>> obtenerUsuarioAdminId(@PathVariable UUID usuarioAdminId) {
        UsuarioAdminResponse usuarioAdminResponse = usuarioAdminService.obtenerPorId(usuarioAdminId);
        return ResponseEntity.ok(ApiResponse.success(usuarioAdminResponse, "Usuario obtenido exitosamente"));
    }

    @PostMapping("/{usuarioAdminId}")
    @PreAuthorize("hasRole('ROLE_SAAS_ADMIN')")
    public ResponseEntity<ApiResponse<UsuarioAdminResponse>> acualizarUsuarioAdmin(@PathVariable UUID usuarioAdminId,
            @Valid @RequestBody ActualizarUsuarioAdminRequest request) {

        UsuarioAdminResponse usuarioAdminResponse = usuarioAdminService.actualizarDatos(usuarioAdminId, request);
        return ResponseEntity.ok(ApiResponse.success(usuarioAdminResponse, "Datos de usuario actualizados exitosamente"));
    }

}
