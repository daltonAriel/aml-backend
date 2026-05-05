package com.aml.app.modules.tipoTransacciones;

import java.sql.Types;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TIPO_TRANSACCION")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TipoTransacccionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "TIPO_TRANSACCION_ID", nullable = false)
    private UUID tipoTransaccionId;

    @Column(name = "TIPO_TRANSACCION_NOMBRE", nullable = false)
    private String tipoTransaccionNombre;

    @Column(name = "TIPO_TRANSACCION_DESCRIPCION", nullable = false)
    private String tipoTransaccionDescripcion;

    @Column(name = "TIPO_TRANSACCION_ESTADO", nullable = false)
    private boolean tipoTransaccionEstado;

}
