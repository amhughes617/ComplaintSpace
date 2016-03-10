package com.theironyard.services;

import com.theironyard.entities.Complaint;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by alexanderhughes on 3/10/16.
 */
public interface ComplaintRepository extends CrudRepository<Complaint, Integer> {
}
