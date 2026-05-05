package com.aml.app.modules.agencia;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.aml.app.modules.empresa.EmpresaEntity;
import com.aml.app.modules.parroquia.ParroquiaEntity;
import com.aml.app.modules.usuario.UsuarioEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "AGENCIAS", uniqueConstraints = {
        // Primera restricción: Código único por empresa
        @UniqueConstraint(name = "UK_AGENCIA_EMPRESA_CODIGO", columnNames = { "EMPRESA_ID", "AGENCIA_CODIGO" }),
        // Segunda restricción: Nombre único por empresa
        @UniqueConstraint(name = "UK_AGENCIA_EMPRESA_NOMBRE", columnNames = { "EMPRESA_ID", "AGENCIA_NOMBRE" })
})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AgenciaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "AGENCIA_ID")
    private UUID agenciaId;

    @Column(name = "EMPRESA_ID", nullable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID empresaId;

    @Column(name = "AGENCIA_CODIGO", nullable = false, length = 10, unique = true)
    private String agenciaCodigo;

    @Column(name = "AGENCIA_NOMBRE", nullable = false)
    private String agenciaNombre;

    @Column(name = "PARROQUIA_ID")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID parroquiaId;

    @Column(name = "AGENCIA_ESTADO", nullable = false)
    private boolean agenciaEstado;

    @Column(name = "FECHA_CREACION", nullable = false, updatable = false)
    private LocalDateTime agenciaFechaCreacion;

    @PrePersist
    protected void onCreate() {
        this.agenciaFechaCreacion = LocalDateTime.now();
    }

    // relaciones
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARROQUIA_ID", referencedColumnName = "PARROQUIA_ID", insertable = false, updatable = false)
    @ToString.Exclude
    private ParroquiaEntity parroquia;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "EMPRESA_ID", referencedColumnName = "EMPRESA_ID", insertable = false, updatable = false)
    @ToString.Exclude
    private EmpresaEntity empresa;

    @OneToMany(mappedBy = "agencia", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<UsuarioEntity> usuarios = new ArrayList<>();
}
