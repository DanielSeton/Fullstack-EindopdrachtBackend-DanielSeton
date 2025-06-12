package com.eindopdracht.DJCorner.controllers;


import com.eindopdracht.DJCorner.dtos.UserRequestDto;
import com.eindopdracht.DJCorner.dtos.UserResponseDto;
import com.eindopdracht.DJCorner.mappers.UserMapper;
import com.eindopdracht.DJCorner.models.User;
import com.eindopdracht.DJCorner.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers() {
//        return ResponseEntity.ok(userRepository.findAll());
//    }
//
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserByID(@PathVariable Long id) {
        return ResponseEntity.ok(UserMapper.toResponseDto(this.service.getSingleUser(id)));
    }
//
//    @GetMapping("/{userName}")
//    public ResponseEntity<User> getUserByUsername(@PathVariable String userName) {
//        Optional<User> user = userRepository.findByUserName(userName);
//
//        if (user.isPresent()) {
//            return ResponseEntity.ok(user.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }



    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        User user = this.service.createUser(userRequestDto);
        UserResponseDto userResponseDto = UserMapper.toResponseDto(user);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + user.getId()).toUriString());

        return ResponseEntity.created(uri).body(userResponseDto);
    }


//    @DeleteMapping("/{id}")
//    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
//        Optional<User> user = userRepository.findById(id);
//
//        if (user.isPresent()) {
//            userRepository.deleteById(id);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.noContent().build();
//    }
//
//
//
//    @PutMapping("/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User newUser) {
//        Optional<User> user = userRepository.findById(id);
//
//        if (user.isEmpty()) {
//            return ResponseEntity.notFound().build();
//
//        } else {
//            User user1 = user.get();
//
//            user1.setUserName(newUser.getUserName());
//            user1.setEmail(newUser.getEmail());
//            user1.setPassword(newUser.getPassword());
//
//            User returnUser = userRepository.save(user1);
//
//            return ResponseEntity.ok().body(returnUser);
//        }
//    }
//
//
//
//    @PatchMapping("/{id}")
//    public ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody User newUser) {
//        Optional<User> user = userRepository.findById(id);
//
//        if (user.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        } else {
//            User user1 = user.get();
//            if (newUser.getUserName() != null) {
//                user1.setUserName(newUser.getUserName());
//            }
//            if (newUser.getEmail() != null) {
//                user1.setEmail(newUser.getEmail());
//            }
//            if (newUser.getPassword() != null) {
//                user1.setPassword(newUser.getPassword());
//            }
//
//            User returnUser = userRepository.save(user1);
//            return ResponseEntity.ok().body(returnUser);
//        }
//    }
}
