package com.aml.app.modules.usuarioAdmin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ActualizarUsuarioAdminRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String usuarioAdminNombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String usuarioAdminApellido;

    @NotBlank(message = "La cedula es obligatoria")
    @Pattern(regexp = "^[0-9]{10}$", message = "La cedula debe solo debe tener digitos numéricos")
    private String usuarioAdminCedula;

    @NotBlank(message = "El teléfono de contacto es obligatorio")
    private String usuarioAdminTelefono;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    private String usuarioAdminEmail;

}
