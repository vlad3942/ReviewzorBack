package ru.ssau.reviewzor.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class PlacesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String name;
    private String category;
    private Boolean follow;
    private String address;
    private Double latitude;
    private Double longitude;
    private String detail;
    private Double rating;

    private String fileName;
}
