package com.aml.app.modules.provincia;

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

import com.aml.app.modules.provincia.dto.BuscarProvinciaRequest;
import com.aml.app.modules.provincia.dto.ProvinciaResponse;
import com.aml.app.modules.provincia.mappers.ProvinciaMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProvinciaService {

    private final ProvinciaRepository provinciaRepository;
    private final ProvinciaMapper provinciaMapper;

    public ProvinciaResponse buscarPorId(UUID provinciaId) {
        ProvinciaEntity provinciaEntity = provinciaRepository.findById(provinciaId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la provincia"));
        return provinciaMapper.toResponse(provinciaEntity);
    }

    public List<ProvinciaResponse> buscarTodos() {
        return provinciaRepository.findAll().stream().map(provinciaMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Page<ProvinciaResponse> buscar(BuscarProvinciaRequest request) {
        Direction direction = request.getSortDir().equalsIgnoreCase("desc") ? Direction.DESC
                : Direction.ASC;
        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by(direction, request.getSortBy()));

        Specification<ProvinciaEntity> spec = (root, query, cb) -> {
            List<Predicate> mainPredicates = new ArrayList<>();

            // Filtro por Nombre
            if (request.getFiltro() != null && !request.getFiltro().isBlank()) {
                String[] terminosBusqueda = request.getFiltro().toUpperCase().split("\\s+");
                List<Predicate> palabraPredicates = new ArrayList<>();

                for (String palabra : terminosBusqueda) {
                    String pattern = "%" + palabra + "%";
                    Predicate nombreLIke = cb.like(cb.upper(root.get("provinciaNombre")), pattern);
                    palabraPredicates.add(cb.or(nombreLIke));
                }
                mainPredicates.add(cb.or(palabraPredicates.toArray(new Predicate[0])));
            }

            return cb.and(mainPredicates.toArray(new Predicate[0]));
        };

        return provinciaRepository.findAll(spec, pageable).map(provinciaMapper::toResponse);
    }

}
