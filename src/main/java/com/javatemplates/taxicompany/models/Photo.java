package com.javatemplates.taxicompany.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Map;

@Entity
@Table(name="photo")
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Photo{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonProperty("id")
    private Long id;

    private String title;

    private String description;
    @Lob
    @Column(name="stored_image")
    private byte[] image;

    public Photo(byte[] image) {
        this.image = image;
    }
}