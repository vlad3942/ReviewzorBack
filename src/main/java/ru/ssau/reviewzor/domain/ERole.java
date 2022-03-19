package ru.ssau.reviewzor.domain;

import lombok.Getter;

public enum ERole {
    ROLE_USER("user"),
    ROLE_MODERATOR("moder"),
    ROLE_ADMIN("admin");

    @Getter
    private final String name;

    ERole(final String name) {
        this.name = name;
    }
}
