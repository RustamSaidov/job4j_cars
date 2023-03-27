package ru.job4j.cars.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "photo")
@Data
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String path;
}