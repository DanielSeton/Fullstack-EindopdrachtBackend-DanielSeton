package com.eindopdracht.DJCorner.controllers;


import com.eindopdracht.DJCorner.dtos.SubmissionRequestDto;
import com.eindopdracht.DJCorner.dtos.SubmissionResponseDto;
import com.eindopdracht.DJCorner.dtos.FeedbackUpdateDto;
import com.eindopdracht.DJCorner.enums.Status;
import com.eindopdracht.DJCorner.helpers.UriHelper;
import com.eindopdracht.DJCorner.mappers.SubmissionMapper;
import com.eindopdracht.DJCorner.models.Submission;
import com.eindopdracht.DJCorner.security.MyUserDetails;
import com.eindopdracht.DJCorner.services.SubmissionService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }


    @GetMapping
    public ResponseEntity<List<SubmissionResponseDto>> getAllSubmissions() {
        return ResponseEntity.ok(submissionService.getAllSubmissions());
    }

    @GetMapping("/{id}/audio")
    public ResponseEntity<byte[]> getAudioFile(@PathVariable Long id) {
        Submission submission = submissionService.getSingleSubmission(id);

        byte[] audioFile = submission.getMusicFile();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(submission.getMusicFileType()));
        headers.setContentDisposition(ContentDisposition.inline()
                .filename(submission.getMusicFileName())
                .build());

        return new ResponseEntity<>(audioFile, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubmissionResponseDto> getSubmissionById(@PathVariable Long id) {
        return ResponseEntity.ok(SubmissionMapper.toSubmissionResponseDto(this.submissionService.getSingleSubmission(id)));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<SubmissionResponseDto>> getSubmissionsByStatus(@PathVariable Status status) {
        List<Submission> submissions = submissionService.getSubmissionsByFeedbackStatus(status);
        List<SubmissionResponseDto> dtoList = submissions.stream()
                .map(SubmissionMapper::toSubmissionResponseDto).toList();

        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<SubmissionResponseDto>> getSubmissionsByTags(
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) Status status){

        List<SubmissionResponseDto> dtos = submissionService.filterSubmissions(tags, status);
        return ResponseEntity.ok(dtos);
    }



    @PostMapping
    public ResponseEntity<SubmissionResponseDto> createSubmission(
            @RequestPart("file") MultipartFile file,
            @RequestPart ("metadata") @Valid SubmissionRequestDto submissionRequestDto,
            @AuthenticationPrincipal MyUserDetails userDetails) {

        Submission submission = submissionService.createSubmission(file, submissionRequestDto, userDetails);
        SubmissionResponseDto submissionResponseDto = SubmissionMapper.toSubmissionResponseDto(submission);

        URI uri = UriHelper.buildResourceUri(submission.getId());

        return ResponseEntity.created(uri).body(submissionResponseDto);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSubmission(@PathVariable Long id) {

        submissionService.deleteSingleSubmission(id);

        return ResponseEntity.noContent().build();
    }



    @PutMapping("/{id}")
    public ResponseEntity<SubmissionResponseDto> updateSubmission(@PathVariable Long id, @RequestBody SubmissionRequestDto submissionRequestDto) {

        SubmissionResponseDto updatedSubmission = submissionService.updateSubmission(id, submissionRequestDto);

        return ResponseEntity.ok(updatedSubmission);
    }



    @PatchMapping("/{id}")
    public ResponseEntity<SubmissionResponseDto> patchSubmission(@PathVariable Long id, @RequestBody SubmissionRequestDto submissionRequestDto) {
        SubmissionResponseDto updatedSubmission = submissionService.updateSubmission(id, submissionRequestDto);
        return ResponseEntity.ok(updatedSubmission);
    }

    @PatchMapping("/{id}/feedback")
    public ResponseEntity<SubmissionResponseDto> updateFeedback(@PathVariable Long id, @RequestBody FeedbackUpdateDto feedbackUpdateDto) {
        SubmissionResponseDto updatedSubmission = submissionService.updateFeedback(id, feedbackUpdateDto);
        return ResponseEntity.ok(updatedSubmission);
    }
}
