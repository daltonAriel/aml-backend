package com.aml.app.modules.auth;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aml.app.modules.auth.dto.AdminJwtResponse;
import com.aml.app.modules.auth.dto.LoginRequest;
import com.aml.app.security.UserDetailService;
import com.aml.app.security.jwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminAuthService {

    private final jwtUtils jwtUtils;
    private final UserDetailService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AdminJwtResponse login(LoginRequest loginRequest) {
        UserDetails userDetails = userDetailsService.loadUserByUsernameAdmin(loginRequest.getEmail());
        if (passwordEncoder.matches(loginRequest.getContrasena(), userDetails.getPassword())) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    userDetails.getPassword(),
                    userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateAdminJwtToken(authentication);

            AdminJwtResponse adminJwtResponse = new AdminJwtResponse(jwt);
            return adminJwtResponse;

        } else {
            throw new BadCredentialsException("Contraseña incorrecta");
        }
    }

}
