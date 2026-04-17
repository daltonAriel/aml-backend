package com.aml.app.modules.departamento;

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

import com.aml.app.modules.departamento.dto.BuscarDepartamentoRequest;
import com.aml.app.modules.departamento.dto.CrearDepartamentoRequest;
import com.aml.app.modules.departamento.dto.DepartamentoResponse;
import com.aml.app.modules.departamento.mappers.DepartamentoMapper;
import com.aml.app.shared.StringUtils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;
    private final DepartamentoMapper departamentoMapper;

    public DepartamentoResponse crearDepartamento(CrearDepartamentoRequest request) {
        //EmpresaEntity empresaEntity = empresaRepository.findById(request.getEmpresaId())
        //        .orElseThrow(() -> new RuntimeException("No se encontró la empresa"));
        DepartamentoEntity departamentoEntity = departamentoMapper.toEntity(request);
        //departamentoEntity.setEmpresa(empresaEntity);
        return departamentoMapper.toResponse(departamentoRepository.save(departamentoEntity));
    }

    public Page<DepartamentoResponse> buscarDepartamentosPorEmpresaId(BuscarDepartamentoRequest request,
            UUID empresaId) {
        Direction direction = request.getSortDir().equalsIgnoreCase("desc") ? Direction.DESC
                : Direction.ASC;
        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by(direction, request.getSortBy()));

        Specification<DepartamentoEntity> spec = (root, query, cb) -> {
            List<Predicate> mainPredicates = new ArrayList<>();

            // Filtro por Empresa
            mainPredicates.add(cb.equal(root.get("empresaId"), empresaId));

            // Filtro por Estado
            if (request.getEstado() != null) {
                mainPredicates.add(cb.equal(root.get("departamentoEstado"), request.getEstado()));
            }

            // Filtro por Nombre o Descripción
            if (request.getFiltro() != null && !request.getFiltro().isBlank()) {
                String[] terminosBusqueda = request.getFiltro().toUpperCase().split("\\s+");
                List<Predicate> palabraPredicates = new ArrayList<>();

                for (String palabra : terminosBusqueda) {
                    String pattern = "%" + palabra + "%";
                    Predicate nombreLike = cb.like(cb.upper(root.get("departamentoNombre")), pattern);
                    Predicate descripcionLike = cb.like(cb.upper(root.get("departamentoDescripcion")), pattern);

                    palabraPredicates.add(cb.or(nombreLike, descripcionLike));
                }

                mainPredicates.add(cb.or(palabraPredicates.toArray(new Predicate[0])));
            }

            return cb.and(mainPredicates.toArray(new Predicate[0]));
        };

        return departamentoRepository.findAll(spec, pageable).map(departamentoMapper::toResponse);
    }

    public void eliminarDepartamento(UUID departamentoId) {
        departamentoRepository.deleteById(departamentoId);
    }

    public DepartamentoResponse obtenerPorId(UUID departamentoId) {
        return departamentoRepository.findById(departamentoId)
                .map(departamentoMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el departamento"));
    }

    public DepartamentoResponse actualizarDepartamento(UUID departamentoId, CrearDepartamentoRequest request) {
        DepartamentoEntity departamentoEntity = departamentoRepository.findById(departamentoId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el departamento"));

        departamentoEntity.setDepartamentoNombre(StringUtils.normalizarEspacios(request.getDepartamentoNombre()));
        departamentoEntity
                .setDepartamentoDescripcion(StringUtils.normalizarEspacios(request.getDepartamentoDescripcion()));
        departamentoEntity.setDepartamentoEstado(request.getDepartamentoEstado());
        return departamentoMapper.toResponse(departamentoRepository.save(departamentoEntity));
    }

}
