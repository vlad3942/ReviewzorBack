package ru.ssau.reviewzor.pojo;

import lombok.Data;
import ru.ssau.reviewzor.domain.User;

import java.util.Set;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private String name;
    private String surname;
    private Set<String> roles;

    public static User parseSignupRequestToUser(final SignupRequest signupRequest) {
        final User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setName(signupRequest.getName());
        user.setSurname(signupRequest.getSurname());
        return user;
    }
}
