package com.aml.app.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.aml.app.security.dto.UserDetailEmpresa;
import com.aml.app.security.dto.UserDetailSaaS;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.ToString;

@ToString
public class UserPrincipal implements UserDetails {

    private final UUID id;
    private final String username; // Usamos email como username
    @JsonIgnore
    private final String password;
    private final UUID empresaId; // NULL para el Super Rol
    private final boolean isSaasAdmin;
    private final boolean usuarioEstado;
    private final boolean empresaEstado;
    private final Collection<? extends GrantedAuthority> authorities;

    private final String usuarioNombre;
    private final String usuarioApellido;
    private final Set<String> usuarioRolDescripcion;

    // Constructor privado inmutable
    private UserPrincipal(UUID id, String email, String password, UUID empresaId,
            boolean isSaasAdmin, boolean estado, boolean empresaEstado,
            Collection<? extends GrantedAuthority> authorities, String usuarioNombre,
            String usuarioApellido, Set<String> usuarioRolDescripcion) {
        this.id = id;
        this.username = email;
        this.password = password;
        this.empresaId = empresaId;
        this.isSaasAdmin = isSaasAdmin;
        this.usuarioEstado = estado;
        this.empresaEstado = empresaEstado;
        this.authorities = authorities;
        this.usuarioNombre = usuarioNombre;
        this.usuarioApellido = usuarioApellido;
        this.usuarioRolDescripcion = usuarioRolDescripcion;
    }

    // Método para crear el Super Usuario (SaaS Owner)
    public static UserPrincipal createSaaS(UserDetailSaaS dto) {
        List<GrantedAuthority> authorities = Collections.singletonList(
                // Definimos el rol de super admin
                new SimpleGrantedAuthority("ROLE_SAAS_ADMIN"));

        return new UserPrincipal(
                dto.getId(),
                dto.getEmail(),
                dto.getPassword(),
                null, // No pertenece a ninguna empresa
                true,
                true, // Para el admin, el sistema siempre está "activo"
                true,
                authorities,
                dto.getNombre(),
                dto.getApellido(),
                dto.getRolDescripcion());
    }

    // Método para crear el Usuario de Cliente
    public static UserPrincipal create(UserDetailEmpresa dto) {
        // Convertimos los Enums en GrantedAuthorities de Spring
        Set<GrantedAuthority> authorities = dto.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority(rol.name()))
                .collect(Collectors.toSet());

        return new UserPrincipal(
                dto.getId(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getEmpresaId(),
                false,
                dto.isEstado(),
                dto.isEmpresaEstado(),
                authorities,
                dto.getNombre(),
                dto.getApellido(),
                dto.getRolDescripcion());
    }

    // --- Métodos obligatorios de UserDetails ---
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return usuarioEstado && empresaEstado;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // Getters para auditoría y lógica de negocio
    public UUID getId() {
        return id;
    }

    public UUID getEmpresaId() {
        return empresaId;
    }

    public boolean isSaasAdmin() {
        return isSaasAdmin;
    }

    public boolean isEmpresaEstado() {
        return empresaEstado;
    }

    public boolean isEstado() {
        return usuarioEstado;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public String getUsuarioApellido() {
        return usuarioApellido;
    }

    public Set<String> getUsuarioRolDescripcion() {
        return usuarioRolDescripcion;
    }

}
