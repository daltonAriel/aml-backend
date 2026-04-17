package com.aml.app.modules.canton.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.aml.app.modules.canton.CantonEntity;
import com.aml.app.modules.canton.dto.CantonResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CantonMapper {

    public abstract CantonResponse toResponse(CantonEntity entity);

}
