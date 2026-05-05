package com.aml.app.modules.empresa.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EmpresaDireccionesLogoResponse {
    private UUID empresaId;
    private String empresaCodigo;
    private String empresaRuc;
    private String empresaNombre;
    private String empresaSiglas;

    // --- DIRECCION ---
    private String provinciaNombre;
    private String cantonNombre;
    private String parroquiaNombre;

    // Logo
    private String temaLogoUrl;

    // --- CONTACTO ---
    private String empresaTelefono;
    private String empresaEmail;
    private String empresaWeb;

    // --- ESTADO ---
    private Boolean empresaEstado;
}
