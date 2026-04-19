package com.aml.app.security.dto;

import java.util.Set;
import java.util.UUID;

import com.aml.app.modules.rol.RolEnumType;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class UserDetailEmpresa {
    private UUID id;
    private String email;
    private String password;
    private UUID empresaId;
    private boolean estado;
    private boolean empresaEstado;
    private Set<RolEnumType> roles;

    private String nombre;
    private String apellido;
    private String rolDescripcion;
}
