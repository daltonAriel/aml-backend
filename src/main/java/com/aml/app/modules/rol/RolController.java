package com.aml.app.modules.rol;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aml.app.config.response.ApiResponse;
import com.aml.app.modules.rol.dto.RolResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RolController {

    private final RolService rolService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RolResponse>>> getMethodName() {

        List<RolResponse> listaRoles = rolService.buscarTodos();
        return ResponseEntity.ok(ApiResponse.success(listaRoles, "Empresas obtenidas exitosamente"));
    }

    @GetMapping("/{rolId}")
    public ResponseEntity<ApiResponse<RolResponse>> getMethodName(@PathVariable UUID rolId) {
        return ResponseEntity.ok(ApiResponse.success(rolService.buscarPorId(rolId), "Rol obtenido exitosamente"));
    }

}
