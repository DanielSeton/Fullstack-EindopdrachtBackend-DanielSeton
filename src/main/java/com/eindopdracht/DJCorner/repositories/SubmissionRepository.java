package com.eindopdracht.DJCorner.repositories;

import com.eindopdracht.DJCorner.models.Submission;
import com.eindopdracht.DJCorner.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByUser(User user);
    Optional<Submission> findByFeedbackId(Long feedbackId);
}
