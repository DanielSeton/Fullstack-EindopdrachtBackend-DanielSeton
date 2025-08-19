package com.eindopdracht.DJCorner.services;

import com.eindopdracht.DJCorner.exceptions.ResourceNotFoundException;
import com.eindopdracht.DJCorner.models.PlaylistTrack;
import com.eindopdracht.DJCorner.repositories.PlaylistTrackRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaylistTrackService {

    private final PlaylistTrackRepository playlistTrackRepository;

    public PlaylistTrackService(PlaylistTrackRepository playlistTrackRepository) {
        this.playlistTrackRepository = playlistTrackRepository;
    }

    public PlaylistTrack findByIdOrThrow(Long id) {
        return playlistTrackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Track with id: " + id + " not found"));
    }
}
