package com.eindopdracht.DJCorner.services;

import com.eindopdracht.DJCorner.dtos.PlaylistTrackRequestDto;
import com.eindopdracht.DJCorner.exceptions.ResourceNotFoundException;
import com.eindopdracht.DJCorner.mappers.PlaylistTrackmapper;
import com.eindopdracht.DJCorner.models.Playlist;
import com.eindopdracht.DJCorner.models.PlaylistTrack;
import com.eindopdracht.DJCorner.repositories.PlaylistRepository;
import com.eindopdracht.DJCorner.repositories.PlaylistTrackRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class PlaylistTrackService {

    private final PlaylistTrackRepository playlistTrackRepository;
    private final PlaylistRepository playlistRepository;
    private final PlaylistTrackmapper playlistTrackmapper;

    public PlaylistTrackService(PlaylistTrackRepository playlistTrackRepository, PlaylistTrackmapper playlistTrackmapper, PlaylistRepository playlistRepository) {
        this.playlistTrackRepository = playlistTrackRepository;
        this.playlistTrackmapper = playlistTrackmapper;
        this.playlistRepository = playlistRepository;
    }

    public PlaylistTrack findByIdOrThrow(Long id) {
        return playlistTrackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Track with id: " + id + " not found"));
    }

    @Transactional
    public void deleteTrack(Long id) {
        Optional<PlaylistTrack> playlistTrack = playlistTrackRepository.findById(id);

        if (playlistTrack.isEmpty()) {
            throw new ResourceNotFoundException("Track with id: " + id + " not found");
        }

        PlaylistTrack track = playlistTrack.get();

        for (Playlist playlist : track.getPlaylists()) {
            playlist.getTracks().remove(track);
        }

        track.getPlaylists().clear();

        playlistTrackRepository.delete(track);
    }

    @Transactional
    public PlaylistTrack createTrack(PlaylistTrackRequestDto playlistTrackRequestDto, MultipartFile file) {
        PlaylistTrack track = playlistTrackmapper.toEntity(playlistTrackRequestDto);

        try {
            track.setMusicFile(file.getBytes());
            track.setMusicFileName(file.getOriginalFilename());
            track.setMusicFileType(file.getContentType());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read uploaded file", e);
        }

        if (playlistTrackRequestDto.getPlaylistId() != null) {
            Playlist playlist = playlistRepository.findById(playlistTrackRequestDto.getPlaylistId())
                    .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
            track.getPlaylists().add(playlist);
            playlist.getTracks().add(track);
        }

        playlistTrackRepository.save(track);
        return track;
    }
}
