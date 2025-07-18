package com.eindopdracht.DJCorner.dtos;

import com.eindopdracht.DJCorner.models.Feedback;
import com.eindopdracht.DJCorner.models.Tag;
import com.eindopdracht.DJCorner.models.User;

import java.time.LocalDate;
import java.util.List;

public class SubmissionResponseDto {
    private Long id;
    private String title;
    private String artistName;
    private LocalDate uploadDate;
    private Integer bpm;
    private String musicFileName;
    private String musicFileType;
    private List<String> tags;
    private Long feedbackId;
    private String feedbackStatus;
    private String feedbackText;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFeedbackStatus() {
        return feedbackStatus;
    }

    public void setFeedbackStatus(String feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public String getAudioDownloadUrl() {
        return audioDownloadUrl;
    }

    public void setAudioDownloadUrl(String audioDownloadUrl) {
        this.audioDownloadUrl = audioDownloadUrl;
    }
}
