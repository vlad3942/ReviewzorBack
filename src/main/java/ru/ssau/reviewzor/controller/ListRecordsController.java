package ru.ssau.reviewzor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ssau.reviewzor.domain.ListRecord;
import ru.ssau.reviewzor.repo.ListRecordRepo;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/records")
public class ListRecordsController {
    private final ListRecordRepo repo;

    @GetMapping("")
    public List<ListRecord> getAllRecords() {
        return repo.findAll();
    }

    @PutMapping("/add")
    public void putNewRecord(@RequestBody ListRecord record) {
        repo.save(record);
    }
}
