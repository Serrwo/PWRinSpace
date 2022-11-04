package pl.pwr.service;

import pl.pwr.model.entity.DataRecord;

import java.time.LocalDateTime;
import java.util.Collection;

public interface DataService {
    Collection<DataRecord> getAll();
    DataRecord getById(Long id);
    Collection<DataRecord> filter(LocalDateTime timeFrom, LocalDateTime timeTo, Long limit);
}
