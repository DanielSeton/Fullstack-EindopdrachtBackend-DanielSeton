package com.eindopdracht.DJCorner.services;

import com.eindopdracht.DJCorner.dtos.UserRequestDto;
import com.eindopdracht.DJCorner.exceptions.ResourceNotFoundException;
import com.eindopdracht.DJCorner.mappers.UserMapper;
import com.eindopdracht.DJCorner.models.User;
import com.eindopdracht.DJCorner.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserRequestDto userRequestDto) {
        return this.userRepository.save(UserMapper.toEntity(userRequestDto));
    }

    public User getSingleUser(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Object with id " + id + " not found"));
    }
}
