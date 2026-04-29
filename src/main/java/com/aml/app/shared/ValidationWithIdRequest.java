package com.aml.app.shared;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ValidationWithIdRequest {
    private UUID id;
    private String valor;
}
