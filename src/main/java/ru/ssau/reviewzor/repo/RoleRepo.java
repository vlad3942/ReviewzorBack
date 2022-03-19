package ru.ssau.reviewzor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.reviewzor.domain.ERole;
import ru.ssau.reviewzor.domain.Role;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
