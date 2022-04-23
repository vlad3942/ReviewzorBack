package ru.ssau.reviewzor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.ssau.reviewzor.domain.User;
import ru.ssau.reviewzor.pojo.MessageResponse;
import ru.ssau.reviewzor.pojo.UserUpdateRequest;
import ru.ssau.reviewzor.repo.UserRepo;
import ru.ssau.reviewzor.utils.JwtUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final JwtUtils jwtUtils;

    public ResponseEntity<?> updateUserData(final String token, final UserUpdateRequest request) {
        final String auth = token.substring("Bearer".length());
        final String username = jwtUtils.getUsernameFromJwt(auth);
        Optional<User> byEmail = userRepo.findByEmail(username);
        if (byEmail.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not found."));
        }
        final User user = byEmail.get();
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getSurname() != null) {
            user.setSurname(request.getSurname());
        }
        userRepo.saveAndFlush(user);
        return ResponseEntity.ok(new MessageResponse("Update was done correctly!"));
    }

    public ResponseEntity<?> getUserByToken(final String token) {
        final String auth = token.substring("Bearer".length());
        final String username = jwtUtils.getUsernameFromJwt(auth);
        Optional<User> byEmail = userRepo.findByEmail(username);
        if (byEmail.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not found."));
        }
        final User user = byEmail.get();
        return ResponseEntity.ok(user);
    }

    public Long getUserIdByBearerToken(final String authToken) {
        final String token = authToken.substring("Bearer ".length());
        final String username = jwtUtils.getUsernameFromJwt(token);
        Optional<User> byUsername = userRepo.findByEmail(username);
        if (byUsername.isEmpty()) {
            throw new IllegalStateException("Error authority");
        }
        return byUsername.get().getId();
    }
}
