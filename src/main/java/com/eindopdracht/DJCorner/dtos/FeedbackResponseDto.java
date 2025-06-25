package com.eindopdracht.DJCorner.dtos;

import com.eindopdracht.DJCorner.enums.Status;

public class FeedbackResponseDto {
    private Long id;
    private String feedback;
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
