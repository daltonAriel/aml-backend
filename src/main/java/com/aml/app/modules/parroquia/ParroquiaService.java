package com.aml.app.modules.parroquia;

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
import org.springframework.transaction.annotation.Transactional;

import com.aml.app.modules.parroquia.dto.BuscarParroquiaRequest;
import com.aml.app.modules.parroquia.dto.ParroquiaResponse;
import com.aml.app.modules.parroquia.mappers.ParroquiaMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParroquiaService {
    private final ParroquiaRepository parroquiaRepository;
    private final ParroquiaMapper parroquiaMapper;

    @Transactional(readOnly = true)
    public ParroquiaResponse buscarPorId(UUID parroquiaId) {
        ParroquiaEntity parroquiaEntity = parroquiaRepository.findById(parroquiaId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la parroquia"));
        return parroquiaMapper.toResponse(parroquiaEntity);
    }

    @Transactional(readOnly = true)
    public List<ParroquiaResponse> buscarTodosPorCantonId(UUID cantonId) {
        Specification<ParroquiaEntity> spec = (root, query, cb) -> {
            List<Predicate> mainPredicates = new ArrayList<>();
            // Filtro por CantonId
            if (cantonId != null) {
                mainPredicates.add(cb.equal(root.get("cantonId"), cantonId.toString()));
            }
            return cb.and(mainPredicates.toArray(new Predicate[0]));
        };
        return parroquiaRepository.findAll(spec).stream().map(parroquiaMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Page<ParroquiaResponse> buscar(UUID cantonId, BuscarParroquiaRequest request) {

        Direction direction = request.getSortDir().equalsIgnoreCase("desc") ? Direction.DESC
                : Direction.ASC;
        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by(direction, request.getSortBy()));

        Specification<ParroquiaEntity> spec = (root, query, cb) -> {
            List<Predicate> mainPredicates = new ArrayList<>();

            // Filtro por Estado
            if (cantonId != null) {
                mainPredicates.add(cb.equal(root.get("cantonId"), cantonId));
            }

            // Filtro por Nombre
            if (request.getFiltro() != null && !request.getFiltro().isBlank()) {
                String[] terminosBusqueda = request.getFiltro().toUpperCase().split("\\s+");
                List<Predicate> palabraPredicates = new ArrayList<>();

                for (String palabra : terminosBusqueda) {
                    String pattern = "%" + palabra + "%";
                    Predicate nombreLike = cb.like(cb.upper(root.get("parroquiaNombre")), pattern);
                    palabraPredicates.add(cb.or(nombreLike));
                }
                mainPredicates.add(cb.or(palabraPredicates.toArray(new Predicate[0])));
            }

            return cb.and(mainPredicates.toArray(new Predicate[0]));
        };

        return parroquiaRepository.findAll(spec, pageable).map(parroquiaMapper::toResponse);
    }

}
