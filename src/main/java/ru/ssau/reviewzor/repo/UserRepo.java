package ru.ssau.reviewzor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.reviewzor.domain.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
}
