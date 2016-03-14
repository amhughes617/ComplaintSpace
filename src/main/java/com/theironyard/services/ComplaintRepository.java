package com.theironyard.services;

import com.theironyard.entities.Category;
import com.theironyard.entities.Complaint;
import org.hibernate.metamodel.source.binder.CompositePluralAttributeElementSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by alexanderhughes on 3/10/16.
 */
public interface ComplaintRepository extends PagingAndSortingRepository<Complaint, Integer> {
    Page<Complaint> findByCategory(Pageable pageable, Category category);
    Page<Complaint> findAll(Pageable pageable);
}
