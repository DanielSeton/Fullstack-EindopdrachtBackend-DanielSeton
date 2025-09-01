package com.eindopdracht.DJCorner.services;

import com.eindopdracht.DJCorner.dtos.FeedbackResponseDto;
import com.eindopdracht.DJCorner.enums.Status;
import com.eindopdracht.DJCorner.exceptions.ResourceNotFoundException;
import com.eindopdracht.DJCorner.models.Feedback;
import com.eindopdracht.DJCorner.models.Submission;
import com.eindopdracht.DJCorner.repositories.FeedbackRepository;
import com.eindopdracht.DJCorner.repositories.SubmissionRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private SubmissionRepository submissionRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    private Feedback feedback;
    private Feedback feedback1;
    private Submission submission;

    @BeforeEach
    void setUp() {
        // Arrange
        feedback = new Feedback();
        feedback.setId(1L);
        feedback.setFeedback("Test Feedback");
        feedback.setStatus(Status.APPROVED);

        submission = new Submission();
        submission.setId(1L);
        submission.setFeedback(feedback);

        feedback1 = new Feedback();
        feedback1.setId(2L);
        feedback1.setFeedback("");
        feedback1.setStatus(Status.NO_FEEDBACK);


    }

    @AfterEach
    void tearDown() {
    }


    @Test
    @DisplayName("Should get all feedback")
    void getFeedback() {
        // Arrange
        when(feedbackRepository.findAll()).thenReturn(List.of(feedback, feedback1));

        // Act
        List<FeedbackResponseDto> result = feedbackService.getFeedback();

        // Assert
        assertEquals(2, result.size());

        FeedbackResponseDto dto1 = result.get(0);
        assertEquals(1L, dto1.getId());
        assertEquals("Test Feedback", dto1.getFeedback());
        assertEquals(Status.APPROVED, dto1.getStatus());

        FeedbackResponseDto dto2 = result.get(1);
        assertEquals(2L, dto2.getId());
        assertEquals("", dto2.getFeedback());
        assertEquals(Status.NO_FEEDBACK, dto2.getStatus());
    }

    @Test
    @DisplayName("Should get Feedback by id")
    void getFeedbackById() {
        // Arrange
        when(feedbackRepository.findById(1L)).thenReturn(Optional.of(feedback));

        // Act
        Feedback result = feedbackService.getFeedbackById(1L);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("Test Feedback", result.getFeedback());
        assertEquals(Status.APPROVED, result.getStatus());
    }

    @Test
    @DisplayName("Should throw ResourceNotFound if Feedback not found when fetching")
    void getFeedbackById_ResourceNotFound() {
        // Arrange
        when(feedbackRepository.findById(1L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            feedbackService.getFeedbackById(1L);
        });
    }

    @Test
    @DisplayName("Should get feedback based on status")
    void getFeedbackByStatus() {
        // Arrange
        when(feedbackRepository.findByStatus(Status.APPROVED)).thenReturn(List.of(feedback));

        // Act
        List<Feedback> result = feedbackService.getFeedbackByStatus(Status.APPROVED);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should delete feedback and make empty feedback")
    void deleteFeedback() {
        // Arrange
        when(submissionRepository.findByFeedbackId(1L)).thenReturn(Optional.of(submission));
        when(feedbackRepository.findById(1L)).thenReturn(Optional.of(feedback));

        // Act
        feedbackService.deleteFeedback(1L);

        // Assert
        verify(feedbackRepository).deleteById(1L);

        ArgumentCaptor<Feedback> feedbackCaptor = ArgumentCaptor.forClass(Feedback.class);
        verify(feedbackRepository).save(feedbackCaptor.capture());
        Feedback savedFeedback = feedbackCaptor.getValue();
        assertEquals("", savedFeedback.getFeedback());
        assertEquals(Status.NO_FEEDBACK, savedFeedback.getStatus());

        assertEquals(savedFeedback, submission.getFeedback());

        verify(submissionRepository).save(submission);
    }

    @Test
    @DisplayName("Should throw ResourceNotFound if Feedback not found when deleting")
    void deleteFeedback_ResourceNotFound() {
        // Arrange
        when(feedbackRepository.findById(1L)).thenReturn(Optional.of(feedback));
        when(submissionRepository.findByFeedbackId(1L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            feedbackService.deleteFeedback(1L);
        });
    }
}