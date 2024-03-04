package org.example.bcm.core.service;

import org.example.bcm.core.model.dto.request.UserLoginDTO;
import org.example.bcm.core.model.dto.request.UserRegisterDTO;
import org.example.bcm.core.model.dto.response.AuthenticationDto;
import org.example.bcm.core.model.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    AuthenticationDto register(UserRegisterDTO user);

    AuthenticationDto login(UserLoginDTO user);

    void logout(User user);
}
