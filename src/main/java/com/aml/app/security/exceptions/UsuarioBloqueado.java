package com.aml.app.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UsuarioBloqueado extends AuthenticationException {
    public UsuarioBloqueado(String msg) {
        super(msg);
    }
}
