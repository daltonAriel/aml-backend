package com.aml.app.modules.parroquia;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ParroquiaRepository extends JpaRepository<ParroquiaEntity, UUID>, JpaSpecificationExecutor<ParroquiaEntity> {

}
