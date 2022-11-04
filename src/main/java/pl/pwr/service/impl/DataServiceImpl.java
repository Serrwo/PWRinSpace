package pl.pwr.service.impl;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pwr.model.entity.DataRecord;
import pl.pwr.model.entity.QDataRecord;
import pl.pwr.model.repository.DataRecordRepository;
import pl.pwr.service.DataService;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {

    private final EntityManager entityManager;

    @Override
    public Collection<DataRecord> getAll() {
        QDataRecord path = QDataRecord.dataRecord;
        return new JPAQuery<>(entityManager).select(path).from(path).fetch();
    }

    @Override
    public DataRecord getById(Long id) {
        QDataRecord path = QDataRecord.dataRecord;
        return new JPAQuery<>(entityManager).from(path).select(path).where(path.id.eq(id)).fetchOne();
    }

    @Override
    public Collection<DataRecord> filter(LocalDateTime timeFrom, LocalDateTime timeTo, Long limit) {

        timeFrom = timeFrom != null ? timeFrom : LocalDateTime.parse("2020-01-01T00:00:00.000");
        timeTo = timeTo != null ? timeTo : LocalDateTime.now();
        limit = limit != null ? limit : Long.MAX_VALUE;

        QDataRecord path = QDataRecord.dataRecord;
        return new JPAQuery<>(entityManager).from(path).select(path)
                .where(ExpressionUtils.allOf (
                    path.time.after(timeFrom),
                    path.time.before(timeTo)
        )).limit(limit).fetch();
    }
}
