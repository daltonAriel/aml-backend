package com.aml.app.modules.usuarioRol;

import java.sql.Types;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.aml.app.modules.rol.RolEntity;
import com.aml.app.modules.usuario.UsuarioEntity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "USUARIO_ROLES")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsuarioRolEntity {
    @EmbeddedId
    @Builder.Default
    private UsuarioRolId usuarioRolId = new UsuarioRolId();

    @Column(name = "USUARIO_ID", insertable = false, updatable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID userId;

    @Column(name = "ROL_ID", insertable = false, updatable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID roleId;

    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID")
    private UsuarioEntity usuario;

    @ManyToOne
    @MapsId("rolId")
    @JoinColumn(name = "ROL_ID", referencedColumnName = "ROL_ID")
    private RolEntity rol;

}
