package com.aml.app.modules.rol;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.aml.app.modules.rol.dto.RolResponse;
import com.aml.app.modules.rol.mappers.RolMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    public RolResponse buscarPorId(UUID rolId) {
        RolEntity entity = rolRepository.findById(rolId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el rol"));
        return rolMapper.toResponse(entity);
    }

    public List<RolResponse> buscarTodos() {
        return rolRepository.findAll()
                .stream()
                .map(rolMapper::toResponse)
                .toList();
    }

}
