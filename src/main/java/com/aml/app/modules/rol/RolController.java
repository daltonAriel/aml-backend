package com.aml.app.modules.rol;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aml.app.config.response.ApiResponse;
import com.aml.app.modules.rol.dto.RolResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RolController {

    private final RolService rolService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN1')")
    public ResponseEntity<ApiResponse<List<RolResponse>>> getMethodName() {
        
        List<RolResponse> listaRoles = rolService.buscarTodos();
        return ResponseEntity.ok(ApiResponse.success(listaRoles, "Empresas obtenidas exitosamente"));
    }

    @GetMapping("/{rolId}")
    public ResponseEntity<ApiResponse<RolResponse>> getMethodName(@PathVariable UUID rolId) {
        return ResponseEntity.ok(ApiResponse.success(rolService.buscarPorId(rolId), "Rol obtenido exitosamente"));
    }

}
