package com.javatemplates.taxicompany.repositories;

import com.javatemplates.taxicompany.models.Photo;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

public interface PhotoRepository extends CrudRepository<Photo, Long> {

}
