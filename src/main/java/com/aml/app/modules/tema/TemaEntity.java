package com.aml.app.modules.tema;

import java.sql.Types;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.aml.app.modules.empresa.EmpresaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TEMAS")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TemaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "TEMA_ID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "EMPRESA_ID", nullable = false, insertable = false, updatable = false, unique = true)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID empresaId;

    @Column(name = "SLOGAN", length = 255)
    private String slogan;

    @Column(name = "PRIMARY", length = 7)
    private String primary;

    @Column(name = "SECONDARY", length = 7)
    private String secondary;

    @Column(name = "TERTIARY", length = 7)
    private String tertiary;

    @Column(name = "LOGO_URL")
    private String logoUrl;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "EMPRESA_ID", referencedColumnName = "EMPRESA_ID", nullable = false, unique = true)
    private EmpresaEntity empresa;

}
