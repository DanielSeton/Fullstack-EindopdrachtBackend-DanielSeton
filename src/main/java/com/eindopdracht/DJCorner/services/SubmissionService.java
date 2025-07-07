package com.eindopdracht.DJCorner.services;

import com.eindopdracht.DJCorner.dtos.FeedbackUpdateDto;
import com.eindopdracht.DJCorner.dtos.SubmissionRequestDto;
import com.eindopdracht.DJCorner.dtos.SubmissionResponseDto;
import com.eindopdracht.DJCorner.enums.Status;
import com.eindopdracht.DJCorner.exceptions.ResourceNotFoundException;
import com.eindopdracht.DJCorner.mappers.SubmissionMapper;
import com.eindopdracht.DJCorner.models.Feedback;
import com.eindopdracht.DJCorner.models.Submission;
import com.eindopdracht.DJCorner.repositories.SubmissionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubmissionService {

    private final SubmissionRepository submissionRepository;

    public SubmissionService(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    public Submission createSubmission(SubmissionRequestDto submissionRequestDto) {
        return this.submissionRepository.save(SubmissionMapper.toEntity(submissionRequestDto));
    }

    public Submission getSingleSubmission(Long id) {
        return this.submissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Submission with id: " + id + " not found"));
    }

    public List<SubmissionResponseDto> getAllSubmissions() {
        List<Submission> submissionsList = submissionRepository.findAll();
        List<SubmissionResponseDto> submissionResponseDtoList = new ArrayList<>();

        for (Submission submission : submissionsList) {
            SubmissionResponseDto submissionResponseDto = SubmissionMapper.toSubmissionResponseDto(submission);
            submissionResponseDtoList.add(submissionResponseDto);
        }
        return submissionResponseDtoList;
    }

    public void deleteSingleSubmission(Long id) {
        Optional<Submission> submissionOptional = this.submissionRepository.findById(id);

        if (submissionOptional.isEmpty()) {
            throw new ResourceNotFoundException("Submission with id: " + id + " not found");
        }

        submissionRepository.deleteById(id);
    }

    public SubmissionResponseDto updateSubmission(Long id, SubmissionRequestDto submissionRequestDto) {
        Submission submission = submissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Submission with id: " + id + " not found"));

        SubmissionMapper.updateEntity(submission, submissionRequestDto);

        Submission updatedSubmission = this.submissionRepository.save(submission);

        return SubmissionMapper.toSubmissionResponseDto(updatedSubmission);
    }

    public SubmissionResponseDto patchSubmission(Long id, SubmissionRequestDto submissionRequestDto) {
        Submission submission = submissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Submission with id: " + id + " not found"));

        SubmissionMapper.patchEntity(submission, submissionRequestDto);

        Submission updatedSubmission = this.submissionRepository.save(submission);

        return SubmissionMapper.toSubmissionResponseDto(updatedSubmission);
    }

    public SubmissionResponseDto updateFeedback(Long submissionId, FeedbackUpdateDto feedbackUpdateDto){
        Submission submission = submissionRepository.findById(submissionId).orElseThrow(() -> new ResourceNotFoundException("Submission with id: " + submissionId + " not found"));

        Feedback feedback = submission.getFeedback();
        if (feedback == null) {
            feedback = new Feedback();
            submission.setFeedback(feedback);
        }

        if (feedbackUpdateDto.getFeedback() != null) {
            feedback.setFeedback(feedbackUpdateDto.getFeedback());
        }

        if (feedbackUpdateDto.getStatus() != null) {
            feedback.setStatus(feedbackUpdateDto.getStatus());
        }

        Submission updatedSubmission = submissionRepository.save(submission);
        return SubmissionMapper.toSubmissionResponseDto(updatedSubmission);
    }

    @Transactional
    public List<Submission> getSubmissionsByFeedbackStatus(Status status) {
        return submissionRepository.findByFeedback_Status(status);
    }

    @Transactional
    public List<SubmissionResponseDto> filterSubmissions(List<String> tags, Status status) {
        long tagCount = (tags != null) ? tags.size() : 0;

        List<Submission> submissions = submissionRepository.filterSubmissions(
                tags != null ? tags : List.of(),
                tagCount,
                status != null ? status.name() : null
        );

        return submissions.stream()
                .map(SubmissionMapper::toSubmissionResponseDto)
                .toList();
    }
}
