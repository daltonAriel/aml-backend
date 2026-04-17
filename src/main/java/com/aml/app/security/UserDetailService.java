package com.aml.app.security;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aml.app.modules.usuario.UsuarioEntity;
import com.aml.app.modules.usuario.UsuarioRepository;
import com.aml.app.modules.usuarioAdmin.UsuarioAdminEntity;
import com.aml.app.modules.usuarioAdmin.UsuarioAdminRepository;
import com.aml.app.security.dto.UserDetailEmpresa;
import com.aml.app.security.dto.UserDetailSaaS;
import com.aml.app.security.exceptions.EmpresaBloqueada;
import com.aml.app.security.exceptions.UsuarioBloqueado;

@Service
public class UserDetailService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioAdminRepository usuarioAdminRepository;

    public UserDetailService(UsuarioRepository usuarioRepository, UsuarioAdminRepository usuarioAdminRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioAdminRepository = usuarioAdminRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UsuarioAdminEntity> usuarioAdminEntity = usuarioAdminRepository.findByUsuarioAdminEmail(email);
        if (usuarioAdminEntity.isPresent()) {
            UsuarioAdminEntity userAdmin = usuarioAdminEntity.get();

            UserDetailSaaS userAdminDto = UserDetailSaaS.builder()
                    .id(userAdmin.getUsuarioAdminId())
                    .email(userAdmin.getUsuarioAdminEmail())
                    .password(userAdmin.getUsuarioAdminContrasena())
                    .activo(true)
                    .build();

            return UserPrincipal.createSaaS(userAdminDto);
        }

        // USUARIOS NORMALES
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findByUsuarioEmail(email);
        if (usuarioEntity.isPresent()) {
            UsuarioEntity user = usuarioEntity.get();

            // ESTADO: Validación de Usuario del Cliente
            if (!user.getEmpresa().isEmpresaEstado()) {
                throw new EmpresaBloqueada("Acceso Denegado Empresa Bloqueada");
            }

            // ESTADO: Validación de Usuario del Cliente
            if (!user.isUsuarioEstado()) {
                throw new UsuarioBloqueado("Acceso Denegado Usuario Bloqueado");
            }

            UserDetailEmpresa userDto = UserDetailEmpresa.builder()
                    .id(user.getUsuarioId())
                    .email(user.getUsuarioEmail())
                    .password(user.getUsuarioContrasena())
                    .empresaId(user.getEmpresaId())
                    .estado(user.isUsuarioEstado())
                    .empresaEstado(user.getEmpresa().isEmpresaEstado())
                    .roles(Collections.singleton(user.getRol().getRolNombre()))
                    .build();

            return UserPrincipal.create(userDto);
        }

        throw new UsernameNotFoundException("Usuario no encontrado");
    }

    @Transactional(readOnly = false)
    public UserDetails loadUserByUsernameAdmin(String email) throws UsernameNotFoundException {
        Optional<UsuarioAdminEntity> usuarioAdminEntity = usuarioAdminRepository.findByUsuarioAdminEmail(email);
        if (usuarioAdminEntity.isPresent()) {
            UsuarioAdminEntity userAdmin = usuarioAdminEntity.get();

            UserDetailSaaS userAdminDto = UserDetailSaaS.builder()
                    .id(userAdmin.getUsuarioAdminId())
                    .email(userAdmin.getUsuarioAdminEmail())
                    .password(userAdmin.getUsuarioAdminContrasena())
                    .activo(true)
                    .build();

            return UserPrincipal.createSaaS(userAdminDto);
        }

        throw new UsernameNotFoundException("Usuario no encontrado");
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsernameUser(String email) throws UsernameNotFoundException {
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findByUsuarioEmail(email);
        if (usuarioEntity.isPresent()) {
            UsuarioEntity user = usuarioEntity.get();

            // ESTADO: Validación de Usuario del Cliente
            if (!user.getEmpresa().isEmpresaEstado()) {
                throw new EmpresaBloqueada("Acceso Denegado Empresa Bloqueada");
            }

            // ESTADO: Validación de Usuario del Cliente
            if (!user.isUsuarioEstado()) {
                throw new UsuarioBloqueado("Acceso Denegado Usuario Bloqueado");
            }

            UserDetailEmpresa userDto = UserDetailEmpresa.builder()
                    .id(user.getUsuarioId())
                    .email(user.getUsuarioEmail())
                    .password(user.getUsuarioContrasena())
                    .empresaId(user.getEmpresaId())
                    .estado(user.isUsuarioEstado())
                    .empresaEstado(user.getEmpresa().isEmpresaEstado())
                    .roles(Collections.singleton(user.getRol().getRolNombre()))
                    .build();

            return UserPrincipal.create(userDto);
        }

        throw new UsernameNotFoundException("Usuario no encontrado");
    }
}
