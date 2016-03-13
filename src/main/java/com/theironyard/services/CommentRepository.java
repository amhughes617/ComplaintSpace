package com.theironyard.services;

import com.theironyard.entities.Comment;
import com.theironyard.entities.Complaint;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by alexanderhughes on 3/10/16.
 */
public interface CommentRepository extends CrudRepository<Comment, Integer> {
    List<Comment> findByComplaint(Complaint complaint);
}
