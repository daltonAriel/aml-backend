package com.aml.app.modules.empresa.mappers;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.aml.app.modules.empresa.EmpresaEntity;
import com.aml.app.modules.empresa.dto.CrearEmpresaRequest;
import com.aml.app.modules.empresa.dto.EmpresaDireccionResponse;
import com.aml.app.modules.empresa.dto.EmpresaDireccionesLogoResponse;
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

    @Mapping(target = "cantonId", source = "parroquia.canton.cantonId")
    @Mapping(target = "provinciaId", source = "parroquia.canton.provincia.provinciaId")
    public abstract EmpresaDireccionResponse toEmpresaDireccionResponse(EmpresaEntity entity);

    @Mapping(target = "provinciaNombre", source = "parroquia.canton.provincia.provinciaNombre")
    @Mapping(target = "cantonNombre", source = "parroquia.canton.cantonNombre")
    @Mapping(target = "parroquiaNombre", source = "parroquia.parroquiaNombre")
    @Mapping(target = "temaLogoUrl", source = "tema.temaLogoUrl")
    public abstract EmpresaDireccionesLogoResponse toEmpresaDireccionesLogo(EmpresaEntity entity);

}
