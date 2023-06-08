package com.javatemplates.taxicompany.services;

import com.javatemplates.taxicompany.models.User;
import com.javatemplates.taxicompany.models.carmodel.Car;
import com.javatemplates.taxicompany.repositories.CarRepository;
import com.javatemplates.taxicompany.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private CarRepository carRepository;

    public UserService(UserRepository userRepository, CarRepository carRepository) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    public Iterable<User> findByName(String name){
        Iterable<User> users = userRepository.findByName(name);
        if(users.iterator().hasNext())
            return users;
        else return null;
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

    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public boolean addFavoriteCar(User user, Long carId){
        if(user == null)
            return false;
        Car favoriteCar = carRepository.findById(carId).orElse(null);
        if(favoriteCar == null)
            return false;
        List<Car> favoriteCars = user.getFavorites();
        favoriteCars.add(favoriteCar);
        user.setFavorites(favoriteCars);
        userRepository.save(user);
        return true;
    }

    public boolean deleteFavoriteCar(User user, Long carId){
        if(user == null)
            return false;
        List<Car> favorites = user.getFavorites();
        for(Car favorite: favorites){
            if(favorite.getId().equals(carId)){
                favorites.remove(favorite);
                user.setFavorites(favorites);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
}
