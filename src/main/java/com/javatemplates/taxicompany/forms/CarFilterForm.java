package com.javatemplates.taxicompany.forms;

import com.javatemplates.taxicompany.models.carmodel.Engine;
import com.javatemplates.taxicompany.models.carmodel.Gearbox;
import com.javatemplates.taxicompany.models.carmodel.Rate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class CarFilterForm {
    private List<Rate> rates;
    private String sort;
    private int showMax;
    private List<Engine> engines;
    private List<Gearbox> gearboxes;
    private String company;
}
