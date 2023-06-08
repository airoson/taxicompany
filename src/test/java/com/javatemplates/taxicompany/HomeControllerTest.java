package com.javatemplates.taxicompany;

import com.javatemplates.taxicompany.controllers.HomeController;
import com.javatemplates.taxicompany.forms.ContactForm;
import com.javatemplates.taxicompany.models.User;
import com.javatemplates.taxicompany.models.carmodel.Car;
import com.javatemplates.taxicompany.models.carmodel.Engine;
import com.javatemplates.taxicompany.models.carmodel.Gearbox;
import com.javatemplates.taxicompany.models.carmodel.Rate;
import com.javatemplates.taxicompany.services.CarService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
public class HomeControllerTest {
    private HomeController controller;

    private List<Car> cars;

    public HomeControllerTest(){
        CarService carService = mock(CarService.class);
        cars = new ArrayList<>();
        cars.add(new Car(1L, "Car 1", 100, 1, 1, Arrays.asList(Rate.ECONOMY), Engine.PETROL,
                Gearbox.MANUAL, new ArrayList<>(), new ArrayList<>()));
        cars.add(new Car(2L, "Car 2", 200, 1, 1, Arrays.asList(Rate.ECONOMY), Engine.PETROL,
                Gearbox.MANUAL, new ArrayList<>(), new ArrayList<>()));
        cars.add(new Car(3L, "Car 3", 300, 1, 1, Arrays.asList(Rate.ECONOMY), Engine.PETROL,
                Gearbox.MANUAL, new ArrayList<>(), new ArrayList<>()));
        given(carService.findRandomCars(3)).willReturn(cars);
        controller = new HomeController(carService);
    }


    @Test
    @DisplayName("HomeController interacts with not authenticated  user.")
    public void homeToNotAuthenticatedUser(){
        Model model = mock(Model.class);
        String result = controller.getHomeView(model, null);
        assertEquals("home", result);
        verify(model).addAttribute("contactForm", new ContactForm());
        verify(model).addAttribute("cars", cars);
        verify(model).addAttribute("admin", false);
        verify(model, never()).addAttribute("user", any());
    }

    @Test
    @DisplayName("HomeController interact with authenticated user.")
    public void homeToAuthenticatedUser(){
        Model model = mock(Model.class);
        User user = mock(User.class);
        Collection<?> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        doReturn(authorities).when(user).getAuthorities();
        String result = controller.getHomeView(model, user);
        assertEquals("home", result);
        verify(model).addAttribute("contactForm", new ContactForm());
        verify(model).addAttribute("cars", cars);
        verify(model).addAttribute("admin", false);
        verify(model).addAttribute("user", user);
    }

    @Test
    @DisplayName("HomeController interact with user logged as administrator.")
    public void homeToUserAdmin(){
        Model model = mock(Model.class);
        User user = mock(User.class);
        Collection<?> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_ADMIN"));
        doReturn(authorities).when(user).getAuthorities();
        String result = controller.getHomeView(model, user);
        assertEquals("home", result);
        verify(model).addAttribute("contactForm", new ContactForm());
        verify(model).addAttribute("cars", cars);
        verify(model).addAttribute("admin", true);
        verify(model).addAttribute("user", user);
    }
}
