package com.aml.app.modules.tema.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TemaResponse {
    private UUID temaId;
    private UUID empresaId;
    private String temaSlogan;
    private String temaPrimary;
    private String temaSecondary;
    private String temaTertiary;
    private String temaLogoUrl;
}
