package com.eindopdracht.DJCorner.repositories;

import com.eindopdracht.DJCorner.enums.Status;
import com.eindopdracht.DJCorner.models.Submission;
import com.eindopdracht.DJCorner.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Optional<Submission> findByFeedbackId(Long feedbackId);

    Page<Submission> findAll(Pageable pageable);

    Page<Submission> findSubmissionByUser(User user, Pageable pageable);

    Page<Submission> findByFeedback_Status(Status status, Pageable pageable);

    @Query(value = """
        SELECT s.*
        FROM submissions s
        JOIN submission_tags st ON s.id = st.submission_id
        JOIN tags t ON st.tag_id = t.id
        LEFT JOIN feedback f ON s.feedback_id = f.id
        WHERE (:tagNamesSize = 0 OR t.name IN (:tagNames))
          AND (:status IS NULL OR f.status = :status)
        GROUP BY s.id
        HAVING (:tagNamesSize = 0 OR COUNT(DISTINCT t.name) = :tagNamesSize)
        """, nativeQuery = true)
    Page<Submission> filterSubmissions(
            @Param("tagNames") List<String> tagNames,
            @Param("tagNamesSize") long tagNamesSize,
            @Param("status") String status,
            Pageable pageable
    );
}
