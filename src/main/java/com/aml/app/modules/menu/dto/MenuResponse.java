package com.aml.app.modules.menu.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MenuResponse {

    private UUID menuId;

    private UUID menuIdPadre;

    private List<MenuResponse> subMenus;

    private String menuLabel;
    private String menuUrl;
    private String menuIcon;
    private Integer menuOrden;
    private Boolean menuEstado;
}
