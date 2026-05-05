package com.aml.app.modules.menu.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CrearMenuRequest {

    private UUID menuIdPadre;

    @NotBlank(message = "El nombre del menu es obligatorio")
    @Size(min = 3, max = 20, message = "El nombre debe tener entre 3 y 20 caracteres")
    private String menuLabel;

    @NotBlank(message = "La url del menu es obligatoria")
    @Size(min = 3, max = 100, message = "La url debe tener entre 3 y 100 caracteres")
    private String menuUrl;

    @NotBlank(message = "El nombre del icono es obligatorio")
    private String menuIcon;

    private Integer menuOrden;

    @NotNull(message = "El estado del menu es obligatorio")
    private Boolean menuEstado;
}
