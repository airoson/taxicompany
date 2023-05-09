package com.javatemplates.taxicompany.controllers;

import com.javatemplates.taxicompany.forms.ContactForm;
import com.javatemplates.taxicompany.models.User;
import com.javatemplates.taxicompany.models.carmodel.Car;
import com.javatemplates.taxicompany.services.CarService;
import com.javatemplates.taxicompany.services.MailService;
import org.springframework.beans.factory.annotation.Value;
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
    private MailService mailService;

    @Value("${email.sendto}")
    private String sendMessagesTo;
    public ContactController(CarService carService, MailService mailService) {
        this.carService = carService;
        this.mailService = mailService;
    }

    @PostMapping
    public String contact(ContactForm form, Model model){
        model.addAttribute("username", form.getName());
        mailService.sendSimpleMessage(sendMessagesTo,
                "Новый клиент",
                String.format("Имя клиента: %s\nТелефон: %s\nВодительское удостоверение: %s",
                        form.getName(),
                        form.getPhoneNumber(),
                        !form.getDriverLicense().isEmpty()? form.getDriverLicense(): "Не указано."));
        return "contact-confirm";
    }

    @PostMapping("/car/{id}")
    public String contactAboutCar(@PathVariable Long id, Model model, @AuthenticationPrincipal User user){
        model.addAttribute("username", user.getUsername());
        Car car = carService.findById(id).orElse(null);
        model.addAttribute("car", car);
        mailService.sendSimpleMessage(sendMessagesTo,
                "Новый клиент",
                String.format("Имя клиента: %s\nТелефон: %s\nПочта: %s\nАвтмобиль: %s",
                        user.getName(),
                        user.getPhoneNumber(),
                        !user.getEmail().isEmpty()?user.getEmail():"Не указана",
                        car.getName() + " , " + car.getPrice() + "Р, ID в системе: " + id));
        return "contact-confirm";
    }
}
