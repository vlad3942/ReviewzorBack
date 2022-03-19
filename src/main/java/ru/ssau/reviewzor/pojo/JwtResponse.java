package ru.ssau.reviewzor.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private final String type = "Bearer";
    private Long userId;
    private String username;
    private String name;
    private String surname;
    private Set<String> roles;
}
