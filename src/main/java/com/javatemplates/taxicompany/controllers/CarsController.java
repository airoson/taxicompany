package com.javatemplates.taxicompany.controllers;


import com.javatemplates.taxicompany.models.Photo;
import com.javatemplates.taxicompany.models.carmodel.Car;
import com.javatemplates.taxicompany.services.CarService;
import com.javatemplates.taxicompany.services.PhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/api/cars", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class CarsController {
    private CarService carService;
    private PhotoService photoService;

    public CarsController(CarService carService, PhotoService photoService) {
        this.carService = carService;
        this.photoService = photoService;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable long id){
        carService.deleteById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCar(@PathVariable long id){
        Car car = carService.findById(id).orElse(null);
        if(car != null)
            return new ResponseEntity<>(car, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<Car> putCar(@RequestBody Car car){
        if(car.getRates() == null || car.getRates().isEmpty())
            return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
        if(car.getName() == null || car.getName().isEmpty())
            return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
        if(car.getEngine() == null)
            return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
        if(car.getGearbox() == null)
            return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
        if(car.getPhotos() == null || car.getPhotos().isEmpty())
            return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
        carService.addCar(car);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @PatchMapping(path="/{id}", consumes = "application/json")
    public ResponseEntity<Car> patchCar(@PathVariable long id, @RequestBody Car car){
        Car oldCar = carService.findById(id).orElse(null);
        if(oldCar != null){
            if(car.getName() != null){
                if(car.getName().isEmpty()){
                    return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
                }
                oldCar.setName(car.getName());
            }
            if(car.getEngine() != null){
                oldCar.setEngine(car.getEngine());
            }
            if(car.getGearbox() != null){
                oldCar.setGearbox(car.getGearbox());
            }
            if(car.getRates() != null){
                if(car.getRates().isEmpty()){
                    return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
                }
                oldCar.setRates(car.getRates());
            }
            if(car.getPrice() != null){
                oldCar.setPrice(car.getPrice());
            }
            if(car.getPayFrequency() != null){
                oldCar.setPayFrequency(car.getPayFrequency());
            }
            if(car.getRentalPeriod() != null){
                oldCar.setRentalPeriod(car.getRentalPeriod());
            }
            if(car.getAddons() != null && !car.getAddons().isEmpty()){
                oldCar.setAddons(car.getAddons());
            }
            log.info("Car photos {}", car.getPhotos());
            if(car.getPhotos() != null && car.getPhotos().size() > 0){
                List<Photo> photos = new ArrayList<>();
                for(Photo p: car.getPhotos()){
                    photos.add(photoService.findPhotoById(p.getId()));
                }
                List<Photo> oldPhoto = oldCar.getPhotos();
                oldCar.setPhotos(photos);
                for(Photo p: oldPhoto){
                    photoService.deleteById(p.getId());
                }
            }
            carService.addCar(oldCar);
            return new ResponseEntity<>(oldCar, HttpStatus.OK);
        }else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
