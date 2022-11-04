package pl.pwr.service;

import com.fazecast.jSerialComm.SerialPort;

public interface SerialPortService {

    void enablePort(String portName);
    void disablePort(String portName);
    SerialPort getPort(String portName);
}
