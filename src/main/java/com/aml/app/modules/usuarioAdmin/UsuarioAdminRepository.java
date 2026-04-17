package com.aml.app.modules.usuarioAdmin;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioAdminRepository
        extends JpaRepository<UsuarioAdminEntity, UUID>, JpaSpecificationExecutor<UsuarioAdminEntity> {

                Optional<UsuarioAdminEntity> findByUsuarioAdminEmail(String email);
}
