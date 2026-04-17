package com.aml.app.modules.rol;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.aml.app.modules.usuario.UsuarioEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ROLES")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "ROL_ID", nullable = false)
    private UUID rolId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROL_NOMBRE", nullable = false, length = 50)
    private RolEnumType rolNombre;

    // Nombre descriptivo del ENUM
    @Column(name = "ROL_DESCRIPCION", nullable = false, length = 100)
    private String rolDescripcion;

    /**
     * Callbacks
     * Este método garantiza que el nombre visual siempre coincida con el Enum
     */

    @PrePersist
    protected void onCreate() {
        this.sincronizarDescripcion();
    }

    @PreUpdate
    protected void onUpdate() {
        this.sincronizarDescripcion();
    }

    private void sincronizarDescripcion() {
        if (this.rolDescripcion != null) {
            // Forzamos la sincronización con el ENUM
            this.rolDescripcion = this.rolNombre.getDescripcion();
        }
    }

    //Relaciones

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<UsuarioEntity> usuarios = new ArrayList<>();
}
