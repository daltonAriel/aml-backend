package com.aml.app.modules.departamento;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.aml.app.modules.empresa.EmpresaEntity;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "DEPARTAMENTOS")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DepartamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "DEPARTAMENTO_ID", nullable = false)
    private UUID departamentoId;

    @Column(name = "EMPRESA_ID", nullable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID empresaId;

    @Column(name = "DEPARTAMENTO_NOMBRE", nullable = false, length = 100)
    private String departamentoNombre;

    @Column(name = "DEPARTAMENTO_DESCRIPCION", length = 255)
    private String departamentoDescripcion;

    @Column(name = "DEPARTAMENTO_ESTADO", nullable = false)
    private boolean departamentoEstado;

    @Column(name = "DEPARTAMENTO_FECHA_CREACION", nullable = false)
    private LocalDateTime departamentoFechaCreacion;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPRESA_ID", referencedColumnName = "EMPRESA_ID", insertable = false, updatable = false)
    @ToString.Exclude
    private EmpresaEntity empresa;

    @PrePersist
    protected void onCreate() {
        this.departamentoFechaCreacion = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<UsuarioEntity> usuarios = new ArrayList<>();

}
