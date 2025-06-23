package com.eindopdracht.DJCorner.mappers;

import com.eindopdracht.DJCorner.dtos.SubmissionRequestDto;
import com.eindopdracht.DJCorner.dtos.SubmissionResponseDto;
import com.eindopdracht.DJCorner.models.Submission;
import com.eindopdracht.DJCorner.models.Tag;

public class SubmissionMapper {

    public static Submission toEntity(SubmissionRequestDto submissionRequestDto) {
        Submission submission = new Submission();

        submission.setTitle(submissionRequestDto.getTitle());
        submission.setArtistName(submissionRequestDto.getArtistName());
        submission.setUploadDate(submissionRequestDto.getUploadDate());
        submission.setBpm(submissionRequestDto.getBpm());
        submission.setMusicFile(submissionRequestDto.getMusicFile());
        submission.setMusicFileName(submissionRequestDto.getMusicFileName());
        submission.setMusicFileType(submissionRequestDto.getMusicFileType());
        submission.setFeedback(submissionRequestDto.getFeedback());
        submission.setUser(submissionRequestDto.getUser());
        submission.setTags(submissionRequestDto.getTags());

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

        submissionResponseDto.setFeedbackSummary(null);

        submissionResponseDto.setAudioDownloadUrl("submissions/" + submission.getId() + "/audio");

        return submissionResponseDto;
    }

    public static void updateEntity(Submission submission, SubmissionRequestDto submissionRequestDto) {
        submission.setTitle(submissionRequestDto.getTitle());
        submission.setArtistName(submissionRequestDto.getArtistName());
        submission.setUploadDate(submissionRequestDto.getUploadDate());
        submission.setBpm(submissionRequestDto.getBpm());
        submission.setMusicFile(submissionRequestDto.getMusicFile());
        submission.setMusicFileName(submissionRequestDto.getMusicFileName());
        submission.setMusicFileType(submissionRequestDto.getMusicFileType());
        submission.setTags(submissionRequestDto.getTags());
    }

    public static void patchEntity(Submission submission, SubmissionRequestDto submissionRequestDto) {
        if (submissionRequestDto.getTitle() != null) {
            submission.setTitle(submissionRequestDto.getTitle());
        }
        if (submissionRequestDto.getArtistName() != null) {
            submission.setArtistName(submissionRequestDto.getArtistName());
        }
        if (submissionRequestDto.getUploadDate() != null) {
            submission.setUploadDate(submissionRequestDto.getUploadDate());
        }
        if (submissionRequestDto.getBpm() != null) {
            submission.setBpm(submissionRequestDto.getBpm());
        }
        if (submissionRequestDto.getMusicFile() != null) {
            submission.setMusicFile(submissionRequestDto.getMusicFile());
        }
        if (submissionRequestDto.getMusicFileName() != null) {
            submission.setMusicFileName(submissionRequestDto.getMusicFileName());
        }
        if (submissionRequestDto.getMusicFileType() != null) {
            submission.setMusicFileType(submissionRequestDto.getMusicFileType());
        }
        if (submissionRequestDto.getTags() != null) {
            submission.setTags(submissionRequestDto.getTags());
        }
    }

}
