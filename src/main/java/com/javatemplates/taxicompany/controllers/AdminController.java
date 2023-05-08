package com.javatemplates.taxicompany.controllers;

import com.javatemplates.taxicompany.forms.AdminCarFilter;
import com.javatemplates.taxicompany.forms.CarFilter;
import com.javatemplates.taxicompany.models.carmodel.Car;
import com.javatemplates.taxicompany.models.carmodel.Engine;
import com.javatemplates.taxicompany.models.carmodel.Gearbox;
import com.javatemplates.taxicompany.models.carmodel.Rate;
import com.javatemplates.taxicompany.services.CarService;
import com.javatemplates.taxicompany.services.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private CarService carService;
    private AdminCarFilter carFilter;

    public AdminController(UserService userService, CarService carService, AdminCarFilter carFilter) {
        this.userService = userService;
        this.carService = carService;
        this.carFilter = carFilter;
    }

    @GetMapping
    public String getAdminView(){
        return "admin";
    }

    @GetMapping("/users")
    public String getUsersAdminView(Model model){
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @ModelAttribute
    public void addFilter(Model model){
        model.addAttribute("filter", carFilter);
    }

    @PostMapping("/cars/setfilter")
    public String setFilter(@RequestParam(required = false, defaultValue = "-1") long id,
                            @RequestParam(required = false, defaultValue = "") String name,
                            @RequestParam int size){
        carFilter.setSize(size);
        if(id != -1){
            carFilter.setId(id);
            carFilter.setName("");
        }else if(!name.isEmpty()){
            carFilter.setName(name);
            carFilter.setId(-1);
        }else{
            carFilter.setName("");
            carFilter.setId(-1);
        }
        return "redirect:/admin/cars?page=0";
    }

    @GetMapping("/cars")
    public String getCarsAdminView(@RequestParam int page,  Model model){
        List<Car> cars = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, carFilter.getSize());
        if(carFilter.getId() != -1){
            Optional<Car> car = carService.findById(carFilter.getId(), pageable);
            if(car.isPresent())
                cars.add(car.get());
        }else if(!carFilter.getName().isEmpty()){
            cars.addAll(carService.findCarsByName(carFilter.getName(), pageable));
        }else{
            cars.addAll(carService.findAllCars(pageable));
        }
        model.addAttribute("cars", cars);
        model.addAttribute("first", page == 0);
        model.addAttribute("last", (carFilter.getId() != -1) || ( !carFilter.getName().isEmpty() &&
                carService.countCarsByRatesAndGearboxesAndEnginesAndName(Arrays.asList(Rate.values()),
                        Arrays.asList(Gearbox.values()), Arrays.asList(Engine.values()),
                carFilter.getName()) <= page * ((long)carFilter.getSize() + 1)) ||
                carService.countAll() <= ((long)carFilter.getSize() + 1) * page
                );
        model.addAttribute("page", page);
        return "cars-admin";
    }
}