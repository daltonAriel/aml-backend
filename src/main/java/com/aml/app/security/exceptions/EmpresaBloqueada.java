package com.aml.app.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class EmpresaBloqueada extends AuthenticationException {
    public EmpresaBloqueada(String msg) {
        super(msg);
    }
    
}
