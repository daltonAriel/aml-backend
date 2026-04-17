package com.aml.app.config.errorManager;

import lombok.Getter;

@Getter
public class RiseException extends RuntimeException {
    private final ErrorCatalog error;

    public RiseException(ErrorCatalog error) {
        super(error.getMessage());
        this.error = error;
    }

    /**
     * para levantar el error con un mensaje personalizado
     */
    public RiseException(ErrorCatalog error, String customMessage) {
        super(customMessage);
        this.error = error;
    }
}
