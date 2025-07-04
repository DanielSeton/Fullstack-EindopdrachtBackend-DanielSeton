package com.eindopdracht.DJCorner.controllers;

import com.eindopdracht.DJCorner.dtos.FeedbackRequestDto;
import com.eindopdracht.DJCorner.dtos.FeedbackResponseDto;
import com.eindopdracht.DJCorner.models.Feedback;
import com.eindopdracht.DJCorner.repositories.FeedbackRepository;
import com.eindopdracht.DJCorner.repositories.SubmissionRepository;
import com.eindopdracht.DJCorner.services.FeedbackService;
import com.eindopdracht.DJCorner.services.SubmissionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService, SubmissionService submissionService) {
        this.feedbackService = feedbackService;
    }


//    @GetMapping("/{id}")
//    public ResponseEntity<Feedback> getFeedback(@PathVariable Long id) {
//        Optional<Feedback> feedback = feedbackRepository.findById(id);
//
//        if (feedback.isPresent()) {
//            return ResponseEntity.ok(feedback.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @PutMapping("/{feedbackId}")
//    public ResponseEntity<FeedbackResponseDto> updateFeedback(@PathVariable Long feedbackId, @Valid @RequestBody FeedbackRequestDto feedbackRequestDto) {
//        FeedbackResponseDto updatedFeedback = feedbackService.updateFeedback(feedbackId, feedbackRequestDto);
//
//        return ResponseEntity.ok(updatedFeedback);
//    }



//    @PutMapping("/{id}")
//    public ResponseEntity<Feedback> updateFeedback(@PathVariable Long id, @RequestBody Feedback newFeedback) {
//        Optional<Feedback> feedback = feedbackRepository.findById(id);
//
//        if (feedback.isEmpty()) {
//            return ResponseEntity.notFound().build();
//
//        } else {
//            Feedback feedback1 = feedback.get();
//
//            feedback1.setFeedback(newFeedback.getFeedback());
//            feedback1.setStatus(newFeedback.getStatus());
//
//            Feedback returnFeedback = feedbackRepository.save(feedback1);
//
//            return ResponseEntity.ok().body(returnFeedback);
//        }
//    }
//
//
//
//    @PatchMapping("/{id}")
//    public ResponseEntity<Feedback> patchFeedback(@PathVariable Long id, @RequestBody Feedback newFeedback) {
//        Optional<Feedback> feedback = feedbackRepository.findById(id);
//
//        if (feedback.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        } else {
//            Feedback feedback1 = feedback.get();
//            if (newFeedback.getFeedback() != null) {
//                feedback1.setFeedback(newFeedback.getFeedback());
//            }
//            if (newFeedback.getStatus() != null) {
//                feedback1.setStatus(newFeedback.getStatus());
//            }
//
//
//            Feedback returnFeedback = feedbackRepository.save(feedback1);
//            return ResponseEntity.ok().body(returnFeedback);
//        }
//    }
}
