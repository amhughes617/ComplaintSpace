package com.theironyard.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by alexanderhughes on 3/10/16.
 */
@Entity
public class Comment {
    @Id
    private UUID id;
    @NotNull
    private String text;
    @ManyToOne
    private Complaint complaint;
    @ManyToOne
    private User user;

    public Comment() {
    }

    public Comment(UUID id, String text, Complaint complaint, User user) {
        this.id = id;
        this.text = text;
        this.complaint = complaint;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Complaint getComplaint() {
        return complaint;
    }

    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
