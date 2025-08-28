package com.eindopdracht.DJCorner.controllers;

import com.eindopdracht.DJCorner.dtos.FeedbackResponseDto;
import com.eindopdracht.DJCorner.mappers.FeedbackMapper;
import com.eindopdracht.DJCorner.models.Feedback;
import com.eindopdracht.DJCorner.enums.Status;
import com.eindopdracht.DJCorner.services.FeedbackService;
import com.eindopdracht.DJCorner.services.SubmissionService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService, SubmissionService submissionService) {
        this.feedbackService = feedbackService;
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity <List<FeedbackResponseDto>> getAllFeedbacks() {
        return ResponseEntity.ok(feedbackService.getAllFeedbacks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackResponseDto> getFeedbackById(@PathVariable Long id) {
        return ResponseEntity.ok(FeedbackMapper.toFeedbackResponseDto(this.feedbackService.getFeedback(id)));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<FeedbackResponseDto>> getFeedbackByStatus(@PathVariable Status status) {
        List<Feedback> feedbackList = feedbackService.getFeedbackByStatus(status);
        List<FeedbackResponseDto> dtoList = feedbackList.stream()
                .map(FeedbackMapper::toFeedbackResponseDto).toList();

        return ResponseEntity.ok(dtoList);
    }
}
