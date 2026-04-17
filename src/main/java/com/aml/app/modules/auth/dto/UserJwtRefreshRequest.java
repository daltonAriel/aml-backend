package com.aml.app.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserJwtRefreshRequest {

    @NotBlank(message = "El refresh token es obligatorio")
    private String refreshToken;

}
