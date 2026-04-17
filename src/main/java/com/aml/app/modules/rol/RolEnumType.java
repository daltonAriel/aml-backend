package com.aml.app.modules.rol;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RolEnumType {
    ROLE_ADMIN("Administrador"),
    ROLE_OC_TITULAR("Oficial de Cumplimiento - Titular"),
    ROLE_OC_CONSULTAS("Oficial de Cumplimiento - Consultas"),
    ROLE_JEFE_OPERATIVO("Jefe Operativo"),
    ROLE_ASISTENTE_OPERATIVO("Asistente Operativo"),
    ROLE_COACTIVAS("Coactivas"),
    ROLE_LEGAL("Área Legal"),
    ROLE_TALENTO_HUMANO("Talento Humano"),
    ROLE_CONTABILIDAD("Contabilidad"),
    ROLE_SERVICIO_CLIENTE("Servicio al Cliente"),
    ROLE_NEGOCIOS("Negocios"),
    ROLE_ASESOR_CUENTA("Asesor de Cuenta"),
    ROLE_COORDINADOR("Coordinador"),
    ROLE_GERENCIA("Gerencia");

    private final String descripcion;
}
