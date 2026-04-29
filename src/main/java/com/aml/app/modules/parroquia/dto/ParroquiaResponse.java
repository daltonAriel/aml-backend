package com.aml.app.modules.parroquia.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParroquiaResponse {

    private UUID parroquiaId;

    private String parroquiaCodigo;

    private String parroquiaNombre;

    private UUID cantonId;

}
