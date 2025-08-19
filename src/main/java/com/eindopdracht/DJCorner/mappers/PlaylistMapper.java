package com.eindopdracht.DJCorner.mappers;

import com.eindopdracht.DJCorner.dtos.PlaylistRequestDto;
import com.eindopdracht.DJCorner.models.Playlist;
import com.eindopdracht.DJCorner.models.PlaylistTrack;
import com.eindopdracht.DJCorner.services.PlaylistTrackService;
import org.springframework.stereotype.Component;

import java.util.List;

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

        playlist.setTrack(tracks);

        return playlist;
    }

    public static Playlist toPlaylistResponseDto(Playlist playlist, PlaylistRequestDto playlistRequestDto) {}
}
