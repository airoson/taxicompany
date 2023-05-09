package com.javatemplates.taxicompany.models.carmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javatemplates.taxicompany.models.Photo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @JsonProperty("photos")
    private void unpackNested(List<Object> ps){
        photos = new ArrayList<>();
        for(Object o: ps){
            Map<String, Integer> photo = (Map<String, Integer>) o;
            Photo p = new Photo();
            p.setId(photo.get("id").longValue());
            photos.add(p);
        }
    }
}
