package com.aml.app.modules.empresa;

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

import com.aml.app.modules.empresa.dto.BuscarEmpresaRequest;
import com.aml.app.modules.empresa.dto.CrearEmpresaRequest;
import com.aml.app.modules.empresa.dto.EmpresaDireccionResponse;
import com.aml.app.modules.empresa.dto.EmpresaResponse;
import com.aml.app.modules.empresa.mappers.EmpresaMapper;
import com.aml.app.modules.tema.TemaEntity;
import com.aml.app.modules.tema.TemaRepository;
import com.aml.app.shared.StringUtils;
import com.aml.app.shared.ValidationResponse;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final EmpresaMapper empresaMapper;
    private final TemaRepository temaRepository;

    @Transactional(rollbackFor = Exception.class)
    public EmpresaResponse crearEmpresa(CrearEmpresaRequest request) {
        EmpresaEntity empresaEntity = empresaRepository.save(empresaMapper.toEntity(request));

        // Datos por defecto
        TemaEntity temaEntity = new TemaEntity();
        temaEntity.setEmpresaId(empresaEntity.getEmpresaId());
        temaEntity.setTemaSlogan("");
        temaEntity.setTemaPrimary("");
        temaEntity.setTemaSecondary("");
        temaEntity.setTemaTertiary("");
        temaEntity.setEmpresa(empresaEntity);
        temaRepository.save(temaEntity);

        return empresaMapper.toResponse(empresaEntity);
    }

    public EmpresaResponse buscarPorId(UUID empresaId) {
        EmpresaEntity entity = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la empresa"));
        return empresaMapper.toResponse(entity);
    }

    public void eliminar(UUID empresaId) {
        empresaRepository.deleteById(empresaId);
    }

    public EmpresaResponse actualizar(UUID empresaId, CrearEmpresaRequest request) {
        EmpresaEntity entity = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la empresa"));

        entity.setEmpresaCodigo(StringUtils.normalizarEspacios(request.getEmpresaCodigo()));
        entity.setEmpresaRuc(StringUtils.normalizarEspacios(request.getEmpresaRuc()));
        entity.setEmpresaNombre(StringUtils.normalizarEspacios(request.getEmpresaNombre()));
        entity.setEmpresaSiglas(StringUtils.normalizarEspacios(request.getEmpresaSiglas()));

        entity.setEmpresaTelefono(request.getEmpresaTelefono());
        entity.setEmpresaEmail(request.getEmpresaEmail());
        entity.setEmpresaWeb(request.getEmpresaWeb());

        entity.setEmpresaEstado(request.getEmpresaEstado());

        entity = empresaRepository.save(entity);
        return empresaMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public Page<EmpresaResponse> buscar(BuscarEmpresaRequest request) {

        Direction direction = request.getSortDir().equalsIgnoreCase("desc") ? Direction.DESC
                : Direction.ASC;
        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by(direction, request.getSortBy()));

        Specification<EmpresaEntity> spec = (root, query, cb) -> {
            List<Predicate> mainPredicates = new ArrayList<>();

            // Filtro por Estado
            if (request.getEstado() != null) {
                mainPredicates.add(cb.equal(root.get("empresaEstado"), request.getEstado()));
            }

            // Filtro por Nombre RUC Siglas o Código
            if (request.getFiltro() != null && !request.getFiltro().isBlank()) {
                String[] terminosBusqueda = request.getFiltro().toUpperCase().split("\\s+");
                List<Predicate> palabraPredicates = new ArrayList<>();

                for (String palabra : terminosBusqueda) {
                    String pattern = "%" + palabra + "%";
                    Predicate rucLike = cb.like(cb.upper(root.get("empresaRuc")), pattern);
                    Predicate nombreLike = cb.like(cb.upper(root.get("empresaNombre")), pattern);
                    Predicate siglasLike = cb.like(cb.upper(root.get("empresaSiglas")), pattern);
                    Predicate codigoLike = cb.like(cb.upper(root.get("empresaCodigo")), pattern);
                    palabraPredicates.add(cb.or(rucLike, nombreLike, siglasLike, codigoLike));
                }
                mainPredicates.add(cb.or(palabraPredicates.toArray(new Predicate[0])));
            }

            return cb.and(mainPredicates.toArray(new Predicate[0]));
        };

        return empresaRepository.findAll(spec, pageable).map(empresaMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ValidationResponse validarEmpresaNombre(String empresaNombre) {

        boolean existe = empresaRepository
                .existsByEmpresaNombreIgnoreCase(StringUtils.normalizarEspacios(empresaNombre));
        ValidationResponse validationResponse = ValidationResponse.builder()
                .present(existe)
                .build();
        return validationResponse;
    }

    @Transactional(readOnly = true)
    public ValidationResponse validarEmpresaNombreById(String empresaNombre, UUID empresaId) {
        boolean existe = empresaRepository.existsByEmpresaNombreIgnoreCaseAndEmpresaIdNot(
                StringUtils.normalizarEspacios(empresaNombre), empresaId);
        ValidationResponse validationResponse = ValidationResponse.builder()
                .present(existe)
                .build();
        return validationResponse;
    }

    @Transactional(readOnly = true)
    public ValidationResponse validarEmpresaCodigo(String empresaCodigo) {
        boolean existe = empresaRepository
                .existsByEmpresaCodigoIgnoreCase(StringUtils.normalizarEspacios(empresaCodigo));
        ValidationResponse validationResponse = ValidationResponse.builder()
                .present(existe)
                .build();
        return validationResponse;
    }

    @Transactional(readOnly = true)
    public ValidationResponse validarEmpresaCodigoById(String empresaCodigo, UUID empresaId) {
        boolean existe = empresaRepository.existsByEmpresaCodigoIgnoreCaseAndEmpresaIdNot(
                StringUtils.normalizarEspacios(empresaCodigo), empresaId);
        ValidationResponse validationResponse = ValidationResponse.builder()
                .present(existe)
                .build();
        return validationResponse;
    }

    public EmpresaDireccionResponse obtenerEmpresaPorIdDirecciones(UUID empresaId) {
        EmpresaEntity entity = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la empresa"));
        return empresaMapper.toEmpresaDireccionResponse(entity);
    }

}
