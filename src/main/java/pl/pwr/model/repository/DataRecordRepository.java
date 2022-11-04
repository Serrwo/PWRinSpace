package pl.pwr.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.model.entity.DataRecord;

public interface DataRecordRepository extends JpaRepository<DataRecord, Long> {
}
