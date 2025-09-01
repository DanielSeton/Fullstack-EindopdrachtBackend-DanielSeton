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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }


    @GetMapping
    public ResponseEntity<Page<SubmissionResponseDto>> getAllSubmissions(
        @RequestParam (defaultValue = "0") int page,
        @RequestParam (defaultValue = "15") int size
    ) {
        Page<SubmissionResponseDto> pagedSubmissions = submissionService.getSubmissions(PageRequest.of(page, size));
        return ResponseEntity.ok(pagedSubmissions);
    }

    @GetMapping("/mine")
    public ResponseEntity<Page<SubmissionResponseDto>> getAllUserSubmissions(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "15") int size,
            @AuthenticationPrincipal MyUserDetails userDetails) {
        Page<SubmissionResponseDto> pagedSubmission = submissionService.getUserSubmissions(userDetails, PageRequest.of(page, size));
        return ResponseEntity.ok(pagedSubmission);

    }

    @GetMapping("/{id}/audio")
    public ResponseEntity<byte[]> getAudioFile(@PathVariable Long id) {
        Submission submission = submissionService.getSubmissionById(id);

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
        return ResponseEntity.ok(SubmissionMapper.toSubmissionResponseDto(this.submissionService.getSubmissionById(id)));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<SubmissionResponseDto>> getSubmissionsByStatus(
            @PathVariable Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size
    ) {
        Page<SubmissionResponseDto> pagedSubmissions = submissionService.getSubmissionsByFeedbackStatus(status, PageRequest.of(page, size));
        return ResponseEntity.ok(pagedSubmissions);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<SubmissionResponseDto>> getSubmissionsByTags(
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size
    ){
        Page<SubmissionResponseDto> pagedSubmissions = submissionService.filterSubmissions(tags, status, PageRequest.of(page, size));
        return ResponseEntity.ok(pagedSubmissions);
    }



    @PostMapping
    public ResponseEntity<SubmissionResponseDto> createSubmission(
            @RequestPart("file") MultipartFile file,
            @RequestPart ("metadata") String metadataJson,
            @AuthenticationPrincipal MyUserDetails userDetails) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        SubmissionRequestDto submissionRequestDto = objectMapper.readValue(metadataJson, SubmissionRequestDto.class);

        Submission submission = submissionService.createSubmission(file, submissionRequestDto, userDetails);
        SubmissionResponseDto submissionResponseDto = SubmissionMapper.toSubmissionResponseDto(submission);

        URI uri = UriHelper.buildResourceUri(submission.getId());

        return ResponseEntity.created(uri).body(submissionResponseDto);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSubmission(
            @PathVariable Long id,
            @AuthenticationPrincipal MyUserDetails userDetails) {

        submissionService.deleteSubmission(id, userDetails);

        return ResponseEntity.noContent().build();
    }



    @PutMapping("/{id}")
    public ResponseEntity<SubmissionResponseDto> updateSubmission(
            @PathVariable Long id,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("metadata") String metadataJson,
            @AuthenticationPrincipal MyUserDetails userDetails) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        SubmissionRequestDto submissionRequestDto = objectMapper.readValue(metadataJson, SubmissionRequestDto.class);

        SubmissionResponseDto updatedSubmission = submissionService.updateSubmission(id, submissionRequestDto, file, userDetails);

        return ResponseEntity.ok(updatedSubmission);
    }



    @PatchMapping("/{id}")
    public ResponseEntity<SubmissionResponseDto> patchSubmission(
            @PathVariable Long id,
            @RequestBody SubmissionRequestDto submissionRequestDto,
            @AuthenticationPrincipal MyUserDetails userDetails) {
        SubmissionResponseDto updatedSubmission = submissionService.patchSubmission(id, submissionRequestDto, userDetails);
        return ResponseEntity.ok(updatedSubmission);
    }

    @PatchMapping("/{id}/feedback")
    public ResponseEntity<SubmissionResponseDto> updateFeedback(@PathVariable Long id, @RequestBody FeedbackUpdateDto feedbackUpdateDto) {
        SubmissionResponseDto updatedSubmission = submissionService.updateFeedback(id, feedbackUpdateDto);
        return ResponseEntity.ok(updatedSubmission);
    }
}
