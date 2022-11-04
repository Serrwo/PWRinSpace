package pl.pwr.service.impl;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortInvalidPortException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pwr.converter.DataParser;
import pl.pwr.model.repository.DataRecordRepository;
import pl.pwr.service.SerialPortDataReader;
import pl.pwr.service.SerialPortService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
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

    @Override
    public SerialPort getPort(String portName) throws NoSuchElementException {
        return Arrays.stream(SerialPort.getCommPorts())
                .toList().stream()
                .filter(p -> p.getDescriptivePortName().equals(portName)).findFirst().get();
    }
}
