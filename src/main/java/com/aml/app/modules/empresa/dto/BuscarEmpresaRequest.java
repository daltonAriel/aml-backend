package com.aml.app.modules.empresa.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuscarEmpresaRequest {
    /**
     * Campos opcionales para filtrar la búsqueda de empresas. Si se proporcionan,
     * se aplicarán como criterios de búsqueda.
     */

    @Nullable
    private String filtro;

    @Nullable
    private Boolean estado;

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
    private String sortBy = "empresaFechaCreacion";

    @Builder.Default
    private String sortDir = "desc";
}
