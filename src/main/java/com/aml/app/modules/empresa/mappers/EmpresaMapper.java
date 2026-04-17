package com.aml.app.modules.empresa.mappers;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.aml.app.modules.empresa.EmpresaEntity;
import com.aml.app.modules.empresa.dto.CrearEmpresaRequest;
import com.aml.app.modules.empresa.dto.EmpresaResponse;
import com.aml.app.shared.StringUtils;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class EmpresaMapper {

    @BeforeMapping
    protected void sanitizeRequest(CrearEmpresaRequest request) {
        if (request != null) {
            request.setEmpresaCodigo(StringUtils.normalizarEspacios(request.getEmpresaCodigo()));
            request.setEmpresaRuc(StringUtils.normalizarEspacios(request.getEmpresaRuc()));
            request.setEmpresaNombre(StringUtils.normalizarEspacios(request.getEmpresaNombre()));
            request.setEmpresaSiglas(StringUtils.normalizarEspacios(request.getEmpresaSiglas()));

            // Sanitización de Contacto
            request.setEmpresaEmail(StringUtils.normalizarEspacios(request.getEmpresaEmail().toLowerCase()));
            request.setEmpresaTelefono(StringUtils.normalizarEspacios(request.getEmpresaTelefono()));
            request.setEmpresaWeb(StringUtils.normalizarEspacios(request.getEmpresaWeb()));
        }
    }

    public abstract EmpresaEntity toEntity(CrearEmpresaRequest request);

    public abstract EmpresaResponse toResponse(EmpresaEntity entity);

}
