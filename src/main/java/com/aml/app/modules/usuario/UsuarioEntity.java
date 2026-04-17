package com.aml.app.modules.usuario;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.aml.app.modules.agencia.AgenciaEntity;
import com.aml.app.modules.departamento.DepartamentoEntity;
import com.aml.app.modules.empresa.EmpresaEntity;
import com.aml.app.modules.rol.RolEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "USUARIOS", uniqueConstraints = {
        // No puede haber dos personas con la misma cédula en la misma empresa
        @UniqueConstraint(name = "UK_USUARIO_CEDULA_EMPRESA", columnNames = { "USUARIO_CEDULA", "EMPRESA_ID" })
})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsuarioEntity {

    @Id
    @Column(name = "USUARIO_ID")
    @JdbcTypeCode(Types.VARCHAR)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID usuarioId;

    @NotBlank
    @Column(name = "USUARIO_NOMBRE", nullable = false, length = 50)
    private String usuarioNombre;

    @NotBlank
    @Column(name = "USUARIO_APELLIDO", nullable = false, length = 50)
    private String usuarioApellido;

    @NotBlank
    @Column(name = "USUARIO_CEDULA", nullable = false, length = 10)
    private String usuarioCedula;

    @NotBlank
    @Column(name = "USUARIO_EMAIL", unique = true, nullable = false)
    private String usuarioEmail;

    @NotBlank
    @Column(name = "USUARIO_TELEFONO", nullable = false, length = 15)
    private String usuarioTelefono;

    @NotBlank
    @Column(name = "USUARIO_CONTRASENA", nullable = false, length = 255)
    @JsonIgnore
    private String usuarioContrasena;

    @Column(name = "USUARIO_ESTADO", nullable = false)
    private boolean usuarioEstado;

    @Column(name = "REVOCATION_DATE")
    private LocalDateTime usuarioRevocationDate;

    @Column(name = "USUARIO_FECHA_CREACION", updatable = false)
    private LocalDateTime usuarioFechaCreacion;

    @Column(name = "EMPRESA_ID", nullable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID empresaId;

    @Column(name = "AGENCIA_ID", nullable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID agenciaId;

    @Column(name = "DEPARTAMENTO_ID", nullable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID departamentoId;

    @Column(name = "ROL_ID", nullable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID rolId;

    @PrePersist
    protected void onCreate() {
        this.usuarioFechaCreacion = LocalDateTime.now();

        /**
         * Actualizacion manual de la revocacion ej: contrasena, roles, estado
         */
        this.usuarioRevocationDate = LocalDateTime.now();

    }

    // Relaciones

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPRESA_ID", referencedColumnName = "EMPRESA_ID", insertable = false, updatable = false)
    private EmpresaEntity empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTAMENTO_ID", referencedColumnName = "DEPARTAMENTO_ID", insertable = false, updatable = false)
    private DepartamentoEntity departamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AGENCIA_ID", referencedColumnName = "AGENCIA_ID", insertable = false, updatable = false)
    private AgenciaEntity agencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROL_ID", referencedColumnName = "ROL_ID", insertable = false, updatable = false)
    private RolEntity rol;

}
