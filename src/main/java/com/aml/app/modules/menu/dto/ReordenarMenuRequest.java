package com.aml.app.modules.menu.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ReordenarMenuRequest {
    private UUID[] listaMenuId;
}
