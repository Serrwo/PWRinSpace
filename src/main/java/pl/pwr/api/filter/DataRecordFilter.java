package pl.pwr.api.filter;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Data
public class DataRecordFilter {

    private LocalDateTime timeFrom;

    private LocalDateTime timeTo;

    private Long limit;
}
