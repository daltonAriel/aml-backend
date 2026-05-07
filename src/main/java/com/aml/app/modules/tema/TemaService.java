package com.aml.app.modules.tema;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.aml.app.modules.tema.dto.ActualizarTemaRequest;
import com.aml.app.modules.tema.dto.TemaResponse;
import com.aml.app.modules.tema.mappers.TemaMapper;
import com.aml.app.shared.StringUtils;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TemaService {
        private final TemaRepository temaRepository;
        private final TemaMapper temaMapper;
        private final StorageLogo storageLogo;

        @Value("${app.storage.location}")
        private String storagePath;

        public TemaResponse actualizar(UUID empresaId, ActualizarTemaRequest request) {
                Specification<TemaEntity> spec = (root, query, criteriaBuilder) -> criteriaBuilder
                                .equal(root.get("empresaId"), empresaId);

                TemaEntity temaEntity = temaRepository.findOne(spec)
                                .orElseThrow(() -> new EntityNotFoundException("No se encontró el tema de la empresa"));

                temaEntity.setTemaSlogan(StringUtils.normalizarEspacios(request.getTemaSlogan()));
                temaEntity.setTemaPrimary(request.getTemaPrimary());
                temaEntity.setTemaSecondary(request.getTemaSecondary());
                temaEntity.setTemaTertiary(request.getTemaTertiary());

                return temaMapper.toResponse(temaRepository.save(temaEntity));
        }

        public TemaResponse obtenerPorEmpresaId(UUID empresaId) {
                Specification<TemaEntity> spec = (root, query, criteriaBuilder) -> criteriaBuilder
                                .equal(root.get("empresaId"), empresaId);

                TemaEntity entity = temaRepository.findOne(spec).orElseThrow(
                                () -> new EntityNotFoundException("No se encontró el tema de la empresa"));
                return temaMapper.toResponse(entity);
        }

        public Optional<TemaResponse> obtenerPorId(UUID temaId) {
                return temaRepository.findById(temaId)
                                .map(temaMapper::toResponse);
        }

        public TemaResponse cargarLogo(UUID empresaId, byte[] logoBytes) {
                String _nombreArchivo = null;

                try {
                        Specification<TemaEntity> spec = (root, query, criteriaBuilder) -> criteriaBuilder
                                        .equal(root.get("empresaId"), empresaId);

                        TemaEntity temaEntity = temaRepository.findOne(spec)
                                        .orElseThrow(() -> new EntityNotFoundException(
                                                        "No se encontró el tema de la empresa"));

                        String oldTemaUrl = temaEntity.getTemaLogoUrl();
                        if (oldTemaUrl != null && !oldTemaUrl.isEmpty()) {
                                storageLogo.eliminar(oldTemaUrl);
                        }

                        _nombreArchivo = storageLogo.guardar(logoBytes, "logo_" + empresaId + ".png");
                        temaEntity.setTemaLogoUrl(_nombreArchivo);

                        return temaMapper.toResponse(temaRepository.save(temaEntity));
                } catch (Exception e) {
                        /**
                         * Si ocurre un error, eliminamos el nuevo logo subido para evitar archivos
                         * huérfanos en el servidor
                         */
                        if (_nombreArchivo != null) {
                                storageLogo.eliminar(_nombreArchivo);
                        }
                        throw new RuntimeException("Error al actualizar el logo: " + e.getMessage());
                }
        }
}
