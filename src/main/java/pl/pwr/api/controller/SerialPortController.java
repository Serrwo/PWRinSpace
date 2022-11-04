package pl.pwr.api.controller;

import com.fazecast.jSerialComm.SerialPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pwr.service.SerialPortService;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/serial-port")
public class SerialPortController {

    private final SerialPortService serialPortService;

    @PostMapping("/connect/{portName}")
    public void connect(@PathVariable String portName) {
        serialPortService.enablePort(portName);
    }


    @PostMapping("/disconnect/{portName}")
    public void disconnect(@PathVariable String portName) {
        serialPortService.disablePort(portName);
    }

    @GetMapping
    public ResponseEntity<Collection<String>> listPorts() {
        Collection<SerialPort> serialPorts = Arrays.stream(SerialPort.getCommPorts()).toList();
        return ResponseEntity.ok(
                serialPorts.stream().map(SerialPort::getDescriptivePortName).collect(Collectors.toList())
        );
    }

}
