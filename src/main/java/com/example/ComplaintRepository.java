package com.example;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by alexanderhughes on 3/10/16.
 */
public interface ComplaintRepository extends CrudRepository<Complaint, Integer> {
}
