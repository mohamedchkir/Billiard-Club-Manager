package org.example.bcm.core.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bcm.core.model.dto.request.update.UpdateUserCityRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateUserRequestDto;
import org.example.bcm.core.model.dto.response.UserSimpleResponseDto;
import org.example.bcm.core.service.UserService;
import org.example.bcm.shared.Const.AppEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppEndpoint.USER_ENDPOINT)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserSimpleResponseDto>> getAllUsers() {
        List<UserSimpleResponseDto> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/update-city")
    public ResponseEntity<UserSimpleResponseDto> updateCityForCurrentUser(
            @RequestBody UpdateUserCityRequestDto updateCityRequestDto
    ) {
        UserSimpleResponseDto updatedUser = userService.updateUserCity(updateCityRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/update")
    public ResponseEntity<UserSimpleResponseDto> updateUser(@Valid
            @RequestBody UpdateUserRequestDto updateUserRequestDto
    ) {
        UserSimpleResponseDto updatedUser = userService.updateUser(updateUserRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }
}
