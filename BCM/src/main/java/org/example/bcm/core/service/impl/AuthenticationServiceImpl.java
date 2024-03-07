package org.example.bcm.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.bcm.core.model.dto.request.UserLoginRequestDto;
import org.example.bcm.core.model.dto.request.UserRegisterRequestDto;
import org.example.bcm.core.model.dto.response.AuthenticationResponseDto;
import org.example.bcm.core.model.entity.Role;
import org.example.bcm.core.model.entity.Token;
import org.example.bcm.core.model.entity.User;
import org.example.bcm.core.repository.UserRepository;
import org.example.bcm.core.service.AuthenticationService;
import org.example.bcm.core.service.TokenService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;


    @Override
    public AuthenticationResponseDto register(UserRegisterRequestDto registerDto) {
        registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        User user = modelMapper.map(registerDto, User.class);
        user.setRole(Role.builder().id(1L).build());

        User saved = userRepository.save(user);

        Token refreshToken = tokenService.generateRefreshToken(saved);

        String token = tokenService.generateToken(saved, refreshToken);

        return AuthenticationResponseDto.builder()
                .accessToken(token)
                .refreshToken(refreshToken.getToken())
                .tokenExpiration(tokenService.extractExpiration(token))
                .build();
    }


    @Override
    public AuthenticationResponseDto login(UserLoginRequestDto loginDto) {
        User userFromDb = userRepository
                .findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        Token refreshToken = tokenService.generateRefreshToken(userFromDb);

        String token = tokenService.generateToken(userFromDb, refreshToken);

        return AuthenticationResponseDto.builder()
                .accessToken(token)
                .refreshToken(refreshToken.getToken())
                .tokenExpiration(tokenService.extractExpiration(token))
                .build();
    }

    @Override
    public void logout(User user) {
        tokenService.revokeRefreshTokensByUser(user);
        SecurityContextHolder.clearContext();
    }

}
