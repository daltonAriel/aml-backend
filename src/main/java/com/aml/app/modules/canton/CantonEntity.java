package com.aml.app.modules.canton;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.aml.app.modules.parroquia.ParroquiaEntity;
import com.aml.app.modules.provincia.ProvinciaEntity;

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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "CANTONES")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CantonEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "CANTON_ID")
    private UUID cantonId;

    @Column(name = "CANTON_CODIGO", nullable = false, length = 10, unique = true)
    private String cantonCodigo;

    @Column(name = "CANTON_NOMBRE", nullable = false, length = 120)
    private String cantonNombre;

    @Column(name = "PROVINCIA_ID", insertable = false, updatable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private String provinciaId;

    // Relación con Provincia
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROVINCIA_ID", nullable = false)
    @ToString.Exclude
    private ProvinciaEntity provincia;

    //Relación con parroquias
    @OneToMany(mappedBy = "canton", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<ParroquiaEntity> parroquias = new ArrayList<>();;
}
