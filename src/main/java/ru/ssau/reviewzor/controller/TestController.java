package ru.ssau.reviewzor.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/all")
    public String getAll() {
        return "public test API";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    public String getUserApi() {
        return "User API";
    }

    @GetMapping("/moder")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public String getModeratorApi() {
        return "Moderator API";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdminApi() {
        return "Admin API";
    }
}
