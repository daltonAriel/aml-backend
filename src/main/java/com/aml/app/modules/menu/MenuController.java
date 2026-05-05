package com.aml.app.modules.menu;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aml.app.config.response.ApiResponse;
import com.aml.app.modules.menu.dto.MenuResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/menu")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MenuController {

    private final MenuService menuService;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<MenuResponse>>> obtenerMenu() {
        List<MenuResponse> menuResponse = menuService.obtenerMenus();
        return ResponseEntity.ok(ApiResponse.success(menuResponse, "Menus obtenidas exitosamente"));
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<ApiResponse<MenuResponse>> getMethodName(@PathVariable UUID menuId) {
        MenuResponse menuResponse = menuService.obtenerMenuPorId(menuId);
        return ResponseEntity.ok(ApiResponse.success(menuResponse, "Menu obtenido exitosamente"));
    }

}
