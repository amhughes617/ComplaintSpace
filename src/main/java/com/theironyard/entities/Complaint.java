package com.theironyard.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by alexanderhughes on 3/10/16.
 */
@Entity
public class Complaint {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    private Category category;
    @ManyToOne
    private User user;
    @NotNull
    private String text;
    @Transient
    private boolean isAuthor;

    public Complaint() {
    }

    public Complaint(Category category, User user, String text) {
        this.category = category;
        this.user = user;
        this.text = text;
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
}
