package com.aml.app.modules.provincia.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuscarProvinciaRequest {
    /**
     * Campos opcionales para filtrar la búsqueda de provincias. Si se proporcionan,
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
    private String sortBy = "provinciaNombre";

    @Builder.Default
    private String sortDir = "desc";

}
