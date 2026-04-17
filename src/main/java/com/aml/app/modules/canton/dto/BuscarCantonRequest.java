package com.aml.app.modules.canton.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuscarCantonRequest {
    /**
     * Campos opcionales para filtrar la búsqueda de cantones. Si se proporcionan,
     * se aplicarán como criterios de búsqueda.
     */

    @Nullable
    private String filtro;

    /**
     * Campos para paginación y ordenamiento. Si no se proporcionan, se usarán
     * valores predeterminados para mostrar la primera página con 10 resultados
     * ordenados por fecha de creación en orden descendente.
     */

    @Builder.Default
    private int page = 0;

    @Builder.Default
    private int size = 10;

    @Builder.Default
    private String sortBy = "cantonNombre";

    @Builder.Default
    private String sortDir = "desc";
    
}
