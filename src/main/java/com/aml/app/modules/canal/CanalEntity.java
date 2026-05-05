package com.aml.app.modules.canal;

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
@Table(name = "CANALES")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CanalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "CANAL_ID", nullable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID calalId;

    private String canalNumeroDocumento;
    private String canal;
    private String canalEstado;
}
