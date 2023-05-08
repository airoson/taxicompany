package com.javatemplates.taxicompany.controllers;


import com.javatemplates.taxicompany.models.carmodel.Car;
import com.javatemplates.taxicompany.services.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/cars", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class CarsController {
    private CarService carService;

    public CarsController(CarService carService) {
        this.carService = carService;
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

    @PatchMapping(path="/{id}", consumes = "application/json")
    public ResponseEntity<Car> patchCar(@PathVariable long id, @RequestBody Car car){
        Car oldCar = carService.findById(id).orElse(null);
        if(oldCar != null){
            if(car.getName() != null){
                oldCar.setName(car.getName());
            }
            if(car.getEngine() != null){
                oldCar.setEngine(car.getEngine());
            }
            if(car.getGearbox() != null){
                oldCar.setGearbox(car.getGearbox());
            }
            if(car.getRates() != null){
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
            if(car.getAddons() != null){
                oldCar.setAddons(car.getAddons());
            }
            carService.addCar(oldCar);
            return new ResponseEntity<>(oldCar, HttpStatus.OK);
        }else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
