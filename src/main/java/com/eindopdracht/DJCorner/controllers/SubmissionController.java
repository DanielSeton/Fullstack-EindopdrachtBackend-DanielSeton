package com.eindopdracht.DJCorner.controllers;


import com.eindopdracht.DJCorner.models.Submission;
import com.eindopdracht.DJCorner.repositories.SubmissionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<Submission> getSubmission(@PathVariable Long id) {
        Optional<Submission> submission = submissionRepository.findById(id);

        if (submission.isPresent()) {
            return ResponseEntity.ok(submission.get());
        } else {
            return ResponseEntity.notFound().build();
        }
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



    @DeleteMapping("/{id}")
    public ResponseEntity<Submission> deleteSubmission(@PathVariable Long id) {
        Optional<Submission> submission = submissionRepository.findById(id);

        if (submission.isPresent()) {
            submissionRepository.deleteById(id);
        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }



    @PutMapping("/{id}")
    public ResponseEntity<Submission> updateSubmission(@PathVariable Long id, @RequestBody Submission newSubmission) {
        Optional<Submission> submission = submissionRepository.findById(id);

        if (submission.isEmpty()) {
            return ResponseEntity.notFound().build();

        } else {
            Submission submission1 = submission.get();

            submission1.setTitle(newSubmission.getTitle());
            submission1.setArtistName(newSubmission.getArtistName());
            submission1.setBpm(newSubmission.getBpm());
            submission1.setMusicFile(newSubmission.getMusicFile());

            Submission returnSubmission = submissionRepository.save(submission1);

            return ResponseEntity.ok().body(returnSubmission);
        }
    }



    @PatchMapping("/{id}")
    public ResponseEntity<Submission> patchSubmission(@PathVariable Long id, @RequestBody Submission newSubmission) {
        Optional<Submission> submission = submissionRepository.findById(id);

        if (submission.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Submission submission1 = submission.get();
            if (newSubmission.getTitle() != null) {
                submission1.setTitle(newSubmission.getTitle());
            }
            if (newSubmission.getArtistName() != null) {
                submission1.setArtistName(newSubmission.getArtistName());
            }
            if (newSubmission.getBpm() != null) {
                submission1.setBpm(newSubmission.getBpm());
            }
            if (newSubmission.getMusicFile() != null) {
                submission1.setMusicFile(newSubmission.getMusicFile());
            }

            Submission returnSubmission = submissionRepository.save(submission1);
            return ResponseEntity.ok().body(returnSubmission);
        }
    }
}
