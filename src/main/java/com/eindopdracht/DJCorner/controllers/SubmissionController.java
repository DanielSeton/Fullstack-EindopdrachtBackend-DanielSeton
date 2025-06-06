package com.eindopdracht.DJCorner.controllers;


import com.eindopdracht.DJCorner.models.Submission;
import com.eindopdracht.DJCorner.repositories.SubmissionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {

    private final SubmissionRepository submissionRepository;

    public SubmissionController(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }


    @GetMapping
    public ResponseEntity<List<Submission>> getSubmissions() {
        return ResponseEntity.ok(submissionRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Submission> createSubmission(@RequestBody Submission submission) {
        this.submissionRepository.save(submission);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + submission.getId()).toUriString());

        return ResponseEntity.created(uri).body(submission);
    }
}
