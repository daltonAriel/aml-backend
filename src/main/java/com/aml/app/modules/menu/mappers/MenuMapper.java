package com.aml.app.modules.menu.mappers;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.aml.app.modules.menu.MenuEntity;
import com.aml.app.modules.menu.dto.CrearMenuRequest;
import com.aml.app.modules.menu.dto.MenuResponse;
import com.aml.app.shared.StringUtils;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class MenuMapper {

    public abstract MenuResponse toResponse(MenuEntity entity);

    public abstract MenuEntity toEntity(CrearMenuRequest request);

    @BeforeMapping
    protected void sanitizeRequest(CrearMenuRequest request) {
        if (request != null) {
            request.setMenuLabel(StringUtils.normalizarEspacios(request.getMenuLabel()));
            request.setMenuUrl(StringUtils.normalizarEspacios(request.getMenuUrl()));
            request.setMenuIcon(StringUtils.normalizarEspacios(request.getMenuIcon()));
        }
    }

}
