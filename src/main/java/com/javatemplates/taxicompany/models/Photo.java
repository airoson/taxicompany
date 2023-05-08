package com.javatemplates.taxicompany.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name="photo")
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Photo{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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