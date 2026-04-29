package com.aml.app.modules.empresa.dto;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CrearEmpresaRequest {

    @NotBlank(message = "El código de la empresa es obligatorio")
    private String empresaCodigo;

    @NotBlank(message = "El RUC es obligatorio")
    @Pattern(regexp = "^[0-9]{13}$", message = "El RUC debe tener 13 dígitos numéricos")
    private String empresaRuc;

    @NotBlank(message = "La Razón Social es obligatoria")
    @Size(min = 3, max = 200, message = "La razón social debe tener entre 3 y 200 caracteres")
    private String empresaNombre;

    private String empresaSiglas;

    // --- UBICACIÓN ---
    @NotNull(message = "El id de Parroquia es obligatorio")
    private UUID parroquiaId;

    // --- CONTACTO ---
    @NotBlank(message = "El teléfono de contacto es obligatorio")
    private String empresaTelefono;

    @NotBlank(message = "El email institucional es obligatorio")
    @Email(message = "El formato del email no es válido")
    private String empresaEmail;

    private String empresaWeb;

    @NotNull(message = "El estado de la empresa es requerido")
    private Boolean empresaEstado;

}
