package com.eindopdracht.DJCorner.mappers;

import com.eindopdracht.DJCorner.dtos.UserRequestDto;
import com.eindopdracht.DJCorner.dtos.UserResponseDto;
import com.eindopdracht.DJCorner.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User toEntity(UserRequestDto userRequestDto) {
        User user = new User();

        user.setUsername(userRequestDto.getUsername());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        user.setRole(userRequestDto.getRole());

        return user;
    }

    public static User toEntityAdmin(UserRequestDto userRequestDto) {
        User user = new User();

        user.setUsername(userRequestDto.getUsername());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        user.setRole("ADMIN");

        return user;
    }

    public static UserResponseDto toResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setId(user.getId());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setRole(user.getRole());

        return userResponseDto;
    }
}
