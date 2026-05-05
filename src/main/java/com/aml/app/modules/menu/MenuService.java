package com.aml.app.modules.menu;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aml.app.modules.menu.dto.CrearMenuRequest;
import com.aml.app.modules.menu.dto.MenuResponse;
import com.aml.app.modules.menu.mappers.MenuMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    @Transactional(readOnly = true)
    public List<MenuResponse> obtenerMenus() {
        return menuRepository.findByMenuIdPadreIsNullOrderByMenuOrdenAsc().stream().map(menuMapper::toResponse)
                .toList();
    }

    public MenuResponse obtenerMenuPorId(UUID menuId) {
        return menuRepository.findById(menuId).map(menuMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el menu"));
    }

    @Transactional
    public MenuResponse guardarMenu(CrearMenuRequest menuRequest) {

        if (menuRequest.getMenuIdPadre() == null) {
            int orden = menuRepository.findTop1ByMenuIdPadreIsNullOrderByMenuOrdenDesc()
                    .map(MenuEntity::getMenuOrden).orElse(0);

            menuRequest.setMenuOrden(orden + 1);
            MenuEntity menuEntity = menuMapper.toEntity(menuRequest);

            menuEntity = menuRepository.save(menuEntity);
            return menuMapper.toResponse(menuEntity);

        } else {
            int orden = menuRepository
                    .findTop1ByMenuIdPadreOrderByMenuOrdenDesc(menuRequest.getMenuIdPadre())
                    .map(MenuEntity::getMenuOrden)
                    .orElse(0);
            menuRequest.setMenuOrden(orden + 1);
            MenuEntity menuEntity = menuMapper.toEntity(menuRequest);
            menuEntity = menuRepository.save(menuEntity);
            return menuMapper.toResponse(menuEntity);
        }
    }

}
