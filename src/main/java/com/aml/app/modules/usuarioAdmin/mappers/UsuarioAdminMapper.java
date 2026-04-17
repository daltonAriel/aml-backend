package com.aml.app.modules.usuarioAdmin.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.aml.app.modules.usuarioAdmin.UsuarioAdminEntity;
import com.aml.app.modules.usuarioAdmin.dto.UsuarioAdminResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UsuarioAdminMapper {

    //public abstract UsuarioAdminEntity toEntity(CrearUsuarioAdminRequest request);

    public abstract UsuarioAdminResponse toResponse(UsuarioAdminEntity entity);

}
