package com.aml.app.modules.transaccion;

import java.math.BigDecimal;
import java.sql.Types;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.aml.app.modules.agencia.AgenciaEntity;
import com.aml.app.modules.canal.CanalEntity;
import com.aml.app.modules.clientes.ClienteEntity;
import com.aml.app.modules.empresa.EmpresaEntity;
import com.aml.app.modules.tipoTransacciones.TipoTransacccionEntity;

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
@Table(name = "TRANSACCIONES")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransaccionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "TRANSACCION_ID", nullable = false)
    private UUID transaccionId;

    @Column(name = "TRANSACCION_NUMERO_DOCUMENTO")
    private String transaccionNumeroDocumento;

    @Column(name = "TRANSACCION_FECHA_TRANSACCION")
    private LocalDate transaccionFechaTransaccion;

    @Column(name = "TRANSACCION_NUMERO_TRANSACCION")
    private String transaccionNumeroTransaccion;

    @Column(name = "TRANSACCION_V_DEBITO")
    private BigDecimal TransaccionVDebito;

    @Column(name = "TRANSACCION_V_CREDITO")
    private BigDecimal transaccionVCredito;

    @Column(name = "TRANSACCION_V_EFECTIVO")
    private BigDecimal transaccionVEfectivo;

    @Column(name = "TRANSACION_V_CHEQUES")
    private BigDecimal transaccionVCheques;

    @Column(name = "TRANSACCION_V_BIENES")
    private BigDecimal transaccionVBienes;

    @Column(name = "TRANSACCION_V_TOTAL")
    private BigDecimal transaccionVTotal;

    @Column(name = "TRANSACCION_CODIGO_PRODUCTO")
    private String transaccionCodigoProducto;

    @Column(name = "TRANSACCION_NUMERO_CUENTA")
    private String transaccionNumeroCuenta;

    @Column(name = "TRANSACCION_CODIGO_MONEDA")
    private String transaccionCodigoMoneda;

    @Column(name = "TRANSACCION_DOCUMENTO_DEPOSITANTE")
    private String transaccionDocumentoDepositante;

    @Column(name = "TRANSACCION_NOMBRE_RAZON_SOCIAL_IDENTIFICACION")
    private String transaccionNombreRazonSocialIdentificacion;

    @Column(name = "TRANSACCION_CODIGO_SWIFT")
    private String transaccionCodigoSwift;

    @Column(name = "TRANSACCION_IMPUESTO_SALIDA_DIVISAS")
    private BigDecimal transaccionImpuestoSalidaDivisas;

    @Column(name = "CANAL_ID")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID canalId; // relacion

    @Column(name = "TRANSACCION_INSTITUCION_FINANCIERA")
    private String trasaccionInstitucionFinanciera;

    @Column(name = "TRANSACCION_CUENTA_ORDENANTE")
    private String trasaccionCuentaOrdenante;

    @Column(name = "TRANSACCION_PAIS_ORIGEN_DESTINO")
    private String trasaccionPaisOrigenDestino;

    @Column(name = "TRANSACCION_CANTON_CIUDAD")
    private String trasaccionCantonCiudad;

    @Column(name = "TRANSACCION_SALDO")
    private BigDecimal trasaccionSaldo;

    @Column(name = "EMPRESA_ID")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID empresaId; // relacion

    @Column(name = "AGENCIA_ID")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID agenciaId; // relacion

    @Column(name = "CLIENTE_ID")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID clienteId; // relacion

    @Column(name = "TIPO_TRANSACCION_ID")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID tipoTransaccionId; // relacion

    // RELACIONES

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPRESA_ID", referencedColumnName = "EMPRESA_ID", insertable = false, updatable = false)
    private EmpresaEntity empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AGENCIA_ID", referencedColumnName = "AGENCIA_ID", insertable = false, updatable = false)
    private AgenciaEntity agencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENTE_ID", referencedColumnName = "CLIENTE_ID", insertable = false, updatable = false)
    private ClienteEntity cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CANAL_ID", referencedColumnName = "CANAL_ID", insertable = false, updatable = false)
    private CanalEntity canal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_TRANSACCION_ID", referencedColumnName = "TIPO_TRANSACCION_ID", insertable = false, updatable = false)
    private TipoTransacccionEntity tipoTransaccion;
}
