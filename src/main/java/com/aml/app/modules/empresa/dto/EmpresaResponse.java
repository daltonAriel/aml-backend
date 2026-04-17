package com.aml.app.modules.empresa.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EmpresaResponse {
    private UUID empresaId;
    private String empresaCodigo;
    private String empresaRuc;
    private String empresaNombre;
    private String empresaSiglas;

    // --- UBICACIÓN ---
    private UUID parroquiaId;

    // --- CONTACTO ---
    private String empresaTelefono;
    private String empresaEmail;
    private String empresaWeb;

    // --- ESTADO Y CONTROL ---
    private Boolean empresaEstado;
    private LocalDateTime empresaFechaCreacion;
}
