package com.aml.app.modules.empresa;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.aml.app.modules.agencia.AgenciaEntity;
import com.aml.app.modules.departamento.DepartamentoEntity;
import com.aml.app.modules.parroquia.ParroquiaEntity;
import com.aml.app.modules.tema.TemaEntity;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "EMPRESAS")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmpresaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "EMPRESA_ID", nullable = false)
    private UUID empresaId;

    @Column(name = "EMPRESA_CODIGO", unique = true)
    private String empresaCodigo;

    @Column(name = "EMPRESA_RUC", unique = false, nullable = false, length = 13)
    private String empresaRuc;

    @Column(name = "EMPRESA_NOMBRE", nullable = false)
    private String empresaNombre;

    @Column(name = "EMPRESA_SIGLAS", nullable = true)
    private String empresaSiglas;

    // --- UBICACIÓN ---
    @Column(name = "PARROQUIA_ID")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID parroquiaId;

    // --- CONTACTO ---
    @Column(name = "EMPRESA_TELEFONO", nullable = false)
    private String empresaTelefono;

    @Column(name = "EMPRESA_EMAIL", nullable = false)
    private String empresaEmail;

    @Column(name = "EMPRESA_WEB", nullable = true)
    private String empresaWeb;

    // --- ESTADO Y CONTROL ---
    @Column(name = "EMPRESA_ESTADO", nullable = true)
    private boolean empresaEstado;

    @Column(name = "EMPRESA_FECHA_CREACION", updatable = false, nullable = false)
    private LocalDateTime empresaFechaCreacion;

    @PrePersist
    protected void onCreate() {
        this.empresaFechaCreacion = LocalDateTime.now();
    }

    // Relaciones

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<DepartamentoEntity> departamentos = new ArrayList<>();

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<AgenciaEntity> agencias = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARROQUIA_ID", referencedColumnName = "PARROQUIA_ID", insertable = false, updatable = false)
    @ToString.Exclude
    private ParroquiaEntity parroquia;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<UsuarioEntity> usuarios = new ArrayList<>();

    @OneToOne(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private TemaEntity tema;

}
