package com.aml.app.modules.tema.mappers;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.aml.app.modules.tema.TemaEntity;
import com.aml.app.modules.tema.dto.ActualizarTemaRequest;
import com.aml.app.modules.tema.dto.TemaResponse;
import com.aml.app.shared.StringUtils;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class TemaMapper {

    @BeforeMapping
    protected void sanitizeRequest(ActualizarTemaRequest request) {
        if (request != null) {
            request.setSlogan(StringUtils.normalizarEspacios(request.getSlogan()));
        }
    }

    public abstract TemaEntity toEntity(ActualizarTemaRequest request);

    public abstract TemaResponse toResponse(TemaEntity  entity);

}
