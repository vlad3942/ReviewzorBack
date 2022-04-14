package ru.ssau.reviewzor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.reviewzor.domain.PlacesModel;

import java.util.List;

public interface PlacesModelRepo extends JpaRepository<PlacesModel, String> {
    List<PlacesModel> findAllByUserId(Long id);
}
