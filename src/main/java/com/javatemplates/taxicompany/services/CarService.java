package com.javatemplates.taxicompany.services;

import com.javatemplates.taxicompany.models.carmodel.Car;
import com.javatemplates.taxicompany.models.carmodel.Engine;
import com.javatemplates.taxicompany.models.carmodel.Gearbox;
import com.javatemplates.taxicompany.models.carmodel.Rate;
import com.javatemplates.taxicompany.repositories.CarRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void addCar(Car car){
        carRepository.save(car);
    }

    public long countCarsByRatesAndGearboxesAndEngines(List<Rate> rates, List<Gearbox> gearboxes,
                                                       List<Engine> engines){
        return carRepository.countCarsByRatesAndGearboxAndEngine(
                        rates.toArray(Rate[]::new),
                engines.stream().map(Engine::getType).toArray(String[]::new),
                gearboxes.stream().map(Gearbox::getType).toArray(String[]::new));
    }

    public long countCarsByRatesAndGearboxesAndEnginesAndName(List<Rate> rates, List<Gearbox> gearboxes,
                                                                 List<Engine> engines, String name){
        return carRepository.countCarsByRatesAndGearboxAndEngineAndName(
                rates.toArray(Rate[]::new),
                engines.stream().map(Engine::getType).toArray(String[]::new),
                gearboxes.stream().map(Gearbox::getType).toArray(String[]::new),
                "%" + name + "%");
    }

    public long countAll(){
        return carRepository.count();
    }

    public Iterable<Car> findRandomCars(int count){
        int pages = (int)carRepository.count() / count;
        return carRepository.findAll(PageRequest.of((int)(Math.random() * pages), count));
    }

    public List<Car> findAllCars(Pageable page) {
        return carRepository.findAll(page).toList();
    }

    public Optional<Car> findById(long id){
        return carRepository.findById(id);
    }

    public Optional<Car> findById(long id, Pageable page){
        return carRepository.findById(id, page).stream().findAny();
    }

    public List<Car> findCarsByRatesAndGearboxesAndEngines(List<Rate> rates, List<Gearbox> gearboxes,
                                                               List<Engine> engines, Pageable page){
        return carRepository.findAllByRatesAndGearboxAndEngine(
                rates.toArray(Rate[]::new),
                engines.stream().map(Engine::getType).toArray(String[]::new),
                gearboxes.stream().map(Gearbox::getType).toArray(String[]::new),
                page);
    }

    public List<Car> findCarsByRatesAndGearboxesAndEnginesAndCompany(List<Rate> rates, List<Gearbox> gearboxes,
                                                           List<Engine> engines, String company, Pageable page){
        return carRepository.findAllByRatesAndGearboxAndEngineAndName(
                rates.toArray(Rate[]::new),
                engines.stream().map(Engine::getType).toArray(String[]::new),
                gearboxes.stream().map(Gearbox::getType).toArray(String[]::new),
                company + "%",
                page);
    }

    public List<String> getAvailableCarsCompanies(){
        List<String> names = carRepository.findAllNames();
        return names.stream().map(name -> name.split(" ")[0]).distinct().toList();
    }

    public long getRowNumberOfCarByID(long id){
        return carRepository.getRowNumberOfCarByID(id);
    }

    public boolean deleteById(long id){
        if(carRepository.findById(id).isPresent()){
            carRepository.deleteById(id);
            return true;
        }else return false;
    }

    public List<Car> findCarsByName(String name, Pageable page){
        return carRepository.findByName("%" + name + "%", page);
    }
}
