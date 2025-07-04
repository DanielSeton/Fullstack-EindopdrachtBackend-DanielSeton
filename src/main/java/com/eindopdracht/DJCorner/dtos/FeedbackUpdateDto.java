package com.eindopdracht.DJCorner.dtos;

import com.eindopdracht.DJCorner.enums.Status;

public class FeedbackUpdateDto {
    private String feedback;
    private Status status;

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
