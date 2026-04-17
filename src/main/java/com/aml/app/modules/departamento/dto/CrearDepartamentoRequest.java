package com.aml.app.modules.departamento.dto;

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
public class CrearDepartamentoRequest {

    @NotNull(message = "El ID de la empresa es obligatorio")
    private UUID empresaId;

    @NotBlank(message = "El nombre del departamento es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String departamentoNombre;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String departamentoDescripcion;

    @NotNull(message = "El estado del departamento es requerido")
    private Boolean departamentoEstado;
}
