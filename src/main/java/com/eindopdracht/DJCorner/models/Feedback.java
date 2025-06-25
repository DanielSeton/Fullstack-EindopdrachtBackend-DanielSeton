package com.eindopdracht.DJCorner.models;

import com.eindopdracht.DJCorner.enums.Status;
import jakarta.persistence.*;


@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String feedback;

    @Enumerated(EnumType.STRING)
    private Status status = Status.NO_FEEDBACK;

    @OneToOne(mappedBy = "feedback")
    private Submission submission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }
}
