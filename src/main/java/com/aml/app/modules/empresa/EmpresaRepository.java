package com.aml.app.modules.empresa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<EmpresaEntity, UUID>, JpaSpecificationExecutor<EmpresaEntity> {
    boolean existsByEmpresaNombreIgnoreCase(String empresaNombre);

    boolean existsByEmpresaNombreIgnoreCaseAndEmpresaIdNot(String empresaNombre, UUID empresaId);

    boolean existsByEmpresaCodigoIgnoreCase(String empresaRuc);

    boolean existsByEmpresaCodigoIgnoreCaseAndEmpresaIdNot(String empresaRuc, UUID empresaId);

    Optional<EmpresaEntity> findByEmpresaCodigo(String empresaCodigo);
}
