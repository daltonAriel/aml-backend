package com.aml.app.modules.usuarioAdmin.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UsuarioAdminResponse {
    private UUID usuarioAdminId;
    private String usuarioAdminNombre;
    private String usuarioAdminApellido;
    private String usuarioAdminCedula;
    private String usuarioAdminTelefono;
    private String usuarioAdminEmail;
}
