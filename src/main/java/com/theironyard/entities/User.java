package com.theironyard.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by alexanderhughes on 3/10/16.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    UUID id;
    @NotNull
    private String userName;
    @NotNull
    private String password;

    public User() {
    }

    public User(UUID id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
