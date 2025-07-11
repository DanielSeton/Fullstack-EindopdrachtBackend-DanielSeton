package com.eindopdracht.DJCorner.dtos;

import com.eindopdracht.DJCorner.models.Tag;
import com.eindopdracht.DJCorner.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SubmissionRequestDto {
    @NotBlank
    private String title;
    private String artistName;
    private LocalDate uploadDate;
    private Integer bpm;

    @NotEmpty
    private byte[] musicFile;

    private String musicFileName;
    private String musicFileType;

    @NotEmpty
    private User user;

    @NotEmpty
    private List<Tag> tags = new ArrayList<>();


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Integer getBpm() {
        return bpm;
    }

    public void setBpm(Integer bpm) {
        this.bpm = bpm;
    }

    public byte[] getMusicFile() {
        return musicFile;
    }

    public void setMusicFile(byte[] musicFile) {
        this.musicFile = musicFile;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
