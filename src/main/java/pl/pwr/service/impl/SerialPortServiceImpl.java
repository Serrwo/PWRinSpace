package pl.pwr.service.impl;

import com.fazecast.jSerialComm.SerialPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.pwr.api.dto.DataRecordDto;
import pl.pwr.converter.DataParser;
import pl.pwr.model.repository.DataRecordRepository;
import pl.pwr.service.SerialPortDataReader;
import pl.pwr.service.SerialPortService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SerialPortServiceImpl implements SerialPortService {

    private final DataParser dataParser;
    private final DataRecordRepository dataRecordRepository;
    private Map<String, SerialPortDataReader> listeners = new HashMap<>();

    @Override
    public void enablePort(String portName) {
        if(listeners.containsKey(portName)) {
            throw new IllegalArgumentException(String.format("Port %s already connected.", portName));
        }
        try {
            SerialPort serialPort = getPort(portName);
            serialPort.openPort();
            SerialPortDataReader reader = new SerialPortDataReader(dataParser, dataRecordRepository, serialPort);
            serialPort.addDataListener(reader);
            listeners.put(portName, reader);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(String.format("Serial port with name '%s' not found.", portName));
        }

    }

    @Override
    public void disablePort(String portName) {
        SerialPortDataReader reader = listeners.get(portName);
        if(reader == null) {
            throw new IllegalArgumentException(String.format("No connected port with name '%s'.", portName));
        }
        reader.closePort();
        listeners.remove(portName);
    }

    public void send(DataRecordDto dataRecordDto, String portName) {
        try {
            SerialPort serialPort = getPort(portName);
            serialPort.openPort();
            Float[] data = dataRecordDto.getData();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(dataRecordDto.getRecruit()).append(";");
            for(int i = 0; i < data.length; i++) {
                Float value = data[i];
                stringBuilder.append(value.toString());
                if(i + 1 < data.length) {
                    stringBuilder.append(";");
                }
            }
            byte[] byteArray = stringBuilder.toString().getBytes();
            try(OutputStream outputStream = serialPort.getOutputStream()) {
                outputStream.write(byteArray);
            } catch (IOException ex) {
                log.error("Could create output stream from serial port '{}'", portName);
                throw new IOException(ex);
            } finally {
                serialPort.closePort();
            }
        } catch (NoSuchElementException | IOException e) {
            throw new IllegalArgumentException(String.format("Couldn't send data to port '%s'.", portName));
        }
    }

    @Override
    public SerialPort getPort(String portName) throws NoSuchElementException {
        return Arrays.stream(SerialPort.getCommPorts())
                .toList().stream()
                .filter(p -> p.getDescriptivePortName().equals(portName)).findFirst().get();
    }
}
