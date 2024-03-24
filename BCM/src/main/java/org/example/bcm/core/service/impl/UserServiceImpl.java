package org.example.bcm.core.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bcm.common.exception.ResourceNotFoundException;
import org.example.bcm.common.exception.ServiceNotFoundException;
import org.example.bcm.core.model.dto.request.update.UpdateUserCityRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateUserRequestDto;
import org.example.bcm.core.model.dto.response.UserSimpleResponseDto;
import org.example.bcm.core.model.entity.City;
import org.example.bcm.core.model.entity.Role;
import org.example.bcm.core.model.entity.User;
import org.example.bcm.core.model.mapper.UserMapper;
import org.example.bcm.core.repository.CityRepository;
import org.example.bcm.core.repository.RoleRepository;
import org.example.bcm.core.repository.TokenRepository;
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
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;

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

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        tokenRepository.deleteByUserId(userId);
        userRepository.delete(user);
    }

    @Override
    public UserSimpleResponseDto changeRole(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        Role managerRole = roleRepository.findByName("MANAGER").orElseThrow(() -> new ResourceNotFoundException("Role", "name", "MANAGER"));
        Role clientRole = roleRepository.findByName("CLIENT").orElseThrow(() -> new ResourceNotFoundException("Role", "name", "CLIENT"));

        user.setRole(user.getRole().getName().equals("MANAGER") ? clientRole : managerRole);
        User updatedUser = userRepository.save(user);
        return UserMapper.toDto(updatedUser);
    }


    @Override
    public UserSimpleResponseDto updateUser(UpdateUserRequestDto updateUserRequestDto) {
        User existingUser = userRepository.findById(updateUserRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", updateUserRequestDto.getUserId()));

        City city = cityRepository.findById(updateUserRequestDto.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException("City", "id", updateUserRequestDto.getCityId()));

        UserMapper.updateEntity(existingUser, updateUserRequestDto, city);

        User updatedUser = userRepository.save(existingUser);
        return UserMapper.toDto(updatedUser);
    }

    @Override
    public List<UserSimpleResponseDto> filterUsers(String firstNameOrLastName, Long cityId) {
        List<User> users = userRepository.filterUsers(firstNameOrLastName, cityId);

        if (users.isEmpty()) {
            throw new ServiceNotFoundException("No users found");
        }
        return users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }
}
