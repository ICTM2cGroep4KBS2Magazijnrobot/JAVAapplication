import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.util.TimerTask;

public class TimerScheduleHandler extends TimerTask implements SerialPortDataListener {
    private final long timeStart;

    // constructor
    public TimerScheduleHandler(long timeStart) {
    this.timeStart = timeStart;

    }

    //override run method in TimerTask
    @Override
    public void run(){
        System.out.println("Time elapsed: " + (System.currentTimeMillis() - this.timeStart) +" milliseconds");

    }

    @Override
    public int getListeningEvents(){
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent){
        if (serialPortEvent.getEventType() == SerialPort.LISTENING_EVENT_DATA_RECEIVED){
            System.out.println("Yes! Arduino leeft, knop gedrukt");
        }

    }
}
