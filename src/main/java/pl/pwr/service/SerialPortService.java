package pl.pwr.service;

import com.fazecast.jSerialComm.SerialPort;
import pl.pwr.api.dto.DataRecordDto;

import java.util.Collection;

public interface SerialPortService {

    void enablePort(String portName);
    void disablePort(String portName);
    SerialPort getPort(String portName);
    void send(DataRecordDto dataRecordDto, String portName);
}
