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
    private UUID temaId;

    @Column(name = "EMPRESA_ID", nullable = false, insertable = false, updatable = false, unique = true)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID empresaId;

    @Column(name = "TEMA_SLOGAN", length = 255)
    private String temaSlogan;

    @Column(name = "TEMA_PRIMARY", length = 7)
    private String temaPrimary;

    @Column(name = "TEMA_SECONDARY", length = 7)
    private String temaSecondary;

    @Column(name = "TEMA_TERTIARY", length = 7)
    private String temaTertiary;

    @Column(name = "TEMA_LOGO_URL")
    private String temaLogoUrl;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "EMPRESA_ID", referencedColumnName = "EMPRESA_ID", nullable = false, unique = true)
    private EmpresaEntity empresa;

}
