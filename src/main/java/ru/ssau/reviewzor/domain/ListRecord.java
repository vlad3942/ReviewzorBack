package ru.ssau.reviewzor.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class ListRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String imageName;
    private String restaurantName;
    private Double grade;
    private boolean isChecked;
}
