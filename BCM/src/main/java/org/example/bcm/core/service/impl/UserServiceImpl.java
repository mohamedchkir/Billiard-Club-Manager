package org.example.bcm.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.bcm.common.exception.ResourceNotFoundException;
import org.example.bcm.common.exception.ServiceNotFoundException;
import org.example.bcm.core.model.dto.request.update.UpdateUserCityRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateUserRequestDto;
import org.example.bcm.core.model.dto.response.UserResponseDto;
import org.example.bcm.core.model.dto.response.UserSimpleResponseDto;
import org.example.bcm.core.model.entity.City;
import org.example.bcm.core.model.entity.User;
import org.example.bcm.core.model.mapper.UserMapper;
import org.example.bcm.core.repository.CityRepository;
import org.example.bcm.core.repository.UserRepository;
import org.example.bcm.core.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService,UserService {
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserSimpleResponseDto updateUserCity(UpdateUserCityRequestDto updateUserCityRequestDto) {
        User user = userRepository.findById(updateUserCityRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", updateUserCityRequestDto.getUserId()));

        user.setCity(cityRepository.findById(updateUserCityRequestDto.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException("City", "id", updateUserCityRequestDto.getCityId())));

        User updatedUser = userRepository.save(user);
        return UserMapper.toDto(updatedUser);
    }

    @Override
    public List<UserSimpleResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new ServiceNotFoundException("No users found");
        }
        return users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        userRepository.delete(user);
    }

    @Override
    public UserSimpleResponseDto updateUser(UpdateUserRequestDto updateUserRequestDto) {
        User existingUser = userRepository.findById(updateUserRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", updateUserRequestDto.getUserId()));

        City city = cityRepository.findById(updateUserRequestDto.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException("City", "id", updateUserRequestDto.getCityId()));

        UserMapper.updateEntity(existingUser, updateUserRequestDto,city);

        User updatedUser = userRepository.save(existingUser);
        return UserMapper.toDto(updatedUser);
    }

    @Override
    public List<UserSimpleResponseDto> filterUsers(String firstName, String lastName, Long cityId) {
        List<User> users = userRepository.filterUsers(firstName, lastName, cityId);

        if (users.isEmpty()) {
            throw new ServiceNotFoundException("No users found");
        }
        return users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }
}
