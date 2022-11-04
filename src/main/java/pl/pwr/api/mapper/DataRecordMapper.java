package pl.pwr.api.mapper;

import org.springframework.stereotype.Component;
import pl.pwr.model.entity.DataRecord;
import pl.pwr.service.Mapper;
import pl.pwr.api.dto.DataRecordDto;

@Component
public class DataRecordMapper implements Mapper<DataRecord, DataRecordDto> {

    @Override
    public DataRecordDto map(DataRecord entity) {
        DataRecordDto dto = new DataRecordDto();
        dto.setId(entity.getId());
        dto.setTime(entity.getTime());
        dto.setRecruit(entity.getRecruit());
        dto.setPort(entity.getPort());
        dto.setData(entity.getData());
        return dto;
    }
}
