package pl.pwr.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pwr.api.filter.DataRecordFilter;
import pl.pwr.api.mapper.DataRecordMapper;
import pl.pwr.service.DataService;
import pl.pwr.api.dto.DataRecordDto;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/data")
@RestController
public class DataController implements InitializingBean {

    private final DataService dataService;

    private final DataRecordMapper dataRecordMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    @GetMapping
    public ResponseEntity<Collection<DataRecordDto>> getByTime(@RequestBody DataRecordFilter filter) {
        return ResponseEntity.ok(
                dataService.filter(filter.getTimeFrom(), filter.getTimeTo(), filter.getLimit())
                        .stream()
                        .map(dataRecordMapper::map)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/list")
    public ResponseEntity<Collection<DataRecordDto>> list() {
        return ResponseEntity.ok(
                dataService.getAll()
                        .stream()
                        .map(dataRecordMapper::map)
                        .collect(Collectors.toList())
        );
    }
}
