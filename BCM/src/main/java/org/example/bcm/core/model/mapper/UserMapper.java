package org.example.bcm.core.model.mapper;

import lombok.AllArgsConstructor;
import org.example.bcm.common.exception.ResourceNotFoundException;
import org.example.bcm.core.model.dto.request.update.UpdateUserRequestDto;
import org.example.bcm.core.model.dto.response.UserSimpleResponseDto;
import org.example.bcm.core.model.entity.City;
import org.example.bcm.core.model.entity.User;
import org.example.bcm.core.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserMapper {

    public static UserSimpleResponseDto toDto(User user) {
        return UserSimpleResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .telephone(user.getTelephone())
                .email(user.getEmail())
                .numberOfToken(user.getNumberOfToken())
                .city(CityMapper.toDto(user.getCity()))
                .role(user.getRole().getName())
                .imageUrl(user.getImageUrl())
                .build();
    }
    public static void updateEntity(User existingUser, UpdateUserRequestDto updateUserRequestDto, City city) {
        existingUser.setFirstName(updateUserRequestDto.getFirstName());
        existingUser.setLastName(updateUserRequestDto.getLastName());
        existingUser.setTelephone(updateUserRequestDto.getTelephone());
        existingUser.setCity(city);
        existingUser.setNumberOfToken(updateUserRequestDto.getNumberOfToken());
        existingUser.setImageUrl(updateUserRequestDto.getImageUrl());
    }
}
