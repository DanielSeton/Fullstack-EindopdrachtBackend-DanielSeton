package com.eindopdracht.DJCorner.dtos;

import com.eindopdracht.DJCorner.enums.Status;
import jakarta.validation.constraints.NotBlank;

public class FeedbackRequestDto {
    @NotBlank
    private String feedback;

    @NotBlank
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
