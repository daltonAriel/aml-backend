package com.aml.app.modules.agencia;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AgenciaRepository extends JpaRepository<AgenciaEntity, UUID>, JpaSpecificationExecutor<AgenciaEntity> {

}
