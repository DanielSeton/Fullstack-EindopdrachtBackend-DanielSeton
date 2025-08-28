package com.eindopdracht.DJCorner.controllers;

import com.eindopdracht.DJCorner.dtos.PlaylistRequestDto;
import com.eindopdracht.DJCorner.dtos.PlaylistResponseDto;
import com.eindopdracht.DJCorner.dtos.PlaylistTrackRequestDto;
import com.eindopdracht.DJCorner.dtos.PlaylistTrackResponseDto;
import com.eindopdracht.DJCorner.helpers.UriHelper;
import com.eindopdracht.DJCorner.mappers.PlaylistMapper;
import com.eindopdracht.DJCorner.mappers.PlaylistTrackmapper;
import com.eindopdracht.DJCorner.models.Playlist;
import com.eindopdracht.DJCorner.models.PlaylistTrack;
import com.eindopdracht.DJCorner.repositories.PlaylistRepository;
import com.eindopdracht.DJCorner.services.PlaylistService;
import com.eindopdracht.DJCorner.services.PlaylistTrackService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistRepository playlistRepository;
    private final PlaylistService playlistService;
    private final PlaylistTrackService playlistTrackService;
    private final PlaylistTrackmapper playlistTrackmapper;

    public PlaylistController(PlaylistRepository playlistRepository, PlaylistService playlistService, PlaylistTrackService playlistTrackService, PlaylistTrackmapper playlistTrackmapper) {
        this.playlistRepository = playlistRepository;
        this.playlistService = playlistService;
        this.playlistTrackService = playlistTrackService;
        this.playlistTrackmapper = playlistTrackmapper;
    }

    @GetMapping
    public ResponseEntity<List<PlaylistResponseDto>> getAllPlaylists() {
        return ResponseEntity.ok(playlistService.getAllPlaylists());
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponseDto> getPlaylistById(@PathVariable Long id) {
        return ResponseEntity.ok(PlaylistMapper.toPlaylistResponseDto(playlistService.getPlaylistById(id)));
    }

    @GetMapping("/{playlistId}/tracks")
    public ResponseEntity<List<PlaylistTrackResponseDto>> getTracksForPlaylist(@PathVariable Long playlistId) {
        List<PlaylistTrackResponseDto> tracks = playlistService.getTracksForPlaylist(playlistId);
        return ResponseEntity.ok(tracks);
    }

    @Transactional
    @GetMapping("{playlistId}/tracks/{trackId}")
    public ResponseEntity<PlaylistTrackResponseDto> getTrackFromPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long trackId) {
        PlaylistTrack track = playlistService.getTrackFromPlaylist(playlistId, trackId);
        PlaylistTrackResponseDto responseDto = playlistTrackmapper.toDto(track);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("tracks/{trackId}/audio")
    public ResponseEntity<byte[]> getTrackAudio (@PathVariable Long trackId) {
        PlaylistTrack track = playlistTrackService.findByIdOrThrow(trackId);

        byte[] audioFile = track.getMusicFile();

        if (track.getMusicFileName() == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(track.getMusicFileType()));
        headers.setContentDisposition(ContentDisposition.inline()
                .filename(track.getMusicFileName())
                .build());

        return new ResponseEntity<>(audioFile, headers, HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<PlaylistResponseDto> createPlaylist(@RequestBody PlaylistRequestDto playlistRequestDto) {
        Playlist playlist = playlistService.createPlaylist(playlistRequestDto);
        PlaylistResponseDto playlistResponseDto = PlaylistMapper.toPlaylistResponseDto(playlist);

        URI uri = UriHelper.buildResourceUri(playlist.getId());

        return ResponseEntity.created(uri).body(playlistResponseDto);
    }

    @PostMapping ("/{playlistId}")
    public ResponseEntity<PlaylistTrackResponseDto> addTrackToPlaylist(
            @PathVariable Long playlistId,
            @RequestPart("file") MultipartFile file,
            @RequestPart("metadata") String metadataJson) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        PlaylistTrackRequestDto trackRequestDto = objectMapper.readValue(metadataJson, PlaylistTrackRequestDto.class);

        trackRequestDto.setPlaylistId(playlistId);

        PlaylistTrack track = playlistTrackService.createTrack(trackRequestDto, file);
        PlaylistTrackResponseDto responseDto = playlistTrackmapper.toDto(track);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PlaylistResponseDto> deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{playlistId}/tracks/{trackId}")
    public ResponseEntity<PlaylistTrackResponseDto> removeTrackFromPlaylist(@PathVariable Long playlistId, @PathVariable Long trackId) {
        playlistService.removeTrackFromPlaylist(playlistId, trackId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistResponseDto> updatePlaylist(@PathVariable Long id, @RequestBody PlaylistRequestDto playlistRequestDto) {
        PlaylistResponseDto playlistResponseDto = playlistService.updatePlaylist(id, playlistRequestDto);

        return ResponseEntity.ok(playlistResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PlaylistResponseDto> patchPlaylist(@PathVariable Long id, @RequestBody PlaylistRequestDto playlistRequestDto) {
        PlaylistResponseDto playlistResponseDto = playlistService.patchPlaylist(id, playlistRequestDto);

        return ResponseEntity.ok(playlistResponseDto);
    }
}
