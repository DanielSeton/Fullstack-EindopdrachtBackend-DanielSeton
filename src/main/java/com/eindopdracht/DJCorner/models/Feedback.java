package com.eindopdracht.DJCorner.models;

import jakarta.persistence.*;

@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String feedback;
    private String status;

    @OneToOne(mappedBy = "feedback")
    private Submission submission;

    public Feedback() { }

    public Feedback(String feedback) {
        this.feedback = feedback;
    }

    public Long getId() {
        return id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
