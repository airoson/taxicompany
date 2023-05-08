package com.javatemplates.taxicompany.services;

import com.javatemplates.taxicompany.models.User;
import com.javatemplates.taxicompany.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByName(String name){
        return userRepository.findByName(name);
    }

    public User findByPhoneNumber(String phoneNumber){
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public boolean addUser(User user){
        if(userRepository.findByPhoneNumber(user.getPhoneNumber()) == null){
            userRepository.save(user);
            return true;
        }else return false;
    }

    public Iterable<User> findAll(){
        return userRepository.findAll();
    }

    public boolean deleteUserById(long id){
        if(userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }
}
