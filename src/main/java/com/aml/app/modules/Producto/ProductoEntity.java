package com.aml.app.modules.Producto;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.aml.app.modules.agencia.AgenciaEntity;
import com.aml.app.modules.clientes.ClienteEntity;
import com.aml.app.modules.empresa.EmpresaEntity;

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
@Table(name = "PRODUCTOS")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "PRODUCTO_ID", nullable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID productoId;

    @Column(name = "PRODUCTO_ITEM", nullable = false)
    private Long productoItem;

    @Column(name = "CLIENTE_ID", nullable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID clienteId; // relacion

    @Column(name = "PRODUCTO_CL_TIPO_IDENTIFICA", nullable = false)
    private String productoClTipoIdentifica;

    @Column(name = "PRODUCTO_TIPO", nullable = false)
    private String productoTipo;

    @Column(name = "PRODUCTO_ESTADO", nullable = false)
    private String productoEstado;

    @Column(name = "PRODUCTO_NUMERO_CUENTA", nullable = false)
    private String productoNumeroCuenta;

    @Column(name = "PRODUCTO_FECHA_APERTURA", nullable = false)
    private LocalDateTime productoFechaApertura;

    @Column(name = "EMPRESA_ID", nullable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID empresaId;

    @Column(name = "AGENCIA_ID", nullable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID agenciaId;

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

}
