package com.javatemplates.taxicompany.forms;

import com.javatemplates.taxicompany.models.carmodel.Engine;
import com.javatemplates.taxicompany.models.carmodel.Gearbox;
import com.javatemplates.taxicompany.models.carmodel.Rate;
import com.javatemplates.taxicompany.services.CarService;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Component
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CarFilter {
    private List<Rate> rates;
    private String sort;
    private int showMax;
    private List<Engine> engines;
    private List<Gearbox> gearboxes;
    private long itemsMatch;
    private CarService carService;
    private String company;
    private List<String> availableCompanies;

    public CarFilter(CarService carService){
        setDefault(carService);
    }

    public void setDefault(CarService carService){
        rates= new ArrayList<>(Arrays.asList(Rate.values()));
        sort="default";
        showMax=10;
        engines = new ArrayList<>(Arrays.asList(Engine.values()));
        gearboxes = new ArrayList<>(Arrays.asList(Gearbox.values()));
        itemsMatch = carService.countAll();
        company = "any";
        availableCompanies = carService.getAvailableCarsCompanies();
    }
}
