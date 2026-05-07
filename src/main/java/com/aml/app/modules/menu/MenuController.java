package com.aml.app.modules.menu;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aml.app.config.response.ApiResponse;
import com.aml.app.modules.menu.dto.CrearMenuRequest;
import com.aml.app.modules.menu.dto.MenuResponse;
import com.aml.app.modules.menu.dto.ReordenarMenuRequest;

import jakarta.validation.Valid;
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
        return ResponseEntity.ok(ApiResponse.success(menuResponse, "Menus obtenidos exitosamente"));
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<ApiResponse<MenuResponse>> obtenerMenuId(@PathVariable UUID menuId) {
        MenuResponse menuResponse = menuService.obtenerMenuPorId(menuId);
        return ResponseEntity.ok(ApiResponse.success(menuResponse, "Menu obtenido exitosamente"));
    }

    @PutMapping("/reordenar")
    public ResponseEntity<ApiResponse<Void>> reordenarMenu(
            @RequestBody ReordenarMenuRequest reordenarMenuRequest) {
        menuService.ReordenarMenu(reordenarMenuRequest);
        return ResponseEntity.ok(ApiResponse.success(null, "Menu reordenado exitosamente"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MenuResponse>> guardarMenu(@Valid @RequestBody CrearMenuRequest menuRequest) {
        MenuResponse menuEntity = menuService.guardarMenu(menuRequest);
        return ResponseEntity.ok(ApiResponse.success(menuEntity, "Menu guardado exitosamente"));
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<ApiResponse<Void>> actualizarMenu(@PathVariable UUID menuId,
            @Valid @RequestBody CrearMenuRequest menuRequest) {
        menuService.actualizarMenu(menuId, menuRequest);
        return ResponseEntity.ok(ApiResponse.success(null, "Menu eliminado exitosamente"));
    }

}
