package com.theironyard.services;

import com.theironyard.entities.Comment;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by alexanderhughes on 3/10/16.
 */
public interface CommentRepository extends CrudRepository<Comment, Integer> {
}
