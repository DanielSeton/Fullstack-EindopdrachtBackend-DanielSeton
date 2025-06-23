package com.eindopdracht.DJCorner.mappers;

import com.eindopdracht.DJCorner.dtos.UserRequestDto;
import com.eindopdracht.DJCorner.dtos.UserResponseDto;
import com.eindopdracht.DJCorner.models.User;

public class UserMapper {

    public static User toEntity(UserRequestDto userRequestDto) {
        User user = new User();

        user.setUsername(userRequestDto.getUsername());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        user.setRole(userRequestDto.getRole());

        return user;
    }

    public static UserResponseDto toResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.id = user.getId();
        userResponseDto.username = user.getUsername();
        userResponseDto.email = user.getEmail();
        userResponseDto.password = user.getPassword();
        userResponseDto.role = user.getRole();

        return userResponseDto;
    }
}
