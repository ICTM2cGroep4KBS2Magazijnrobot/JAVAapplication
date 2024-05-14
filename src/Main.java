import com.fazecast.jSerialComm.*;
import com.fazecast.jSerialComm.SerialPortInvalidPortException;

import java.util.Timer;

import java.sql.*;
import java.util.ArrayList;

public class Main {



    public static void main(String[] args) throws SQLException {

        long timeStart = System.currentTimeMillis(); //start timer for connection
        try {
            var sp = SerialPort.getCommPort("dev/cu.usbmodem1412401"); //set port, usually COM3 or COM4

            sp.setComPortParameters(115200, Byte.SIZE, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY); //Set the parameters
            sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);

            var hasOpened = sp.openPort(); //check for if the port is free
            if (!hasOpened) {
                throw new IllegalStateException("Failed to open serial port"); //if unavailable throw an exception

            }

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                sp.closePort();
            }));

            var timer = new Timer();
            var timedSchedule = new TimerScheduleHandler(timeStart);
            sp.addDataListener(timedSchedule);
            System.out.println("Listen: " + timedSchedule.getListeningEvents());
            //timer.schedule(timedSchedule, 0, 1000);

        } catch(SerialPortInvalidPortException ex) {

            System.out.println("Com port unavailable, check if you set it up correctly" +
                    "It should be set to the same Com port as your Arduino");



        }

        MainFrame scherm = new MainFrame();

    }

}
