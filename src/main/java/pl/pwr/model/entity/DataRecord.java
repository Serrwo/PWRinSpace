package pl.pwr.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import pl.pwr.converter.FloatDataConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
public class DataRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @CreationTimestamp
    private LocalDateTime time;

    @Column(length = 50)
    private String port;

    @Column
    private String recruit;

    @Column
    @Convert(converter = FloatDataConverter.class)
    private Float[] data;
}
