package com.aml.app.modules.canton;

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

import com.aml.app.modules.canton.dto.BuscarCantonRequest;
import com.aml.app.modules.canton.dto.CantonResponse;
import com.aml.app.modules.canton.mappers.CantonMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CantonService {

    private final CantonRepository cantonRepository;
    private final CantonMapper cantonMapper;

    public CantonResponse buscarPorId(UUID cantonId) {
        CantonEntity cantonEntity = cantonRepository.findById(cantonId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la provincia"));
        return cantonMapper.toResponse(cantonEntity);
    }

    public List<CantonResponse> buscarTodosPorProvinciaId(UUID provinciaId) {
        Specification<CantonEntity> spec = (root, query, cb) -> {
            List<Predicate> mainPredicates = new ArrayList<>();
            // Filtro por ProvinciaId
            if (provinciaId != null) {
                mainPredicates.add(cb.equal(root.get("provinciaId"), provinciaId.toString()));
            }
            return cb.and(mainPredicates.toArray(new Predicate[0]));
        };
        return cantonRepository.findAll(spec).stream().map(cantonMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Page<CantonResponse> buscar(UUID provinciaId, BuscarCantonRequest request) {

        Direction direction = request.getSortDir().equalsIgnoreCase("desc") ? Direction.DESC
                : Direction.ASC;
        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by(direction, request.getSortBy()));

        Specification<CantonEntity> spec = (root, query, cb) -> {
            List<Predicate> mainPredicates = new ArrayList<>();

            // Filtro por Estado
            if (provinciaId != null) {
                mainPredicates.add(cb.equal(root.get("provinciaId"), provinciaId));
            }

            // Filtro por Nombre
            if (request.getFiltro() != null && !request.getFiltro().isBlank()) {
                String[] terminosBusqueda = request.getFiltro().toUpperCase().split("\\s+");
                List<Predicate> palabraPredicates = new ArrayList<>();

                for (String palabra : terminosBusqueda) {
                    String pattern = "%" + palabra + "%";
                    Predicate nombreLike = cb.like(cb.upper(root.get("cantonNombre")), pattern);
                    palabraPredicates.add(cb.or(nombreLike));
                }
                mainPredicates.add(cb.or(palabraPredicates.toArray(new Predicate[0])));
            }

            return cb.and(mainPredicates.toArray(new Predicate[0]));
        };

        return cantonRepository.findAll(spec, pageable).map(cantonMapper::toResponse);
    }
}
