package com.eindopdracht.DJCorner.mappers;

import com.eindopdracht.DJCorner.dtos.PlaylistTrackRequestDto;
import com.eindopdracht.DJCorner.dtos.PlaylistTrackResponseDto;
import com.eindopdracht.DJCorner.models.Playlist;
import com.eindopdracht.DJCorner.models.PlaylistTrack;
import com.eindopdracht.DJCorner.models.User;
import com.eindopdracht.DJCorner.services.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlaylistTrackmapper {
    private final UserService userService;

    public PlaylistTrackmapper(UserService userService) {
        this.userService = userService;
    }

    public PlaylistTrack toEntity(PlaylistTrackRequestDto playlistTrackRequestDto) {
        PlaylistTrack playlistTrack = new PlaylistTrack();

        playlistTrack.setTitle(playlistTrackRequestDto.getTitle());
        playlistTrack.setMusicFileName(playlistTrackRequestDto.getMusicFileName());
        playlistTrack.setMusicFileType(playlistTrackRequestDto.getMusicFileType());

        User user = userService.findUserById(playlistTrackRequestDto.getUserId());
        playlistTrack.setUser(user);

        return playlistTrack;
    }

    public PlaylistTrackResponseDto toDto(PlaylistTrack playlistTrack) {
        PlaylistTrackResponseDto trackDto = new PlaylistTrackResponseDto();

        trackDto.setId(playlistTrack.getId());
        trackDto.setTitle(playlistTrack.getTitle());
        trackDto.setMusicFileName(playlistTrack.getMusicFileName());
        trackDto.setMusicFileType(playlistTrack.getMusicFileType());

        if (playlistTrack.getUser() != null) {
            trackDto.setUploadedBy(playlistTrack.getUser().getUsername());
        }

        List<Long> playlistIds = playlistTrack.getPlaylists().stream()
                .map(Playlist::getId)
                .toList();
        trackDto.setPlaylistIds(playlistIds);

        trackDto.setAudioDownloadUrl("playlists/tracks/" + playlistTrack.getId() + "/audio");

        return trackDto;
    }


}
