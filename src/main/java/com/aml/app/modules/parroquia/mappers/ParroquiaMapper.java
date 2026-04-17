package com.aml.app.modules.parroquia.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.aml.app.modules.parroquia.ParroquiaEntity;
import com.aml.app.modules.parroquia.dto.ParroquiaResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ParroquiaMapper {

    public abstract ParroquiaResponse toResponse(ParroquiaEntity entity);

}
