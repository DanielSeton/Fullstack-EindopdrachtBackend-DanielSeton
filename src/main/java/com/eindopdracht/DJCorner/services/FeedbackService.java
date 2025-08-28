package com.eindopdracht.DJCorner.services;

import com.eindopdracht.DJCorner.dtos.FeedbackRequestDto;
import com.eindopdracht.DJCorner.dtos.FeedbackResponseDto;
import com.eindopdracht.DJCorner.enums.Status;
import com.eindopdracht.DJCorner.exceptions.ResourceNotFoundException;
import com.eindopdracht.DJCorner.mappers.FeedbackMapper;
import com.eindopdracht.DJCorner.models.Feedback;
import com.eindopdracht.DJCorner.models.Submission;
import com.eindopdracht.DJCorner.repositories.FeedbackRepository;
import com.eindopdracht.DJCorner.repositories.SubmissionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final SubmissionRepository submissionRepository;

    public FeedbackService(FeedbackRepository feedbackRepository, SubmissionRepository submissionRepository) {
        this.feedbackRepository = feedbackRepository;
        this.submissionRepository = submissionRepository;
    }

    public Feedback createFeedback(FeedbackRequestDto feedbackRequestDto) {
        return feedbackRepository.save(FeedbackMapper.toEntity(feedbackRequestDto));
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

    public Feedback getFeedback(Long id) {
        return feedbackRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Feedback with id: " + id + " not found"));
    }

    public List<Feedback> getFeedbackByStatus(Status status) {
        return feedbackRepository.findByStatus(status);
    }

    public void deleteFeedback(Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId).orElseThrow(() -> new ResourceNotFoundException("Feedback with id: " + feedbackId + " not found"));

        Submission submission = submissionRepository.findByFeedbackId(feedbackId).orElseThrow(() -> new ResourceNotFoundException("Submission with Feedback id: " + feedbackId + " not found"));

        feedbackRepository.deleteById(feedbackId);

        Feedback newFeedback = new Feedback();
        newFeedback.setStatus(Status.NO_FEEDBACK);
        newFeedback.setFeedback("");

        feedbackRepository.save(newFeedback);

        submission.setFeedback(newFeedback);
        submissionRepository.save(submission);
    }
}
