package com.theironyard.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by alexanderhughes on 3/10/16.
 */
@Entity
public class Comment {
    @Id
    @GeneratedValue
    private int id;
    @NotNull
    private String text;
    @ManyToOne
    private Complaint complaint;
    @ManyToOne
    private User user;
    @Transient
    private boolean isAuthor;

    public Comment() {
    }

    public Comment(String text, Complaint complaint, User user) {
        this.text = text;
        this.complaint = complaint;
        this.user = user;
    }

    public boolean isAuthor() {
        return isAuthor;
    }

    public void setAuthor(boolean author) {
        isAuthor = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
