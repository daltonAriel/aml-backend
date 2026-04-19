package com.aml.app.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class jwtUtils {
    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.admin-expiration-ms}")
    private final int jwtAdminExpirationMs = 900000;

    @Value("${security.jwt.expiration-ms}")
    private final int jwtExpirationMs = 900000;

    @Value("${security.jwt.refreshexpiration-ms}")
    private final int refreshExpirationMs = 900000;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Genera un token JWT
     */
    public String generateJwtToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .claim("usuarioId", userPrincipal.getId())
                .claim("isSaas", userPrincipal.isSaasAdmin())
                .claim("empresaId", userPrincipal.getEmpresaId()) // UUID de la empresa
                .claim("empresaEstado", userPrincipal.isEmpresaEstado())
                .claim("roles", userPrincipal.getAuthorities())
                .claim("usuarioNombre", userPrincipal.getUsuarioNombre())
                .claim("usuarioApellido", userPrincipal.getUsuarioApellido())
                .claim("usuarioRolDescripcion", userPrincipal.getUsuarioRolDescripcion())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateAdminJwtToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .claim("usuarioId", userPrincipal.getId())
                .claim("isSaas", userPrincipal.isSaasAdmin())
                .claim("empresaId", userPrincipal.getEmpresaId()) // UUID de la empresa
                .claim("empresaEstado", userPrincipal.isEmpresaEstado())
                .claim("roles", userPrincipal.getAuthorities())
                .claim("usuarioNombre", userPrincipal.getUsuarioNombre())
                .claim("usuarioApellido", userPrincipal.getUsuarioApellido())
                .claim("usuarioRolDescripcion", userPrincipal.getUsuarioRolDescripcion())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtAdminExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Genera un token de actualización
     */
    public String generateRefreshToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .claim("refreshToken", true)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + refreshExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Obtiene el Email de usuario a partir del token JWT
     */
    public String getUserEmailFromJwt(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Obtiene el ID de usuario a partir del token JWT
     */
    public String getUserIdFromJwt(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("usuarioId").toString();
    }

    /**
     * Obtenemos si el token es de un Admin Saas
     * 
     * @param token
     * @return
     */
    public boolean getIsSaasFromJwt(String token) {
        Boolean isSaas = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("isSaas", Boolean.class);

        return isSaas != null && isSaas;
    }

    /**
     * Obtenemos si el token es RefreshToken su valor es booleano
     */

    public boolean isRefreshToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("refreshToken", Boolean.class);
    }

    /**
     * Obtiene el token JWT de la solicitud HTTP
     */
    public String getJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    /**
     * Valida el token JWT
     */

    public boolean validateJwtToken(String authToken) {
        Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(authToken);
        return true;
    }
}
