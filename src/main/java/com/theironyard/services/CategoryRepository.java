package com.theironyard.services;

import com.theironyard.entities.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by alexanderhughes on 3/10/16.
 */
public interface CategoryRepository extends CrudRepository<Category, Integer> {
}
