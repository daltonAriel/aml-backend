package com.aml.app.modules.clientes;

import java.sql.Types;
import java.time.LocalDate;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "CLIENTES", uniqueConstraints = {
        @UniqueConstraint(name = "uk_cliente_empresa", columnNames = { "CLIENTE_CODIGO", "EMPRESA_CODIGO" })
})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "CLIENTE_ID", nullable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID clienteId;

    @Column(name = "CLIENTE_CODIGO")
    private String clienteCodigo;
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "EMPRESA_ID")

    private UUID empresaId;

    @Column(name = "CLIENTE_TIPO_IDENTIFICA")
    private String clienteTipoIdentifica;

    @Column(name = "CLIENTE_IDENTIFICA")
    private String clienteIdentifica;

    @Column(name = "CLIENTE_NOMBRE_PRIMER")
    private String clienteNombrePrimer;
    @Column(name = "CLIENTE_NOMBRE_SEGUNDO")
    private String clienteNombreSegundo;
    @Column(name = "CLIENTE_APELLIDO_PRIMER")
    private String clienteApellidoPrimer;
    @Column(name = "CLIENTE_APELLIDO_SEGUNDO")
    private String clienteApellidoSegundo;

    @Column(name = "CLIENTE_NOMBRE")
    private String clienteNombre;
    @Column(name = "CLIENTE_APELLIDOS")
    private String clienteApellidos;

    @Column(name = "CLIENTE_EMAIL")
    private String clienteEmail;
    @Column(name = "CLIENTE_TELEFONO")
    private String clienteTelefono;
    @Column(name = "CLIENTE_CELULAR")
    private String CLIENTE_CELULAR;

    @Column(name = "CLIENTE_PROVINCIA")
    private String clienteProvincia;
    @Column(name = "CLIENTE_CANTON")
    private String clienteCanton;

    @Column(name = "CLIENTE_DIRECCION")
    private String clienteDireccion;
    @Column(name = "CLIENTE_CALLE_PRINCIPAL")
    private String clienteCallePrincipal;
    @Column(name = "CLIENTE_CALLE_TRANSVERSAL")
    private String clienteCalleTransversal;
    @Column(name = "CLIENTE_NRO_CASA")
    private String clienteNroCasa;
    @Column(name = "CLIENTE_REFERENCIA_UBIC_DIR")
    private String clienteReferenciaUbicDir;

    @Column(name = "CLIENTE_FECHA_NACIMIENTO")
    private LocalDate clienteFechaNacimiento;
    @Column(name = "CLIENTE_FECHA_INGRESO")
    private LocalDate clienteFechaIngreso;
    @Column(name = "CLIENTE_FECHA_CONSTITUCION")
    private LocalDate clienteFechaConstitucion;

    @Column(name = "CLIENTE_SEXO")
    private String clienteSexo;
    @Column(name = "CLIENTE_ESTADO_CIVIL")
    private String clienteEstadoCivil;

    @Column(name = "CODIGO_PROVINCIA")
    private String codigoProvincia;
    @Column(name = "CODIGO_CIUDAD")
    private String codigoCiudad;

    // RELACION
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPRESA_ID", referencedColumnName = "EMPRESA_ID", insertable = false, updatable = false)
    private EmpresaEntity empresa;

}
