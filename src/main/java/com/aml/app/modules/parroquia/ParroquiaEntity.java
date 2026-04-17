package com.aml.app.modules.parroquia;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.aml.app.modules.agencia.AgenciaEntity;
import com.aml.app.modules.canton.CantonEntity;
import com.aml.app.modules.empresa.EmpresaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "PARROQUIAS")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ParroquiaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "PARROQUIA_ID")
    private UUID parroquiaId;

    @Column(name = "PARROQUIA_CODIGO", nullable = false, length = 10, unique = true)
    private String parroquiaCodigo;

    @Column(name = "PARROQUIA_NOMBRE", nullable = false, length = 150)
    private String parroquiaNombre;

    
    @Column(name = "CANTON_ID", insertable = false, updatable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID cantonId;


    // Relaciónes
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CANTON_ID", nullable = false)
    @ToString.Exclude
    private CantonEntity canton;

    @OneToMany(mappedBy = "parroquia", fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<EmpresaEntity> empresas = new ArrayList<>();

    // Una parroquia puede tener muchas agencias físicas
    @OneToMany(mappedBy = "parroquia", fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<AgenciaEntity> agencias = new ArrayList<>();

}
