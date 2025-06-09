package com.eindopdracht.DJCorner.controllers;


import com.eindopdracht.DJCorner.models.User;
import com.eindopdracht.DJCorner.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userName}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String userName) {
        Optional<User> user = userRepository.findByUserName(userName);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        this.userRepository.save(user);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + user.getId()).toUriString());

        return ResponseEntity.created(uri).body(user);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            userRepository.deleteById(id);
        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }



    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User newUser) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();

        } else {
            User user1 = user.get();

            user1.setUserName(newUser.getUserName());
            user1.setEmail(newUser.getEmail());
            user1.setPassword(newUser.getPassword());

            User returnUser = userRepository.save(user1);

            return ResponseEntity.ok().body(returnUser);
        }
    }



    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody User newUser) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            User user1 = user.get();
            if (newUser.getUserName() != null) {
                user1.setUserName(newUser.getUserName());
            }
            if (newUser.getEmail() != null) {
                user1.setEmail(newUser.getEmail());
            }
            if (newUser.getPassword() != null) {
                user1.setPassword(newUser.getPassword());
            }

            User returnUser = userRepository.save(user1);
            return ResponseEntity.ok().body(returnUser);
        }
    }
}
