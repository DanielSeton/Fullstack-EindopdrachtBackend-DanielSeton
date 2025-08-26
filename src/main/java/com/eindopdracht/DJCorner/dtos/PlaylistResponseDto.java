package com.eindopdracht.DJCorner.dtos;

import com.eindopdracht.DJCorner.models.PlaylistTrack;

import java.util.ArrayList;
import java.util.List;

public class PlaylistResponseDto {
    private Long id;
    private String title;
    private String genre;
    private List<Long> tracksIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<Long> getTracksIds() {
        return tracksIds;
    }

    public void setTracksIds(List<Long> tracksIds) {
        this.tracksIds = tracksIds;
    }
}
