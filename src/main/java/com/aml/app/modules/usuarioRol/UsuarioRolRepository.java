package com.aml.app.modules.usuarioRol;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRolEntity, UsuarioRolId> {

}
