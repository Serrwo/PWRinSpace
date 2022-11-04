package pl.pwr.service;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.pwr.converter.DataParser;
import pl.pwr.exception.ParseException;
import pl.pwr.model.entity.DataRecord;
import pl.pwr.model.repository.DataRecordRepository;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class SerialPortDataReader implements SerialPortDataListener {

    private final DataParser dataParser;

    private final DataRecordRepository dataRecordRepository;

    private SerialPort serialPort;

    public SerialPortDataReader(DataParser dataParser, DataRecordRepository dataRecordRepository, SerialPort serialPort) {
        this.dataParser = dataParser;
        this.dataRecordRepository = dataRecordRepository;
        this.serialPort = serialPort;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        byte[] rawData = event.getReceivedData();
        String portName = event.getSerialPort().getDescriptivePortName();
        if(rawData.length <= 0) {
            log.warn("Empty package received at port: {}.", portName);
            return;
        }
        String rawString = new String(rawData, StandardCharsets.UTF_8);
        DataRecord dataRecord = new DataRecord();
        log.info("Received {} bytes of data from port: {}.", rawData.length, portName);
        try {
            dataRecord = dataParser.tryParse(rawString);
        } catch (IllegalArgumentException e) {
            try {
                parseRawData(rawString, dataRecord);
            } catch (ParseException rawParseEx) {
                log.error("Received package at port: {}, but couldn't parse it.", portName);
                return;
            }
        }
        dataRecord.setPort(portName);
        dataRecord.setTime(LocalDateTime.now());
        dataRecordRepository.saveAndFlush(dataRecord);

        Float[] parsedData = dataRecord.getData();
        for(int i = 0; i < parsedData.length; i++) {
            log.info("Value {}: {}", i, parsedData[i]);
        }
    }

    protected void parseRawData(String rawData, DataRecord dataRecord) throws ParseException {
        if(rawData == null) {
            return;
        }
        List<Float> values = new ArrayList<>();
        String[] strings = rawData.split(";");
        String recruit = strings[0];
        for(int i = 1; i < strings.length; i++) {
            try {
                values.add(Float.parseFloat(strings[i]));
            } catch (NumberFormatException e) {
                throw new ParseException(e.toString(), e);
            }
        }
        Float[] data = values.toArray((Float[]) Array.newInstance(Float.class, 0));
        dataRecord.setData(data);
        dataRecord.setRecruit(recruit);
    }

    public void openPort() {
        serialPort.openPort();
    }

    public void closePort() {
        serialPort.closePort();
    }
}
