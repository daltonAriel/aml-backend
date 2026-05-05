package com.aml.app.modules.patrimonio;

import java.math.BigDecimal;
import java.sql.Types;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.aml.app.modules.clientes.ClienteEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "PATRIMONIOS")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PatrimonioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "PATRIMONIO_ID", nullable = false)
    private UUID patrimonioId;

    @Column(name = "CLIENTE_ID", nullable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID clienteId;

    @Column(name = "PATRIMONIO_NUMERO_DOCUMENTO", nullable = false)
    private String patrimonioNumeroDocumento;

    @Column(name = "PATRIMONIO_FECHA_REGISTRO", nullable = false)
    private LocalDate patrimonioFechaRegistro;

    @Column(name = "PATRIMONIO_MONTO", nullable = false)
    private BigDecimal patrimonioMonto;

    @Column(name = "PATRIMONIO_ESTADO", nullable = false)
    private String patrimonioEstado;

    // RELACION
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENTE_ID", referencedColumnName = "CLIENTE_ID", insertable = false, updatable = false)
    @ToString.Exclude
    private ClienteEntity cliente;
}
