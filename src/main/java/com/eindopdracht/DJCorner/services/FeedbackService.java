package com.eindopdracht.DJCorner.services;

import com.eindopdracht.DJCorner.dtos.FeedbackRequestDto;
import com.eindopdracht.DJCorner.dtos.FeedbackResponseDto;
import com.eindopdracht.DJCorner.exceptions.ResourceNotFoundException;
import com.eindopdracht.DJCorner.mappers.FeedbackMapper;
import com.eindopdracht.DJCorner.models.Feedback;
import com.eindopdracht.DJCorner.repositories.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Feedback createFeedback(FeedbackRequestDto feedbackRequestDto) {
        return this.feedbackRepository.save(FeedbackMapper.toEntity(feedbackRequestDto));
    }

    public List<FeedbackResponseDto> getAllFeedbacks() {
        List<Feedback> feedbackList = feedbackRepository.findAll();
        List<FeedbackResponseDto> feedbackResponseDtoList = new ArrayList<>();

        for (Feedback feedback : feedbackList) {
            FeedbackResponseDto feedbackResponseDto = FeedbackMapper.toFeedbackResponseDto(feedback);
            feedbackResponseDtoList.add(feedbackResponseDto);
        }
        return feedbackResponseDtoList;
    }

    public Feedback updateFeedback(Long feedbackId, FeedbackRequestDto feedbackRequestDto) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Object with id: " + feedbackId + " not found"));

        feedback.setFeedback(feedbackRequestDto.getFeedback());
        feedback.setStatus(feedbackRequestDto.getStatus());

        Feedback updatedFeedback = feedbackRepository.save(feedback);
        return FeedbackMapper.toFeedbackResponseDto(updatedFeedback);
    }
}
