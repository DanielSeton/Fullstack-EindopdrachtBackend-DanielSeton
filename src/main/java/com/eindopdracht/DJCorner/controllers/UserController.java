package com.eindopdracht.DJCorner.controllers;


import com.eindopdracht.DJCorner.dtos.UserRequestDto;
import com.eindopdracht.DJCorner.dtos.UserResponseDto;
import com.eindopdracht.DJCorner.helpers.UriHelper;
import com.eindopdracht.DJCorner.mappers.UserMapper;
import com.eindopdracht.DJCorner.models.User;
import com.eindopdracht.DJCorner.security.MyUserDetails;
import com.eindopdracht.DJCorner.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserByID(
            @PathVariable Long id,
            @AuthenticationPrincipal MyUserDetails userDetails) {
        UserResponseDto user = userService.getUserById(id, userDetails);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(UserMapper.toResponseDto(this.userService.getUserByUsername(username)));
    }


    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        User user = this.userService.createUser(userRequestDto);
        UserResponseDto userResponseDto = UserMapper.toResponseDto(user);

        URI uri = UriHelper.buildResourceUri(user.getId());

        return ResponseEntity.created(uri).body(userResponseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createUserWithRole(@Valid @RequestBody UserRequestDto userRequestDto) {
        User user = userService.createUserWithRole(userRequestDto);
        UserResponseDto userResponseDto = UserMapper.toResponseDto(user);

        URI uri = UriHelper.buildResourceUri(user.getId());

        return ResponseEntity.created(uri).body(userResponseDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {

        userService.deleteSingleUser(id);

        return ResponseEntity.noContent().build();
    }



    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDto userRequestDto) {

        UserResponseDto updatedUser = userService.updateUser(id, userRequestDto);

        return ResponseEntity.ok(updatedUser);
    }



    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> patchUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto updatedUser = userService.patchUser(id, userRequestDto);
        return ResponseEntity.ok(updatedUser);
    }
}
