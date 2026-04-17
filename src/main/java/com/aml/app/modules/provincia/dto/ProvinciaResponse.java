package com.aml.app.modules.provincia.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProvinciaResponse {

    private UUID provinciaId;
    private String provinciaCodigo;
    private String provinciaNombre;

}
