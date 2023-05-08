package com.javatemplates.taxicompany.controllers;


import com.javatemplates.taxicompany.forms.ContactForm;
import com.javatemplates.taxicompany.models.User;
import com.javatemplates.taxicompany.services.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Slf4j
public class HomeController {
    private CarService carServices;

    public HomeController(CarService carServices) {

        this.carServices = carServices;
    }

    @GetMapping("/home")
    public String getHomeView(Model model, @AuthenticationPrincipal User user){
        if(user != null) {
            log.info(String.format("Current user's name is %s phone is %s email is %s", user.getName(), user.getPhoneNumber(), user.getEmail()));
            model.addAttribute("user", user);
        }
        model.addAttribute("contactForm", new ContactForm());
        model.addAttribute("cars", carServices.findThreeCars());
        if(user != null && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            model.addAttribute("admin", true);
        else
            model.addAttribute("admin", false);
        return "home";
    }
}
