package com.eindopdracht.DJCorner.repositories;

import com.eindopdracht.DJCorner.models.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

}
