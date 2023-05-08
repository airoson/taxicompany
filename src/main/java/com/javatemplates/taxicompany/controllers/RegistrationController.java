package com.javatemplates.taxicompany.controllers;

import com.javatemplates.taxicompany.security.RegistrationForm;
import com.javatemplates.taxicompany.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    UserService userService;
    PasswordEncoder encoder;

    public RegistrationController(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @GetMapping
    public String getRegistrationView(){
        return "regin";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form, Model model){
        if(!userService.addUser(form.toUser(encoder))) {
            model.addAttribute("alreadyExists", true);
            return "regin";
        }else
            return "redirect:/home";
    }
}
