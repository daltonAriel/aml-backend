package com.aml.app.modules.tema.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TemaResponse {
    private UUID id;
    private UUID empresaId;
    private String slogan;
    private String primary;
    private String secondary;
    private String tertiary;
    private String logoUrl;
}
