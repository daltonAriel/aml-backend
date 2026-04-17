package com.aml.app.modules.usuarioAdmin;

import java.sql.Types;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USUARIOS_ADMIN")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "USUARIO_ADMIN_ID", nullable = false)
    private UUID usuarioAdminId;

    @NotBlank
    @Column(name = "USUARIO_ADMIN_NOMBRE", nullable = false, length = 50)
    private String usuarioAdiminNombre;

    @NotBlank
    @Column(name = "USUARIO_ADMIN_APELLIDO", nullable = false, length = 50)
    private String usuarioAdminApellido;

    @NotBlank
    @Column(name = "USUARIO_ADMIN_CEDULA", nullable = false, length = 10, unique = true)
    private String usuarioAdminCedula;

    @NotBlank
    @Column(name = "USUARIO_ADMIN_TELEFONO", nullable = false, length = 15)
    private String usuarioAdminTelefono;

    @NotBlank
    @Column(name = "USUARIO_ADMIN_EMAIL", unique = true, nullable = false)
    private String usuarioAdminEmail;

    @NotBlank
    @Column(name = "USUARIO_ADMIN_CONTRASENA", nullable = false, length = 255)
    private String usuarioAdminContrasena;

}
