package com.eindopdracht.DJCorner.dtos;

import java.util.List;

public class PlaylistTrackResponseDto {
    private Long id;
    private String title;
    private String musicFileName;
    private String musicFileType;
    private String uploadedBy;
    private List<Long> playlistIds;
    private String audioDownloadUrl;

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

    public String getMusicFileName() {
        return musicFileName;
    }

    public void setMusicFileName(String musicFileName) {
        this.musicFileName = musicFileName;
    }

    public String getMusicFileType() {
        return musicFileType;
    }

    public void setMusicFileType(String musicFileType) {
        this.musicFileType = musicFileType;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getAudioDownloadUrl() {
        return audioDownloadUrl;
    }

    public void setAudioDownloadUrl(String audioDownloadUrl) {
        this.audioDownloadUrl = audioDownloadUrl;
    }

    public List<Long> getPlaylistIds() {
        return playlistIds;
    }

    public void setPlaylistIds(List<Long> playlistIds) {
        this.playlistIds = playlistIds;
    }
}
