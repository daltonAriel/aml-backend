package com.aml.app.modules.provincia.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.aml.app.modules.provincia.ProvinciaEntity;
import com.aml.app.modules.provincia.dto.ProvinciaResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ProvinciaMapper {

    public abstract ProvinciaResponse toResponse(ProvinciaEntity entity);

}
