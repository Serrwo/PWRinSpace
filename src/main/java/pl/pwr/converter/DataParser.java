package pl.pwr.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.pwr.model.entity.DataRecord;

@Component
public class DataParser {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public DataRecord tryParse(String source) {
        try {
            return objectMapper.readValue(source, DataRecord.class);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
