package com.aml.app.modules.agencia.mappers;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.aml.app.modules.agencia.AgenciaEntity;
import com.aml.app.modules.agencia.dto.AgenciaResponse;
import com.aml.app.modules.agencia.dto.CrearAgenciaRequest;
import com.aml.app.shared.StringUtils;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class AgenciaMapper {

    @BeforeMapping
    protected void sanitizeRequest(CrearAgenciaRequest request) {
        if (request != null) {
            request.setAgenciaCodigo(StringUtils.normalizarEspacios(request.getAgenciaCodigo()));
            request.setAgenciaNombre(StringUtils.normalizarEspacios(request.getAgenciaNombre()));
        }
    }

    public abstract AgenciaEntity toEntity(CrearAgenciaRequest request);

    public abstract AgenciaResponse toResponse(AgenciaEntity entity);
}
