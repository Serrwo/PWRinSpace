package pl.pwr.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class SerialPortDataWriter {

    public void send(Collection<Float> data, String portName) {

    }
}
