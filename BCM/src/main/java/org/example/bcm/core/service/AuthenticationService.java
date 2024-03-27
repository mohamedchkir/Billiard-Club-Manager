package org.example.bcm.core.service;

import org.example.bcm.core.model.dto.request.UserLoginRequestDto;
import org.example.bcm.core.model.dto.request.UserRegisterRequestDto;
import org.example.bcm.core.model.dto.response.AuthenticationResponseDto;
import org.example.bcm.core.model.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    AuthenticationResponseDto register(UserRegisterRequestDto user);

    AuthenticationResponseDto login(UserLoginRequestDto user);

    void logout(User user);
    void forgotPassword(String email);

    void resetPassword(String token, String password);
}
