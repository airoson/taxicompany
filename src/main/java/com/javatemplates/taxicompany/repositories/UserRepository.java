package com.javatemplates.taxicompany.repositories;

import com.javatemplates.taxicompany.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Iterable<User> findByName(String name);
    User findByPhoneNumber(String phoneNumber);
}
