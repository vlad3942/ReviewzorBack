package ru.ssau.reviewzor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ssau.reviewzor.domain.PlacesModel;
import ru.ssau.reviewzor.pojo.MessageResponse;
import ru.ssau.reviewzor.repo.PlacesModelRepo;
import ru.ssau.reviewzor.service.FileService;
import ru.ssau.reviewzor.service.UserService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/data/places")
@RequiredArgsConstructor
public class PlacesController {

    private final PlacesModelRepo repo;
    private final FileService fileService;
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getAllRecords(@RequestHeader("Authorization") final String authToken) {
        final Long id = userService.getUserIdByBearerToken(authToken);
        List<PlacesModel> forDeleting = new ArrayList<>();
        List<PlacesModel> places = repo.findAllByUserId(id);
        places.forEach(place -> {
            if (Boolean.TRUE.equals(place.getIsDeleted())) {
                forDeleting.add(place);
            }
        });
        repo.deleteAll(forDeleting);
        return ResponseEntity.ok(places);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRecord(@RequestHeader("Authorization") final String authToken, @RequestBody final PlacesModel place) {
        final Long id = userService.getUserIdByBearerToken(authToken);
        if (place != null) {
            if (place.getId() == null) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: User id was not set."));
            }
            if(!fileService.isExistsByName(place.getFileName())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: File is not exist."));
            }
            place.setLastModified(Timestamp.valueOf(LocalDateTime.now()));
            place.setUserId(id);
            repo.saveAndFlush(place);
            return ResponseEntity.ok(new MessageResponse("Place added!"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Place is null or empty"));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteRecord(@RequestHeader("Authorization") final String authToken, @PathVariable("id") final String id) {
        final Optional<PlacesModel> placeOptional = repo.findById(id);
        if (placeOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Record is not found."));
        }
        final Long userId = userService.getUserIdByBearerToken(authToken);
        final PlacesModel place = placeOptional.get();
        if (!place.getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new MessageResponse("Error: Deleting record not allowed for current user."));
        }
        place.setIsDeleted(true);
        place.setLastModified(Timestamp.valueOf(LocalDateTime.now()));
        repo.saveAndFlush(place);
        return ResponseEntity.ok(new MessageResponse("Record was deleted."));
    }
}
