package ru.ssau.reviewzor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ssau.reviewzor.domain.PlacesModel;
import ru.ssau.reviewzor.domain.User;
import ru.ssau.reviewzor.repo.PlacesModelRepo;
import ru.ssau.reviewzor.repo.UserRepo;
import ru.ssau.reviewzor.service.FileService;
import ru.ssau.reviewzor.utils.JwtUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/data/places")
@RequiredArgsConstructor
public class PlacesController {

    private final PlacesModelRepo repo;
    private final UserRepo userRepo;
    private final JwtUtils jwtUtils;
    private final FileService fileService;

    @GetMapping("")
    public List<PlacesModel> getAllRecords(@RequestHeader("Authorization") final String authToken) {
        final String token = authToken.substring("Bearer ".length());
        final String username = jwtUtils.getUsernameFromJwt(token);
        Optional<User> byUsername = userRepo.findByEmail(username);
        if (byUsername.isEmpty()) {
            return Collections.emptyList();
        }
        final Long id = byUsername.get().getId();
        return repo.findAllByUserId(id);
    }

    @PostMapping("/add")
    public void addRecord(@RequestHeader("Authorization") final String authToken, @RequestBody PlacesModel place) {
        final String token = authToken.substring("Bearer ".length());
        final String username = jwtUtils.getUsernameFromJwt(token);
        Optional<User> byUsername = userRepo.findByEmail(username);
        if (byUsername.isEmpty()) {
            throw new IllegalStateException("Error authority");
        }
        final Long id = byUsername.get().getId();
        if (place != null) {
            if(!fileService.isExistsByName(place.getFileName())) {
                throw new IllegalArgumentException("File is not exist");
            }
            place.setUserId(id);
            repo.saveAndFlush(place);
        }
    }
}
