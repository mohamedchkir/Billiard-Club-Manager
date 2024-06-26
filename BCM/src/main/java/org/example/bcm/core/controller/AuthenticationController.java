package org.example.bcm.core.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bcm.core.model.dto.request.RefreshTokenRequestDto;
import org.example.bcm.core.model.dto.request.UserLoginRequestDto;
import org.example.bcm.core.model.dto.request.UserRegisterRequestDto;
import org.example.bcm.core.model.dto.response.ApiResponse;
import org.example.bcm.core.model.dto.response.AuthenticationResponseDto;
import org.example.bcm.core.model.dto.response.UserResponseDto;
import org.example.bcm.core.model.entity.Permission;
import org.example.bcm.core.model.entity.User;
import org.example.bcm.core.service.AuthenticationService;
import org.example.bcm.core.service.TokenService;
import org.example.bcm.shared.Const.AppEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppEndpoint.AUTHENTICATION_ENDPOINT)
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@Valid @RequestBody UserRegisterRequestDto user) {
        return ResponseEntity.ok(
                authenticationService.register(user)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@Valid @RequestBody UserLoginRequestDto user) {
        return ResponseEntity.ok(
                authenticationService.login(user)
        );
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return ResponseEntity.ok(
                tokenService.generateNewAccessToken(refreshTokenRequestDto.getRefreshToken())
        );
    }

    @GetMapping("/info")
    public ResponseEntity<UserResponseDto> getUser() throws RuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof User user) {
            return ResponseEntity.ok(
                    UserResponseDto.builder()
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .email(user.getEmail())
                            .telephone(user.getTelephone())
                            .numberOfToken(user.getNumberOfToken())
                            .password(user.getPassword())
                            .permissions(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                            .role(user.getRole().getName())
                            .city(user.getCity().getName())
                            .imageUrl(user.getImageUrl())
                            .build()
            );
        }

        return ResponseEntity.badRequest().body(
                UserResponseDto.builder()
                        .email("anonymous")
                        .role("anonymous")
                        .build()
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof User user) {
            authenticationService.logout(user);

            return ResponseEntity.ok(
                    Map.of("message", "You have been logged out")
            );
        }

        return ResponseEntity.badRequest().body(
                Map.of("message", "You are not logged in")
        );
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<?>> forgotPassword(@RequestParam String email) {
        authenticationService.forgotPassword(email);
        return ResponseEntity.ok(
                ApiResponse.success("An email has been sent to reset your password", null)
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<?>> resetPassword(@RequestParam String token, @RequestParam String password) {
        authenticationService.resetPassword(token, password);
        return ResponseEntity.ok(
                ApiResponse.success("Password has been reset", null)
        );
    }
}
