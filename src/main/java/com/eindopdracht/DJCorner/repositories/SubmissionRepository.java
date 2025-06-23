package com.eindopdracht.DJCorner.repositories;

import com.eindopdracht.DJCorner.models.Submission;
import com.eindopdracht.DJCorner.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByUser(User user);
}
