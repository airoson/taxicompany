package com.javatemplates.taxicompany.controllers;

import com.javatemplates.taxicompany.forms.CarFilter;
import com.javatemplates.taxicompany.forms.CarFilterForm;
import com.javatemplates.taxicompany.models.User;
import com.javatemplates.taxicompany.models.carmodel.Car;
import com.javatemplates.taxicompany.services.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cars")
@Slf4j
public class CarsViewController {
    private CarService carService;
    private CarFilter filter;

    public CarsViewController(CarService carService, CarFilter carFilter) {
        this.carService = carService;
        filter = carFilter;
    }

    @ModelAttribute
    public void addFilter(Model model){
        model.addAttribute("filter", filter);
    }

    @PostMapping("/all/setfilter")
    public String setFiler(CarFilterForm carFilterForm){
        filter.setSort(carFilterForm.getSort());
        filter.setCompany(carFilterForm.getCompany());
        filter.setGearboxes(carFilterForm.getGearboxes());
        filter.setEngines(carFilterForm.getEngines());
        filter.setRates(carFilterForm.getRates());
        filter.setShowMax(carFilterForm.getShowMax());
        if(filter.getCompany().equals("any"))
            filter.setItemsMatch(carService.countCarsByRatesAndGearboxesAndEngines(filter.getRates(), filter.getGearboxes(), filter.getEngines()));
        else
            filter.setItemsMatch(carService.countCarsByRatesAndGearboxesAndEnginesAndName(filter.getRates(), filter.getGearboxes(), filter.getEngines(), filter.getCompany()));
        return "redirect:/cars/all?page=0";
    }

    @GetMapping("/all")
    public String getCarsFiltered(@RequestParam int page, Model model, @AuthenticationPrincipal User user){
        Sort sort;
        if(filter.getSort().equals("default"))
            sort = Sort.by("id");
        else if(filter.getSort().equals("price"))
            sort = Sort.by("price");
        else if(filter.getSort().equals("rental-period-desc"))
            sort = Sort.by("rental_period").descending();
        else
            sort = Sort.by("rental_period");
        Pageable pageable = PageRequest.of(page, filter.getShowMax(), sort);
        List<Car> cars;
        if(filter.getCompany().equals("any")){
            cars = carService.findCarsByRatesAndGearboxesAndEngines(filter.getRates(),
                    filter.getGearboxes(), filter.getEngines(), pageable);
        }else{
            cars = carService.findCarsByRatesAndGearboxesAndEnginesAndCompany(filter.getRates(),
                    filter.getGearboxes(), filter.getEngines(), filter.getCompany(), pageable);
        }
        model.addAttribute("last", (long)(page + 1) * filter.getShowMax() >= filter.getItemsMatch());
        model.addAttribute("first", page == 0);
        model.addAttribute("page", page);
        model.addAttribute("cars", cars);
        if(user != null)
            model.addAttribute("user", user);
        return "cars";
    }

    @GetMapping("/{id}")
    public String getCar(@PathVariable Long id, Model model, @AuthenticationPrincipal User user){
        filter.setDefault(carService);
        int page = (int)(carService.getRowNumberOfCarByID(id) / 10);
        Pageable pageable = PageRequest.of(page, 10);
        List<Car> cars = carService.findCarsByRatesAndGearboxesAndEngines(filter.getRates(),
                filter.getGearboxes(), filter.getEngines(), pageable);
        model.addAttribute("last", (long)(page + 1) * filter.getShowMax() >= filter.getItemsMatch());
        model.addAttribute("first", page == 0);
        model.addAttribute("page", page);
        model.addAttribute("cars", cars);
        model.addAttribute("scrollTo", id);
        if(user != null)
            model.addAttribute("user", user);
        return "cars";
    }
}
