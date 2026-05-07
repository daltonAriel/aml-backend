package com.aml.app.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private jwtUtils jwtUtils;

    @Autowired
    private UserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = jwtUtils.getJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String email = jwtUtils.getUserEmailFromJwt(jwt);
                boolean isSaasAdmin = jwtUtils.getIsSaasFromJwt(jwt);
                UserDetails userDetails = null;

                // implementar redis para verificacion, si falla resids usar comprobacion en
                // base de datos(mas lenta)
                //

                if (isSaasAdmin) {
                    userDetails = userDetailService.loadUserByUsernameAdmin(email);
                } else {
                    userDetails = userDetailService.loadUserByUsernameUser(email);
                }

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Guardar la autenticación en el contexto
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (io.jsonwebtoken.JwtException e) {
            request.setAttribute("exception", e);
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);

    }

}
