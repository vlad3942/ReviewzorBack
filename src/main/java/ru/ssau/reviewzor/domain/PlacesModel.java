package ru.ssau.reviewzor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class PlacesModel {
    @Id
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

    @JsonIgnore
    private Long userId;
}
