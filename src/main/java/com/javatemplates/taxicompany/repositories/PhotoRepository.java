package com.javatemplates.taxicompany.repositories;

import com.javatemplates.taxicompany.models.Photo;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PhotoRepository extends CrudRepository<Photo, Long> {
    Optional<Photo> findById(Long id);
}
