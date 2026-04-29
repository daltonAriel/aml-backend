package com.aml.app.shared;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ValidationRequest {
    @NotBlank(message = "El valor para verificar no puede estar vacío")
    private String valor;
}
