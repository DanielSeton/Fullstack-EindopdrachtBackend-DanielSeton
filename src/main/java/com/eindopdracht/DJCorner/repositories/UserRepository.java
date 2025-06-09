package com.eindopdracht.DJCorner.repositories;

import com.eindopdracht.DJCorner.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);
}
