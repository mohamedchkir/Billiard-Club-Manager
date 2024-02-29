package org.example.bcm.core.service;

import org.example.bcm.core.model.dto.response.AuthenticationDto;
import org.example.bcm.core.model.entity.Token;
import org.example.bcm.core.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface TokenService {
    String generateToken(UserDetails userDetails, Token refreshToken);

    boolean isTokenValid(String token, UserDetails userDetails);

    String extractUsername(String token);

    Date extractExpiration(String token);

    Token generateRefreshToken(User user);

    void revokeRefreshTokensByUser(User user);

    AuthenticationDto generateNewAccessToken(String refreshToken);
}
