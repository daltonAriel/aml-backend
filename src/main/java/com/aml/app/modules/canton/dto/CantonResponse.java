package com.aml.app.modules.canton.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CantonResponse {

    private UUID cantonId;

    private String cantonCodigo;

    private String cantonNombre;

    private UUID provinciaId;

}
