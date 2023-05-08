package com.javatemplates.taxicompany.models.carmodel;

import com.javatemplates.taxicompany.models.Photo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name="car")
@AllArgsConstructor
@NoArgsConstructor(force=true)
public class Car implements Serializable {
    private static long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private Integer price, rentalPeriod, payFrequency;
    private List<Rate> rates;
    private Engine engine;
    private Gearbox gearbox;
    @Convert(converter = AddonsConverter.class)
    private List<String> addons;

    @OneToMany
    private List<Photo> photos;
}
