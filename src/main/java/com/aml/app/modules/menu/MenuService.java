package com.aml.app.modules.menu;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aml.app.modules.menu.dto.CrearMenuRequest;
import com.aml.app.modules.menu.dto.MenuResponse;
import com.aml.app.modules.menu.dto.ReordenarMenuRequest;
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

    @Transactional(readOnly = true)
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

    @Transactional(rollbackFor = Exception.class)
    public void ReordenarMenu(ReordenarMenuRequest reordenarMenuRequest) {
        int contador = 0;
        for (UUID menuId : reordenarMenuRequest.getListaMenuId()) {
            MenuEntity menu = menuRepository.findById(menuId)
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró el menu"));
            menu.setMenuOrden(contador);
            menuRepository.save(menu);
            contador++;
        }
    }

    @Transactional
    public void actualizarMenu(UUID menuId, CrearMenuRequest menuRequest) {
        MenuEntity menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el menu"));
        menu.setMenuLabel(menuRequest.getMenuLabel());
        menu.setMenuUrl(menuRequest.getMenuUrl().replaceAll("\\s", ""));
        menu.setMenuIcon(menuRequest.getMenuIcon());
        menu.setMenuEstado(menuRequest.getMenuEstado());
        menuRepository.save(menu);
    }

}
