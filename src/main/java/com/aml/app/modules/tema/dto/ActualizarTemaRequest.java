package com.aml.app.modules.tema.dto;

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
public class ActualizarTemaRequest {
    @NotNull(message = "Campo de slogan no puede ser null")
    @Size(max = 150, message = "El campo debe tener menos de 150 caracteres")
    private String slogan;
    
    @NotBlank(message = "El color primario es obligatorio")
    @Size(min = 4, max = 7, message = "El color hexadecimal debe tener entre 4 y 7 caracteres")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Formato de color inválido. Debe ser hexadecimal sin opacidad")
    private String primary;

    @NotBlank(message = "El color secundario es obligatorio")
    @Size(min = 4, max = 7, message = "El color hexadecimal debe tener entre 4 y 7 caracteres")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Formato de color inválido. Debe ser hexadecimal sin opacidad")
    private String secondary;

    @NotBlank(message = "El color terciario es obligatorio")
    @Size(min = 4, max = 7, message = "El color hexadecimal debe tener entre 4 y 7 caracteres")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Formato de color inválido. Debe ser hexadecimal sin opacidad")
    private String tertiary;
}
