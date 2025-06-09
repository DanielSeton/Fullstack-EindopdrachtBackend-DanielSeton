package com.eindopdracht.DJCorner.repositories;

import com.eindopdracht.DJCorner.models.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
