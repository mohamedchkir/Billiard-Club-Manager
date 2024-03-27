package org.example.bcm.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.bcm.common.exception.DataBaseConstraintException;
import org.example.bcm.common.exception.NotAllowedToJoinException;
import org.example.bcm.core.model.dto.request.UserLoginRequestDto;
import org.example.bcm.core.model.dto.request.UserRegisterRequestDto;
import org.example.bcm.core.model.dto.response.AuthenticationResponseDto;
import org.example.bcm.core.model.entity.Role;
import org.example.bcm.core.model.entity.Token;
import org.example.bcm.core.model.entity.User;
import org.example.bcm.core.repository.TokenRepository;
import org.example.bcm.core.repository.UserRepository;
import org.example.bcm.core.service.AuthenticationService;
import org.example.bcm.core.service.TokenService;
import org.example.bcm.shared.Enum.TokenType;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;



    @Override
    public AuthenticationResponseDto register(UserRegisterRequestDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new NotAllowedToJoinException("Email already exists");
        }

        registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        User user = modelMapper.map(registerDto, User.class);
        user.setRole(Role.builder().id(2L).build());
        user.setNumberOfToken(0);
        user.setImageUrl("https://static-00.iconduck.com/assets.00/profile-default-icon-2048x2045-u3j7s5nj.png");

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

    @Override
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        tokenService.revokeRefreshTokensByUser(user);

        Token token = Token.builder()
                .token(Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()))
                .revoked(false)
                .expiryDate(Instant.now().plusSeconds(60 * 60))
                .type(TokenType.RESET_PASSWORD)
                .user(user)
                .build();

        emailService.sendResetLinkPassword(user.getEmail(), "http://localhost:4200/reset-password?token=" + token.getToken());

        tokenRepository.save(token);
    }

    @Override
    public void resetPassword(String token, String password) {
        Token resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new DataBaseConstraintException("Invalid token"));

        if (resetToken.getExpiryDate().isBefore(Instant.now()) || resetToken.getRevoked()) {
            throw new DataBaseConstraintException("Token has expired or has been revoked");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
        tokenService.revokeRefreshTokensByUser(user);
    }

}
