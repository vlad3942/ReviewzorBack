package ru.ssau.reviewzor.service;

import org.springframework.http.ResponseEntity;
import ru.ssau.reviewzor.pojo.JwtResponse;
import ru.ssau.reviewzor.pojo.LoginRequest;
import ru.ssau.reviewzor.pojo.SignupRequest;

public interface AuthService {
    JwtResponse authUser(LoginRequest loginRequest);
    ResponseEntity<?> registerUser(SignupRequest signupRequest);
}
