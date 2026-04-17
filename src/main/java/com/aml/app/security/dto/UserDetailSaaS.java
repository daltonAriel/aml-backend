package com.aml.app.security.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailSaaS {
private UUID id;
    private String email;
    private String password;
    private boolean activo;
}
