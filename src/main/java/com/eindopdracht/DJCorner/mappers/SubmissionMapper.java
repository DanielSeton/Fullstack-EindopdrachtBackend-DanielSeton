package com.eindopdracht.DJCorner.mappers;

import com.eindopdracht.DJCorner.dtos.SubmissionRequestDto;
import com.eindopdracht.DJCorner.dtos.SubmissionResponseDto;
import com.eindopdracht.DJCorner.enums.Status;
import com.eindopdracht.DJCorner.models.Feedback;
import com.eindopdracht.DJCorner.models.Submission;
import com.eindopdracht.DJCorner.models.Tag;
import com.eindopdracht.DJCorner.models.User;
import com.eindopdracht.DJCorner.services.TagService;
import com.eindopdracht.DJCorner.services.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubmissionMapper {

    private final TagService tagService;
    private final UserService userService;

    public SubmissionMapper(TagService tagService, UserService userService) {
        this.tagService = tagService;
        this.userService = userService;
    }

    public Submission toEntity(SubmissionRequestDto submissionRequestDto) {
        Submission submission = new Submission();

        submission.setTitle(submissionRequestDto.getTitle());
        submission.setBpm(submissionRequestDto.getBpm());
        submission.setMusicFileName(submissionRequestDto.getMusicFileName());
        submission.setMusicFileType(submissionRequestDto.getMusicFileType());

        Feedback feedback = new Feedback();
        feedback.setStatus(Status.NO_FEEDBACK);
        submission.setFeedback(feedback);

        List<Tag> tagObjects = submissionRequestDto.getTags()
                .stream()
                .map(tagService::findOrCreateByName)
                .toList();

        submission.setTags(tagObjects);

        if (submissionRequestDto.getUserId() != null) {
            User user = userService.getSingleUser(submissionRequestDto.getUserId());
            submission.setUser(user);
        }

        return submission;
    }

    public static SubmissionResponseDto toSubmissionResponseDto(Submission submission) {
        SubmissionResponseDto submissionResponseDto = new SubmissionResponseDto();

        submissionResponseDto.setId(submission.getId());
        submissionResponseDto.setTitle(submission.getTitle());
        submissionResponseDto.setArtistName(submission.getArtistName());
        submissionResponseDto.setUploadDate(submission.getUploadDate());
        submissionResponseDto.setBpm(submission.getBpm());
        submissionResponseDto.setMusicFileName(submission.getMusicFileName());
        submissionResponseDto.setMusicFileType(submission.getMusicFileType());

        submissionResponseDto.setTags(
                submission.getTags().stream()
                        .map(Tag::getName)
                        .toList()
        );

        Feedback feedback = submission.getFeedback();
        if (feedback != null) {
            submissionResponseDto.setFeedbackId(
                    submission.getFeedback() != null ? submission.getFeedback().getId() : null
            );
            submissionResponseDto.setFeedbackStatus(
                    feedback.getStatus() != null ? feedback.getStatus().name() : null
            );
            submissionResponseDto.setFeedbackText(
                    feedback.getFeedback() != null ? feedback.getFeedback() : ""
            );
        } else {
            submissionResponseDto.setFeedbackStatus("NO_FEEDBACK");
            submissionResponseDto.setFeedbackText("");
        }

        submissionResponseDto.setAudioDownloadUrl("submissions/" + submission.getId() + "/audio");

        submissionResponseDto.setUserId(submission.getUser().getId());


        return submissionResponseDto;
    }

    public void updateEntity(Submission submission, SubmissionRequestDto submissionRequestDto) {
        submission.setTitle(submissionRequestDto.getTitle());
        submission.setArtistName(submissionRequestDto.getArtistName());
        submission.setBpm(submissionRequestDto.getBpm());
        submission.setMusicFileName(submissionRequestDto.getMusicFileName());
        submission.setMusicFileType(submissionRequestDto.getMusicFileType());

        if (submissionRequestDto.getTags() != null) {
            List<Tag> tagObjects = submissionRequestDto.getTags()
                    .stream()
                    .map(tagService::findOrCreateByName)
                    .toList();
            submission.setTags(tagObjects);
        }
    }

    public void patchEntity(Submission submission, SubmissionRequestDto submissionRequestDto) {
        if (submissionRequestDto.getTitle() != null) {
            submission.setTitle(submissionRequestDto.getTitle());
        }
        if (submissionRequestDto.getArtistName() != null) {
            submission.setArtistName(submissionRequestDto.getArtistName());
        }
        if (submissionRequestDto.getBpm() != null) {
            submission.setBpm(submissionRequestDto.getBpm());
        }
        if (submissionRequestDto.getMusicFileName() != null) {
            submission.setMusicFileName(submissionRequestDto.getMusicFileName());
        }
        if (submissionRequestDto.getMusicFileType() != null) {
            submission.setMusicFileType(submissionRequestDto.getMusicFileType());
        }
        if (submissionRequestDto.getTags() != null) {
            List<Tag> tagObjects = submissionRequestDto.getTags().stream()
                    .map(tagService::findOrCreateByName)
                    .toList();
            submission.setTags(tagObjects);
        }
    }

}
