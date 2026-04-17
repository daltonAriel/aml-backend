package com.aml.app.modules.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aml.app.config.response.ApiResponse;
import com.aml.app.modules.auth.dto.LoginRequest;
import com.aml.app.modules.auth.dto.UserJwtRefresh;
import com.aml.app.modules.auth.dto.UserJwtRefreshRequest;
import com.aml.app.modules.auth.dto.UserJwtResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth/user")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserAuthService userAuthService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserJwtResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        UserJwtResponse jwtUserResponse = userAuthService.login(loginRequest);
        return ResponseEntity.ok(ApiResponse.success(jwtUserResponse, "Login exitoso"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<UserJwtRefresh>> refreshToken(
            @Valid @RequestBody UserJwtRefreshRequest userJwtRefreshRequest) {
        UserJwtRefresh userJwtRefresh = userAuthService.refreshToken(userJwtRefreshRequest.getRefreshToken());

        return ResponseEntity.ok(ApiResponse.success(userJwtRefresh, "Login exitoso"));
    }

}
