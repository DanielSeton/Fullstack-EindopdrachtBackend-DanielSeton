package com.eindopdracht.DJCorner.controllers;

import com.eindopdracht.DJCorner.models.Feedback;
import com.eindopdracht.DJCorner.repositories.FeedbackRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("feedback")
public class FeedbackController {

    private final FeedbackRepository feedbackRepository;

    public FeedbackController(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
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



    @PostMapping
    public ResponseEntity<Feedback> createShow(@RequestBody Feedback feedback) {
        return ResponseEntity.ok(this.feedbackRepository.save(feedback));
    }



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
