package com.eindopdracht.DJCorner.repositories;

import com.eindopdracht.DJCorner.models.PlaylistTrack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack, Long> {
    Optional<PlaylistTrack> findById(Long id);
}
