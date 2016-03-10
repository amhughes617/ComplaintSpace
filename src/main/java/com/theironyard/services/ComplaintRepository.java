package com.theironyard.services;

import com.theironyard.entities.Complaint;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * Created by alexanderhughes on 3/10/16.
 */
public interface ComplaintRepository extends CrudRepository<Complaint, Integer> {
    Complaint findById(UUID id);
}
