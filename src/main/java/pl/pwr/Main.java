package pl.pwr;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.nio.charset.StandardCharsets;

@SpringBootApplication(scanBasePackages = "pl.pwr")
@EntityScan(basePackages = "pl.pwr")
@EnableJpaRepositories(basePackages = "pl.pwr")
public class Main {
    public static void main(String[] args) {

//        SerialPort comPort = SerialPort.getCommPorts()[0];
//        comPort.openPort();
//        comPort.addDataListener(new SerialPortDataListener() {
//
//            @Override
//            public int getListeningEvents() {
//                System.out.println("Listener init");
//                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
//            }
//
//            @Override
//            public void serialEvent(SerialPortEvent event)
//            {
//                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
//                    return;
//                byte[] newData = new byte[comPort.bytesAvailable()];
//                int numRead = comPort.readBytes(newData, newData.length);
//                System.out.println("Read " + numRead + " bytes.");
//            }
//        });

        SpringApplication.run(Main.class, args);
    }
}