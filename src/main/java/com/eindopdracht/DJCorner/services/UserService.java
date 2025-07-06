package com.eindopdracht.DJCorner.services;

import com.eindopdracht.DJCorner.dtos.UserRequestDto;
import com.eindopdracht.DJCorner.dtos.UserResponseDto;
import com.eindopdracht.DJCorner.exceptions.ResourceNotFoundException;
import com.eindopdracht.DJCorner.mappers.UserMapper;
import com.eindopdracht.DJCorner.models.User;
import com.eindopdracht.DJCorner.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserRequestDto userRequestDto) {
        return this.userRepository.save(UserMapper.toEntity(userRequestDto));
    }

    public List<UserResponseDto> getAllUsers(){
        List<User> userList = userRepository.findAll();
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();

        for (User user : userList) {
            UserResponseDto userResponseDto = UserMapper.toResponseDto(user);
            userResponseDtoList.add(userResponseDto);
        }
        return userResponseDtoList;
    }

    public User getSingleUser(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found"));
    }

    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User with name: " + username + " not found"));
    }

    public void deleteSingleUser(Long id) {
        Optional<User> userOptional = this.userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with id: " + id + " not found");
        }

        userRepository.deleteById(id);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found"));

        user.setUsername(userRequestDto.getUsername());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());

        User updatedUser = this.userRepository.save(user);

        return UserMapper.toResponseDto(updatedUser);

    }

    public UserResponseDto patchUser(Long id,UserRequestDto userRequestDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found"));


        if (userRequestDto.getUsername() != null) {
            user.setUsername(userRequestDto.getUsername());
        }
        if (userRequestDto.getEmail() != null) {
            user.setEmail(userRequestDto.getEmail());
        }
        if (userRequestDto.getPassword() != null) {
            user.setPassword(userRequestDto.getPassword());
        }

        User returnUser = userRepository.save(user);
        return UserMapper.toResponseDto(returnUser);
    }

}
