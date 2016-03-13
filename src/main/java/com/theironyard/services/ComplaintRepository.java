package com.theironyard.services;

import com.theironyard.entities.Category;
import com.theironyard.entities.Complaint;
import org.hibernate.metamodel.source.binder.CompositePluralAttributeElementSource;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by alexanderhughes on 3/10/16.
 */
public interface ComplaintRepository extends CrudRepository<Complaint, Integer> {
    List<Complaint> findByCategory(Category category);
}
