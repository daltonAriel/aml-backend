package com.aml.app.modules.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aml.app.config.response.ApiResponse;
import com.aml.app.modules.auth.dto.AdminJwtResponse;
import com.aml.app.modules.auth.dto.LoginRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth/admin")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AdminJwtResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        AdminJwtResponse jwtAdminJwtResponse = adminAuthService.login(loginRequest);
        return ResponseEntity.ok(ApiResponse.success(jwtAdminJwtResponse, "Login exitoso"));
    }


}
