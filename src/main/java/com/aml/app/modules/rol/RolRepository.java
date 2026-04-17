package com.aml.app.modules.rol;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<RolEntity, UUID>, JpaSpecificationExecutor<RolEntity> {

    public Optional<RolEntity> findFirstByRolNombre(RolEnumType rolEnum);

}
