package ru.ssau.reviewzor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.reviewzor.domain.ListRecord;

public interface ListRecordRepo extends JpaRepository<ListRecord, Long> {

}
