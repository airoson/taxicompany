package com.javatemplates.taxicompany.controllers;

import com.javatemplates.taxicompany.security.RegistrationForm;
import com.javatemplates.taxicompany.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public String getRegistrationView(Model model){
        model.addAttribute("reginForm", new RegistrationForm());
        return "regin";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form, Model model){
        Matcher m = Pattern.compile("(?:([a-z])|([A-Z])|(\\d)|([`~!@#$%^&*()\\-_=+\\[\\]{},<.>/?])){6,20}").matcher(form.getPassword());
        if(!m.matches() || m.group(1) == null || m.group(2) == null || m.group(3) == null || m.group(4) == null){
            model.addAttribute("wrongPassword", true);
            model.addAttribute("reginForm", form);
            return "regin";
        }
        if(form.getEmail() != null && !form.getEmail().isEmpty()){
            m = Pattern.compile("[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}").matcher(form.getEmail());
            if(!m.matches()){
                model.addAttribute("wrongEmail", true);
                model.addAttribute("reginForm", form);
                return "regin";
            }
        }
        m = Pattern.compile("\\W*\\+\\d+-?\\d\\d\\d-?\\d\\d\\d-?\\d\\d-?\\d\\d\\W*").matcher(form.getPhoneNumber());
        if(!m.matches()){
            model.addAttribute("wrongPhoneNumber", true);
            model.addAttribute("reginForm", form);
            return "regin";
        }else{
            form.setPhoneNumber(form.getPhoneNumber().trim().replace("-", ""));
        }
        if(!userService.addUser(form.toUser(encoder))) {
            model.addAttribute("alreadyExists", true);
            model.addAttribute("reginForm", form);
            return "regin";
        }else
            return "redirect:/login";
    }
}
