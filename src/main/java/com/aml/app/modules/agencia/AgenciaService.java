package com.aml.app.modules.agencia;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.aml.app.modules.agencia.dto.AgenciaResponse;
import com.aml.app.modules.agencia.dto.BuscarAgenciaRequest;
import com.aml.app.modules.agencia.dto.CrearAgenciaRequest;
import com.aml.app.modules.agencia.mappers.AgenciaMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgenciaService {

    private final AgenciaRepository agenciaRepository;
    private final AgenciaMapper agenciaMapper;

    public AgenciaResponse crearAgencia(CrearAgenciaRequest request) {
        AgenciaEntity agenciaEntity = agenciaRepository
                .save(agenciaMapper.toEntity(request));

        return agenciaMapper.toResponse(agenciaEntity);
    }

    public AgenciaResponse obtenerPorId(UUID agenciaId) {
        return agenciaRepository.findById(agenciaId)
                .map(agenciaMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la agencia"));
    }

    public Page<AgenciaResponse> buscarAgencias(BuscarAgenciaRequest request) {
        Direction direction = request.getSortDir().equalsIgnoreCase("desc") ? Direction.DESC
                : Direction.ASC;
        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by(direction, request.getSortBy()));

        Specification<AgenciaEntity> spec = (root, query, cb) -> {
            List<Predicate> mainPredicates = new ArrayList<>();

            // Filtro por Agencia o codigo
            if (request.getFiltro() != null && !request.getFiltro().isBlank()) {
                String[] terminosBusqueda = request.getFiltro().toUpperCase().split("\\s+");
                List<Predicate> palabraPredicates = new ArrayList<>();

                for (String palabra : terminosBusqueda) {
                    String pattern = "%" + palabra + "%";
                    Predicate codigoLike = cb.like(cb.upper(root.get("agenciaCodigo")), pattern);
                    Predicate nombreLike = cb.like(cb.upper(root.get("agenciaNombre")), pattern);
                    palabraPredicates.add(cb.or(codigoLike, nombreLike));
                }
                mainPredicates.add(cb.or(palabraPredicates.toArray(new Predicate[0])));
            }

            return cb.and(mainPredicates.toArray(new Predicate[0]));
        };

        return agenciaRepository.findAll(spec, pageable).map(agenciaMapper::toResponse);
    }

    public AgenciaResponse actualizarAgencia(UUID agenciaId, CrearAgenciaRequest request) {
        AgenciaEntity agenciaEntity = agenciaRepository
                .findById(agenciaId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la agencia"));

        agenciaEntity.setAgenciaCodigo(request.getAgenciaCodigo());
        agenciaEntity.setAgenciaNombre(request.getAgenciaNombre());
        agenciaEntity.setParroquiaId(request.getParroquiaId());
        agenciaEntity.setAgenciaEstado(request.isAgenciaEstado());

        agenciaRepository.save(agenciaEntity);
        return agenciaMapper.toResponse(agenciaEntity);
    }

    public void eliminarAgencia(UUID agenciaId) {
        agenciaRepository.deleteById(agenciaId);
    }

}
