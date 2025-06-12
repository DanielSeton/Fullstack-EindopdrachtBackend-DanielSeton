package com.eindopdracht.DJCorner.mappers;

import com.eindopdracht.DJCorner.dtos.UserRequestDto;
import com.eindopdracht.DJCorner.dtos.UserResponseDto;
import com.eindopdracht.DJCorner.models.User;

public class UserMapper {

    public static User toEntity(UserRequestDto userRequestDto) {
        User user = new User(userRequestDto.userName, userRequestDto.email, userRequestDto.password);
        return user;
    }

    public static UserResponseDto toResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.userName = user.getUserName();
        userResponseDto.email = user.getEmail();
        userResponseDto.password = user.getPassword();
        return userResponseDto;
    }
}
