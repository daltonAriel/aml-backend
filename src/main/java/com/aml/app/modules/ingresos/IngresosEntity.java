package com.aml.app.modules.ingresos;

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
@Table(name = "INGRESOS")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IngresosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "INGRESO_ID", nullable = false)
    private UUID ingresoId;

    @Column(name = "CLIENTE_ID", nullable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID clienteId;

    @Column(name = "INGRESO_NUMERO_DOCUMENTO", nullable = false)
    private String ingresoNumeroDocumento;

    @Column(name = "INGRESO_FECHA_REGISTRO", nullable = false)
    private LocalDate ingresoFechaRegistro;

    @Column(name = "INGRESO_TIPO_INGRESO", nullable = false)
    private Integer ingresoTipoIngreso;

    @Column(name = "INGRESO_MONTO", nullable = false)
    private BigDecimal ingresoMonto;

    @Column(name = "INGRESO_ESTADO", nullable = false)
    private String ingresoEstado;

    // Relaciones
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENTE_ID", referencedColumnName = "CLIENTE_ID", insertable = false, updatable = false)
    @ToString.Exclude
    private ClienteEntity cliente;
}
