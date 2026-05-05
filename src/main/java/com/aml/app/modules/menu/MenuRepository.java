package com.aml.app.modules.menu;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, UUID> {
    List<MenuEntity> findByMenuIdPadreIsNullOrderByMenuOrdenAsc();

    Optional<MenuEntity> findTop1ByMenuIdPadreIsNullOrderByMenuOrdenDesc();

    Optional<MenuEntity> findTop1ByMenuIdPadreOrderByMenuOrdenDesc(UUID menuIdPadre);

}
