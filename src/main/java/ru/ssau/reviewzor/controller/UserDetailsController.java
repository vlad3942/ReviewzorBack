package ru.ssau.reviewzor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ssau.reviewzor.domain.User;
import ru.ssau.reviewzor.pojo.UserUpdateRequest;
import ru.ssau.reviewzor.service.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserDetailsController {

    private final UserService userService;

    @PostMapping("/update")
    public ResponseEntity<?> update(
            @RequestHeader("Authorization") final String authToken,
            @RequestBody final UserUpdateRequest updateData
    ) {
        return userService.updateUserData(authToken, updateData);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(
            @RequestHeader("Authorization") final String authToken
    ) {
        return userService.getUserByToken(authToken);
    }
}
