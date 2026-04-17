package com.aml.app.modules.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.aml.app.modules.auth.dto.LoginRequest;
import com.aml.app.modules.auth.dto.UserJwtRefresh;
import com.aml.app.modules.auth.dto.UserJwtResponse;
import com.aml.app.security.UserDetailService;
import com.aml.app.security.jwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAuthService {

    private final jwtUtils jwtUtils;
    private final UserDetailService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public UserJwtResponse login(LoginRequest loginRequest) {
        UserDetails userDetails = userDetailsService.loadUserByUsernameUser(loginRequest.getEmail());

        if (passwordEncoder.matches(loginRequest.getContrasena(), userDetails.getPassword())) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    userDetails.getPassword(),
                    userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            String refreshToken = jwtUtils.generateRefreshToken(authentication);

            UserJwtResponse userJwtResponse = new UserJwtResponse(jwt, refreshToken);
            return userJwtResponse;

        } else {
            throw new BadCredentialsException("Contraseña incorrecta");
        }
    }

    public UserJwtRefresh refreshToken(String refreshToken) {

        if (refreshToken != null && jwtUtils.validateJwtToken(refreshToken)) {

            String email = jwtUtils.getUserEmailFromJwt(refreshToken);
            Boolean isRefreshTokek = jwtUtils.isRefreshToken(refreshToken);
            if (!isRefreshTokek) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "El token no es un Refresh Token válido");
            }

            UserDetails userDetails = userDetailsService.loadUserByUsernameUser(email);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            // 5. Generamos el nuevo Access Token
            String newAccessToken = jwtUtils.generateJwtToken(authentication);

            UserJwtRefresh userJwtRefresh = new UserJwtRefresh(newAccessToken);

            return userJwtRefresh;
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "El token no es valido");
    }

}
