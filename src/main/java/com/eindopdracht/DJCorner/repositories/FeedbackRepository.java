package com.eindopdracht.DJCorner.repositories;

import com.eindopdracht.DJCorner.enums.Status;
import com.eindopdracht.DJCorner.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByStatus(Status status);
}
