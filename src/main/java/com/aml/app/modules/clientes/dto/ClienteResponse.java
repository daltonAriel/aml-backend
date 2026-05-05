package com.aml.app.modules.clientes.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponse {
    private UUID clienteId;

    private String clienteCodigo;
    private UUID empresaId;

    private String clienteTipoIdentifica;
    private String clienteIdentifica;

    private String clienteNombrePrimer;
    private String clienteNombreSegundo;
    private String clienteApellidoPrimer;
    private String clienteApellidoSegundo;

    private String clienteNombre;
    private String clienteApellidos;

    private String clienteEmail;
    private String clienteTelefono;
    private String clienteCelular;

    private String clienteProvincia;
    private String clienteCanton;

    private String clienteDireccion;
    private String clienteCallePrincipal;
    private String clienteCalleTransversal;
    private String clienteNroCasa;
    private String clienteReferenciaUbicDir;

    private LocalDate clienteFechaNacimiento;
    private LocalDate clienteFechaIngreso;
    private LocalDate clienteFechaConstitucion;

    private String clienteSexo;
    private String clienteEstadoCivil;

    private String codigoProvincia;
    private String codigoCiudad;
}
