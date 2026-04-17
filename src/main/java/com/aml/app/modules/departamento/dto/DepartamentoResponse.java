package com.aml.app.modules.departamento.dto;

import java.time.LocalDateTime;
import java.util.UUID;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartamentoResponse {

    private UUID departamentoId;

    private UUID empresaId;

    private String departamentoNombre;

    private String departamentoDescripcion;

    private boolean departamentoEstado;

    private LocalDateTime departamentoFechaCreacion;

}
