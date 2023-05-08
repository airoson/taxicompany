package com.javatemplates.taxicompany.controllers;

import com.javatemplates.taxicompany.forms.ContactForm;
import com.javatemplates.taxicompany.models.User;
import com.javatemplates.taxicompany.models.carmodel.Car;
import com.javatemplates.taxicompany.services.CarService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller()
@RequestMapping("/contact")
public class ContactController {
    private CarService carService;

    public ContactController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public String contact(ContactForm form, Model model){
        model.addAttribute("username", form.getName());
        return "contact-confirm";
    }

    @PostMapping("/car/{id}")
    public String contactAboutCar(@PathVariable Long id, Model model, @AuthenticationPrincipal User user){
        model.addAttribute("username", user.getUsername());
        Car car = carService.findById(id).orElse(null);
        model.addAttribute("car", car);
        return "contact-confirm";
    }
}
