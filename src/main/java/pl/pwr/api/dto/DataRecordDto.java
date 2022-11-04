package pl.pwr.api.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DataRecordDto {

    private Long id;

    private LocalDateTime time;

    private String port;

    private String recruit;

    private Float[] data;
}
