package com.aml.app.modules.usuarioRol;

import java.util.UUID;

import com.aml.app.modules.rol.RolEntity;
import com.aml.app.modules.usuario.UsuarioEntity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class UsuarioRolEntity {
    @EmbeddedId
    private UsuarioRolId usuarioRolId = new UsuarioRolId();

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID")
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "ROL_ID", referencedColumnName = "ROL_ID")
    private RolEntity rol;

    @Column(name = "USUARIO_ID", insertable = false, updatable = false)
    private UUID userId;

    @Column(name = "ROL_ID", insertable = false, updatable = false)
    private UUID roleId;
}
