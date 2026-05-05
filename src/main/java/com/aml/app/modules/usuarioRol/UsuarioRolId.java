package com.aml.app.modules.usuarioRol;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRolId implements Serializable {

    @Column(name = "USUARIO_ID")
    private UUID userId;

    @Column(name = "ROL_ID")
    private UUID roleId;

}
