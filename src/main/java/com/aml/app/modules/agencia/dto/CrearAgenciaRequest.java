package com.aml.app.modules.agencia.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrearAgenciaRequest {
    @NotNull(message = "El ID de la empresa es obligatorio")
    private UUID empresaId;

    @NotBlank(message = "El código de la agencia no puede estar vacío")
    @Size(max = 10, message = "El código no puede superar los 10 caracteres")
    private String agenciaCodigo;

    @NotBlank(message = "El nombre de la agencia es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String agenciaNombre;

    @NotBlank(message = "El id de Parroquia es obligatorio")
    private UUID parroquiaId;

    @NotNull(message = "El estado de la agencia es requerido")
    private boolean agenciaEstado;
}
