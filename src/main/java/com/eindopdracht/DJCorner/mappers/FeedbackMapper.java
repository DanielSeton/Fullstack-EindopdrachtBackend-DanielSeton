package com.eindopdracht.DJCorner.mappers;

import com.eindopdracht.DJCorner.dtos.FeedbackRequestDto;
import com.eindopdracht.DJCorner.dtos.FeedbackResponseDto;
import com.eindopdracht.DJCorner.models.Feedback;

public class FeedbackMapper {

    public static Feedback toEntity(FeedbackRequestDto feedbackRequestDto) {
        Feedback feedback = new Feedback();

        feedback.setFeedback(feedbackRequestDto.getFeedback());
        feedback.setStatus(feedbackRequestDto.getStatus());

        return feedback;
    }

    public static FeedbackResponseDto toFeedbackResponseDto(Feedback feedback) {
        FeedbackResponseDto feedbackResponseDto = new FeedbackResponseDto();

        feedbackResponseDto.setId(feedback.getId());
        feedbackResponseDto.setFeedback(feedback.getFeedback());
        feedbackResponseDto.setStatus(feedback.getStatus());

        return feedbackResponseDto;

    }
}
