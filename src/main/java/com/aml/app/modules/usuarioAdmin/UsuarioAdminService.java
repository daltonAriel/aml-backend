package com.aml.app.modules.usuarioAdmin;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.aml.app.modules.usuarioAdmin.dto.ActualizarUsuarioAdminRequest;
import com.aml.app.modules.usuarioAdmin.dto.UsuarioAdminResponse;
import com.aml.app.modules.usuarioAdmin.mappers.UsuarioAdminMapper;
import com.aml.app.shared.StringUtils;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioAdminService {
    private final UsuarioAdminRepository usuarioAdminRepository;
    private final UsuarioAdminMapper usuarioAdminMapper;

    public UsuarioAdminResponse obtenerPorId(UUID usuarioAdminId) {
        UsuarioAdminEntity entity = usuarioAdminRepository.findById(usuarioAdminId).orElseThrow(
                () -> new EntityNotFoundException("No se encontró el usuario"));
        return usuarioAdminMapper.toResponse(entity);
    }

    public UsuarioAdminResponse actualizarDatos(UUID usuarioAdminId, ActualizarUsuarioAdminRequest request) {
        UsuarioAdminEntity entity = usuarioAdminRepository.findById(usuarioAdminId).orElseThrow(
                () -> new EntityNotFoundException("No se encontró el usuario"));

        entity.setUsuarioAdiminNombre(StringUtils.normalizarEspacios(request.getUsuarioAdminNombre()));
        entity.setUsuarioAdminApellido(StringUtils.normalizarEspacios(request.getUsuarioAdminApellido()));
        entity.setUsuarioAdminCedula(StringUtils.normalizarEspacios(request.getUsuarioAdminCedula()));
        entity.setUsuarioAdminEmail(StringUtils.normalizarEspacios(request.getUsuarioAdminEmail()));
        entity.setUsuarioAdminTelefono(StringUtils.normalizarEspacios(request.getUsuarioAdminTelefono()));

        return usuarioAdminMapper.toResponse(entity);
    }

}
