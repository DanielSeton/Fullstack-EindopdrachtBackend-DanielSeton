package com.eindopdracht.DJCorner.dtos;

import com.eindopdracht.DJCorner.models.PlaylistTrack;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class PlaylistRequestDto {
    @NotBlank
    private String title;
    private String genre;
    private List<Long> trackIds;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<Long> getTrackIds() {
        return trackIds;
    }

    public void setTrackIds(List<Long> trackIds) {
        this.trackIds = trackIds;
    }
}
