package com.eindopdracht.DJCorner.services;

import com.eindopdracht.DJCorner.dtos.UserRequestDto;
import com.eindopdracht.DJCorner.dtos.UserResponseDto;
import com.eindopdracht.DJCorner.exceptions.ResourceNotFoundException;
import com.eindopdracht.DJCorner.mappers.UserMapper;
import com.eindopdracht.DJCorner.models.User;
import com.eindopdracht.DJCorner.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final List<String> ALLOWED_ROLES = List.of("USER", "STAFF", "ADMIN");

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(UserRequestDto userRequestDto) {
        String role = userRequestDto.getRole();

        if (role != null && !ALLOWED_ROLES.contains(role.toUpperCase())) {
            throw new ResourceNotFoundException("Invalid role: " + role);
        }

        if (role == null || role.isBlank()) {
            userRequestDto.setRole("USER");
        } else {
            userRequestDto.setRole(role.toUpperCase());
        }

        User user = UserMapper.toEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return this.userRepository.save(user);
    }

    public User createUserWithRole(UserRequestDto userRequestDto) {
        String role = userRequestDto.getRole();

        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("Role cannot be null or empty");
        }

        if (!ALLOWED_ROLES.contains(role.toUpperCase())) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        userRequestDto.setRole(role.toUpperCase());
        User user = UserMapper.toEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return this.userRepository.save(user);
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
