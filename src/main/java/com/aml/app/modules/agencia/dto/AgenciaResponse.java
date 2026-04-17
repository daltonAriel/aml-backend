package com.aml.app.modules.agencia.dto;

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
public class AgenciaResponse {

    private UUID agenciaId;

    private UUID empresaId;

    private String agenciaCodigo;

    private String agenciaNombre;

    private String agenciaDireccion;

    private boolean agenciaEstado;

    private LocalDateTime agenciaFechaCreacion;
    
}
