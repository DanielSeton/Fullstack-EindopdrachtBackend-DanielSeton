package com.eindopdracht.DJCorner.services;

import com.eindopdracht.DJCorner.dtos.FeedbackUpdateDto;
import com.eindopdracht.DJCorner.dtos.SubmissionRequestDto;
import com.eindopdracht.DJCorner.dtos.SubmissionResponseDto;
import com.eindopdracht.DJCorner.enums.Status;
import com.eindopdracht.DJCorner.exceptions.ResourceNotFoundException;
import com.eindopdracht.DJCorner.mappers.SubmissionMapper;
import com.eindopdracht.DJCorner.models.Feedback;
import com.eindopdracht.DJCorner.models.Submission;
import com.eindopdracht.DJCorner.models.User;
import com.eindopdracht.DJCorner.repositories.SubmissionRepository;
import com.eindopdracht.DJCorner.repositories.UserRepository;
import com.eindopdracht.DJCorner.security.MyUserDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final SubmissionMapper submissionMapper;
    private final UserRepository userRepository;


    public SubmissionService(SubmissionRepository submissionRepository, SubmissionMapper submissionMapper, UserRepository userRepository) {
        this.submissionRepository = submissionRepository;
        this.submissionMapper = submissionMapper;
        this.userRepository = userRepository;
    }

    public Submission createSubmission(MultipartFile file, SubmissionRequestDto submissionRequestDto, MyUserDetails userDetails) {
        Submission submission = submissionMapper.toEntity(submissionRequestDto);

        try {
            submission.setMusicFile(file.getBytes());
            submission.setMusicFileName(file.getOriginalFilename());
            submission.setMusicFileType(file.getContentType());
        } catch (Exception e) {
            throw new RuntimeException("Failed to read uploaded file", e);
        }

        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        submission.setUser(user);
        submission.setArtistName(user.getUsername());

        submission.setUploadDate(LocalDate.now());

        return submissionRepository.save(submission);
    }

    public Submission getSingleSubmission(Long id) {
        return this.submissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Submission with id: " + id + " not found"));
    }

    public Page<SubmissionResponseDto> getAllSubmissions(Pageable pageable) {
        Page<Submission> submissionsPage = submissionRepository.findAll(pageable);

        return submissionsPage.map(SubmissionMapper::toSubmissionResponseDto);
    }

    @Transactional
    public Page<SubmissionResponseDto> getUserSubmissions(MyUserDetails userDetails, Pageable pageable) {
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        Page<Submission> submissionsPage = submissionRepository.findSubmissionByUser(user, pageable);
        return submissionsPage.map(SubmissionMapper::toSubmissionResponseDto);
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

        submissionMapper.updateEntity(submission, submissionRequestDto);

        Submission updatedSubmission = this.submissionRepository.save(submission);

        return SubmissionMapper.toSubmissionResponseDto(updatedSubmission);
    }

    public SubmissionResponseDto patchSubmission(Long id, SubmissionRequestDto submissionRequestDto) {
        Submission submission = submissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Submission with id: " + id + " not found"));

        submissionMapper.patchEntity(submission, submissionRequestDto);

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
    public Page<SubmissionResponseDto> getSubmissionsByFeedbackStatus(Status status, Pageable pageable) {
        Page<Submission> submissionsPage = submissionRepository.findByFeedback_Status(status, pageable);
        return submissionsPage.map(SubmissionMapper::toSubmissionResponseDto);
    }

    @Transactional
    public Page<SubmissionResponseDto> filterSubmissions(List<String> tags, Status status, Pageable pageable) {
        long tagCount = (tags != null) ? tags.size() : 0;

        Page<Submission> submissionsPage = submissionRepository.filterSubmissions(
                tags != null ? tags : List.of(),
                tagCount,
                status != null ? status.name() : null,
                pageable
        );

        return submissionsPage.map(SubmissionMapper::toSubmissionResponseDto);
    }
}
