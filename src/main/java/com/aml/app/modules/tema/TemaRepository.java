package com.aml.app.modules.tema;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TemaRepository extends JpaRepository<TemaEntity, UUID>, JpaSpecificationExecutor<TemaEntity> {
    
}
