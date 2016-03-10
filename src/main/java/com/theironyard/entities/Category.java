package com.theironyard.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by alexanderhughes on 3/10/16.
 */
@Entity
public class Category {
    @Id
    private UUID id;
    @NotNull
    private String category;

    public Category() {
    }

    public Category(UUID id, String category) {
        this.id = id;
        this.category = category;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
