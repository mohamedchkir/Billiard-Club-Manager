package org.example.bcm.core.service;

import org.example.bcm.core.model.dto.request.update.UpdateUserCityRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateUserRequestDto;
import org.example.bcm.core.model.dto.response.UserSimpleResponseDto;

import java.util.List;

public interface UserService {

    UserSimpleResponseDto updateUserCity(UpdateUserCityRequestDto updateUserCityRequestDto);

    List<UserSimpleResponseDto> getAllUsers();

    void deleteUser(Long userId);

    UserSimpleResponseDto changeRole(Long userId);

    UserSimpleResponseDto updateUser(UpdateUserRequestDto updateUserRequestDto);

    List<UserSimpleResponseDto> filterUsers(String firstName, String lastName, Long cityId);

}
