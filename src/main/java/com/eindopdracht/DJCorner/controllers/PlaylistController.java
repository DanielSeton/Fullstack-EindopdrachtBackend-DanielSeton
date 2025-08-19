package com.eindopdracht.DJCorner.controllers;

import com.eindopdracht.DJCorner.models.Playlist;
import com.eindopdracht.DJCorner.repositories.PlaylistRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistRepository playlistRepository;

    public PlaylistController(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    //todo playlist tracks gelijk toevoegen aan playlist met postrequest hier

    @GetMapping
    public ResponseEntity<List<Playlist>> getAllPlaylists() {
        return ResponseEntity.ok(playlistRepository.findAll());
    }


    @PostMapping
    public ResponseEntity<Playlist> createPlaylist(@RequestBody Playlist playlist) {
        return ResponseEntity.ok(playlistRepository.save(playlist));
    }


}
