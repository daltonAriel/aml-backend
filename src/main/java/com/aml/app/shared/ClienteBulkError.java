package com.aml.app.shared;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteBulkError {
    private String campo;
    private String tipo;
    private String mensaje;
}
