package com.eindopdracht.DJCorner.mappers;

import com.eindopdracht.DJCorner.dtos.PlaylistRequestDto;
import com.eindopdracht.DJCorner.dtos.PlaylistResponseDto;
import com.eindopdracht.DJCorner.models.Playlist;
import com.eindopdracht.DJCorner.models.PlaylistTrack;
import com.eindopdracht.DJCorner.services.PlaylistTrackService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlaylistMapper {

    private final PlaylistTrackService playlistTrackService;

    public PlaylistMapper(PlaylistTrackService playlistTrackService) {
        this.playlistTrackService = playlistTrackService;
    }

    public Playlist toEntity(PlaylistRequestDto playlistRequestDto) {
        Playlist playlist = new Playlist();

        playlist.setTitle(playlistRequestDto.getTitle());
        playlist.setGenre(playlistRequestDto.getGenre());

        List<PlaylistTrack> tracks = playlistRequestDto.getTrackIds()
                .stream()
                .map(playlistTrackService::findByIdOrThrow)
                .toList();

        playlist.setTracks(tracks);

        return playlist;
    }

    public static PlaylistResponseDto toPlaylistResponseDto(Playlist playlist) {
        PlaylistResponseDto playlistResponseDto = new PlaylistResponseDto();

        playlistResponseDto.setId(playlist.getId());
        playlistResponseDto.setTitle(playlist.getTitle());
        playlistResponseDto.setGenre(playlist.getGenre());

        playlistResponseDto.setTracksIds(
                playlist.getTracks().stream()
                        .map(PlaylistTrack::getId)
                        .collect(Collectors.toList())
        );

        return playlistResponseDto;
    }

    public void updateEntity(Playlist playlist, PlaylistRequestDto playlistRequestDto) {
        playlist.setTitle(playlistRequestDto.getTitle());
        playlist.setGenre(playlistRequestDto.getGenre());
    }

    public void patchEntity(Playlist playlist, PlaylistRequestDto playlistRequestDto) {
        if (playlistRequestDto.getTitle() != null) {
            playlist.setTitle(playlistRequestDto.getTitle());
        }
        if (playlistRequestDto.getGenre() != null) {
            playlist.setGenre(playlistRequestDto.getGenre());
        }
    }
}
