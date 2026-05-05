package com.aml.app.shared;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BulkErrorDetail<T> {
    private int fila;
    private String tipo;
    private String mensaje;

    private List<ClienteBulkError> campos;
    private T registro;
}
