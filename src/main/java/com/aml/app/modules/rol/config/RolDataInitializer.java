package com.aml.app.modules.rol.config;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.aml.app.modules.rol.RolEntity;
import com.aml.app.modules.rol.RolEnumType;
import com.aml.app.modules.rol.RolRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@Order(1)
@RequiredArgsConstructor
public class RolDataInitializer {
    private final RolRepository rolRepository;

    @Bean
    CommandLineRunner initRoles() {
        return args -> {
            System.out.println("Iniciando sincronización de roles...");

            for (RolEnumType rolEnum : RolEnumType.values()) {

                Optional<RolEntity> rolExistente = rolRepository.findFirstByRolNombre(rolEnum);

                if (rolExistente.isEmpty()) {
                    // NO EXISTE -> INSERTAR
                    RolEntity nuevoRol = RolEntity.builder()
                            .rolNombre(rolEnum)
                            .rolDescripcion(rolEnum.getDescripcion())
                            .build();
                    rolRepository.save(nuevoRol);
                } else {
                    // VERIFICAR SI HAY QUE ACTUALIZAR
                    RolEntity rol = rolExistente.get();
                    // Si el nombre visual en el Enum cambió, actualizamos la DB
                    if (!rol.getRolDescripcion().equals(rolEnum.getDescripcion())) {
                        rol.setRolDescripcion(rolEnum.getDescripcion());
                        rolRepository.save(rol);
                    }
                }
            }
            System.out.println("Sincronización de roles completada.");
        };
    }
}
