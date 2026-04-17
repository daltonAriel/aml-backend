package com.aml.app.modules.departamento.mappers;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.aml.app.modules.departamento.DepartamentoEntity;
import com.aml.app.modules.departamento.dto.CrearDepartamentoRequest;
import com.aml.app.modules.departamento.dto.DepartamentoResponse;
import com.aml.app.shared.StringUtils;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class DepartamentoMapper {

    @BeforeMapping
    protected void sanitizeRequest(CrearDepartamentoRequest request) {
        if (request != null) {
            request.setDepartamentoNombre(StringUtils.normalizarEspacios(request.getDepartamentoNombre()));
            request.setDepartamentoDescripcion(StringUtils.normalizarEspacios(request.getDepartamentoDescripcion()));
        }
    }

    public abstract DepartamentoEntity toEntity(CrearDepartamentoRequest request);

    public abstract DepartamentoResponse toResponse(DepartamentoEntity entity);
}
