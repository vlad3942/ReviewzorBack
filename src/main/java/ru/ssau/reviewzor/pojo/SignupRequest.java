package ru.ssau.reviewzor.pojo;

import lombok.Data;
import ru.ssau.reviewzor.domain.User;

import java.util.Set;

@Data
public class SignupRequest {
    private String email;
    private String password;
    private Set<String> roles;

    public static User parseSignupRequestToUser(final SignupRequest signupRequest) {
        final User user = new User();
        user.setEmail(signupRequest.getEmail());
        return user;
    }
}
