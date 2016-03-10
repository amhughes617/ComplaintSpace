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
public class Complaint {
    @Id
    UUID id;
    @ManyToOne
    private Category category;
    @ManyToOne
    private User user;
    @NotNull
    private String title;
    @NotNull
    private String text;

    public Complaint() {
    }

    public Complaint(UUID id, String title, Category category, User user, String text) {
        this.id = id;
        this.category = category;
        this.user = user;
        this.text = text;
        this.title = title;
    }

    public UUID getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
