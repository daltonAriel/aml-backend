package com.aml.app.modules.clientes.dto;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CrearClienteRequest {
    @NotBlank(message = "El codigo del cliente es obligatorio")
    private String clienteCodigo;

    @NotNull(message = "El id de la empresa es obligatorio")
    private UUID empresaId;

    @NotBlank(message = "El tipo de identificacion es obligatorio")
    private String clienteTipoIdentifica;

    @NotBlank(message = "El numero de identificacion es obligatorio")
    @Size(min = 5, max = 20)
    private String clienteIdentifica;

    @NotBlank(message = "El primer nombre es obligatorio")
    private String clienteNombrePrimer;

    @NotBlank(message = "El segundo nombre es obligatorio")
    private String clienteNombreSegundo;

    @NotBlank(message = "El primer apellido es obligatorio")
    private String clienteApellidoPrimer;

    @NotBlank(message = "El segundo apellido es obligatorio")
    private String clienteApellidoSegundo;

    @NotBlank(message = "El nombre es obligatorio")
    private String clienteNombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String clienteApellidos;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es valido")
    private String clienteEmail;

    @NotBlank(message = "El telefono es obligatorio")
    @Size(min = 7, max = 15, message = "El telefono debe tener entre 7 y 15 caracteres")
    private String clienteTelefono;

    @NotBlank(message = "El numero celular es obligatorio")
    @Size(min = 7, max = 15, message = "El celular debe tener entre 7 y 15 caracteres")
    private String clienteCelular;

    @NotBlank(message = "La provincia es obligatoria")
    private String clienteProvincia;

    @NotBlank(message = "El canton es obligatorio")
    private String clienteCanton;

    @NotBlank(message = "La parroquia es obligatoria")
    private String clienteDireccion;

    @NotBlank(message = "La calle principal es obligatoria")
    private String clienteCallePrincipal;

    @NotBlank(message = "La calle transversal es obligatoria")
    private String clienteCalleTransversal;

    @NotBlank(message = "El numero de casa es obligatorio")
    private String clienteNroCasa;

    @NotBlank(message = "La referencia es obligatoria")
    private String clienteReferenciaUbicDir;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate clienteFechaNacimiento;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    private LocalDate clienteFechaIngreso;

    @NotNull(message = "La fecha de constitucion es obligatoria")
    private LocalDate clienteFechaConstitucion;

    @NotBlank(message = "El sexo es obligatorio")
    private String clienteSexo;

    @NotBlank(message = "El estado civil es obligatorio")
    private String clienteEstadoCivil;

    @NotBlank(message = "El codigo de provincia es obligatorio")
    private String codigoProvincia;

    @NotBlank(message = "El codigo de canton es obligatorio")
    private String codigoCiudad;
}
