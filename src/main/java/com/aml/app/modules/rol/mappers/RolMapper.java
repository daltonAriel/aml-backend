package com.aml.app.modules.rol.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.aml.app.modules.rol.RolEntity;
import com.aml.app.modules.rol.dto.RolResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class RolMapper {

    public abstract RolResponse toResponse(RolEntity entity);

}
