package com.aml.app.modules.canton;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CantonRepository extends JpaRepository<CantonEntity, UUID>, JpaSpecificationExecutor<CantonEntity> {

}
