package com.aml.app.modules.menu;

import java.sql.Types;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "MENUS")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "MENU_ID")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID menuId;

    @Column(name = "MENU_ID_PADRE", nullable = true)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID menuIdPadre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID_PADRE", referencedColumnName = "MENU_ID", insertable = false, updatable = false)
    private MenuEntity menuPadre;

    @OneToMany(mappedBy = "menuPadre", cascade = CascadeType.ALL)
    @OrderBy("menuOrden Asc")
    private List<MenuEntity> subMenus;

    private String menuLabel;
    private String menuUrl;
    private String menuIcon;
    private Integer menuOrden;
    private Boolean menuEstado;
}
