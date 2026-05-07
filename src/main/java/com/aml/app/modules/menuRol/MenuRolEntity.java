package com.aml.app.modules.menuRol;

import java.sql.Types;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.aml.app.modules.menu.MenuEntity;
import com.aml.app.modules.rol.RolEntity;
import com.aml.app.modules.usuarioRol.UsuarioRolId;

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
@Table(name = "MENU_ROLES")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuRolEntity {
    @EmbeddedId
    @Builder.Default
    private UsuarioRolId usuarioRolId = new UsuarioRolId();

    @Column(name = "MENU_ID", insertable = false, updatable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID menuId;

    @Column(name = "ROL_ID", insertable = false, updatable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID rolId;

    @ManyToOne
    @MapsId("menuId")
    @JoinColumn(name = "MENU_ID", referencedColumnName = "MENU_ID")
    private MenuEntity menu;

    @ManyToOne
    @MapsId("rolId")
    @JoinColumn(name = "ROL_ID", referencedColumnName = "ROL_ID")
    private RolEntity rol;
}
