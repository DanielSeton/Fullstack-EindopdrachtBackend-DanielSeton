package com.eindopdracht.DJCorner.services;

import com.eindopdracht.DJCorner.dtos.PlaylistRequestDto;
import com.eindopdracht.DJCorner.dtos.PlaylistResponseDto;
import com.eindopdracht.DJCorner.dtos.PlaylistTrackResponseDto;
import com.eindopdracht.DJCorner.exceptions.ResourceNotFoundException;
import com.eindopdracht.DJCorner.mappers.PlaylistMapper;
import com.eindopdracht.DJCorner.mappers.PlaylistTrackmapper;
import com.eindopdracht.DJCorner.models.Playlist;
import com.eindopdracht.DJCorner.models.PlaylistTrack;
import com.eindopdracht.DJCorner.repositories.PlaylistRepository;
import com.eindopdracht.DJCorner.repositories.PlaylistTrackRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistMapper playlistMapper;
    private final PlaylistTrackmapper playlistTrackmapper;
    private final PlaylistTrackRepository playlistTrackRepository;

    public PlaylistService(PlaylistRepository playlistRepository, PlaylistMapper playlistMapper, PlaylistTrackmapper playlistTrackmapper, PlaylistTrackRepository playlistTrackRepository) {
        this.playlistRepository = playlistRepository;
        this.playlistMapper = playlistMapper;
        this.playlistTrackmapper = playlistTrackmapper;
        this.playlistTrackRepository = playlistTrackRepository;
    }

    public Playlist getPlaylistById(long id) {
        return this.playlistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Submission with id: " + id + " not found"));
    }

    @Transactional
    public List<PlaylistResponseDto> getAllPlaylists() {
        List<Playlist> Playlists = playlistRepository.findAll();
        List<PlaylistResponseDto> PlaylistResponseDto = new ArrayList<>();

        for (Playlist playlist : Playlists) {
            PlaylistResponseDto playlistResponseDto = PlaylistMapper.toPlaylistResponseDto(playlist);
            PlaylistResponseDto.add(playlistResponseDto);
        }

        return PlaylistResponseDto;
    }

    public Playlist createPlaylist(PlaylistRequestDto playlistRequestDto) {
        Playlist playlist = playlistMapper.toEntity(playlistRequestDto);

        return playlistRepository.save(playlist);
    }

    public void deletePlaylist(long id) {
        Optional<Playlist> playlist = playlistRepository.findById(id);

        if (playlist.isEmpty()) {
            throw new ResourceNotFoundException("Playlist with id: " + id + " not found");
        }

        playlistRepository.deleteById(id);
    }

    @Transactional
    public void removeTrackFromPlaylist(Long playlistId, Long trackId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist with id: " + playlistId + " not found"));

        PlaylistTrack track = playlistTrackRepository.findById(trackId)
                .orElseThrow(() -> new ResourceNotFoundException("Track with id: " + trackId + " not found"));

        playlist.getTracks().remove(track);
        track.getPlaylists().remove(playlist);

        playlistRepository.save(playlist);
    }

    public PlaylistResponseDto updatePlaylist(Long id, PlaylistRequestDto playlistRequestDto) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist with id: " + id + " not found"));

        playlistMapper.updateEntity(playlist, playlistRequestDto);

        Playlist updatedPlaylist = playlistRepository.save(playlist);

        return PlaylistMapper.toPlaylistResponseDto(updatedPlaylist);
    }

    public PlaylistResponseDto patchPlaylist(long id, PlaylistRequestDto playlistRequestDto) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist with id: " + id + " not found"));

        playlistMapper.patchEntity(playlist, playlistRequestDto);

        Playlist updatedPlaylist = playlistRepository.save(playlist);

        return PlaylistMapper.toPlaylistResponseDto(updatedPlaylist);
    }

    @Transactional
    public List<PlaylistTrackResponseDto> getTracksForPlaylist(long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist with id: " + playlistId + " not found"));

        return playlist.getTracks().stream()
                .map(playlistTrackmapper::toDto)
                .collect(Collectors.toList());
    }

    public PlaylistTrack getTrackFromPlaylist(Long playlistId, Long trackId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist with id: " + playlistId + " not found"));

        return playlist.getTracks().stream()
                .filter(track -> track.getId().equals(trackId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Track with id: " + trackId + " not found in this playlist"));
    }
}
