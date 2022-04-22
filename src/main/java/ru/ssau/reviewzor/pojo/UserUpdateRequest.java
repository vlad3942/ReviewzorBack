package ru.ssau.reviewzor.pojo;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String name;
    private String surname;
}
