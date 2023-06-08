package com.javatemplates.taxicompany.controllers;

import com.javatemplates.taxicompany.models.carmodel.Car;
import com.javatemplates.taxicompany.services.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.javatemplates.taxicompany.models.User;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class AccountController {
    private UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/account")
    public String getUserAccount(Principal principal, Model model){
        User user = userService.findByPhoneNumber(principal.getName());
        List<Car> favorites = user.getFavorites();
        model.addAttribute("favorites", favorites);
        model.addAttribute("user", user);
        if(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            model.addAttribute("admin", true);
        else
            model.addAttribute("admin", false);
        return "account";
    }
}
