package com.aml.app.modules.rol.dto;

import java.util.UUID;

import com.aml.app.modules.rol.RolEnumType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RolResponse {
    private UUID rolId;
    private RolEnumType rolNombre;
    private String rolDescripcion;
}
