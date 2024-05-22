import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;



public class MainFrame extends JFrame implements ActionListener {

    private ArrayList<OrderButton> orderButtons = new ArrayList<>(); // index van deze lijst moet overeenkomen met daadwerkelijke orders
    private Voorraad voorraad;

    private MagazijnOverzicht panel;
    private OrderPickVolgorde panel3;
    private static ArrayList<Byte> bytes = new ArrayList<>();
    private static int bytesToRead = -1;
    private String modus;

    int counter = 0; //TIJDELIJK, ALLEEN VOOR TESTEN MET SERIALCOMMUNICATIE IN JAVA


    MainFrame() throws IOException, InterruptedException {
        SerialPort serialPort = SerialPort.getCommPort("COM10"); // Replace "COM10" with your port
        serialPort.setComPortParameters(9600, 8, 1, 0); // Baud rate, Data bits, Stop bits, Parity
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0); // Non-blocking read

        if (serialPort.openPort()) {
            System.out.println("Port opened successfully.");
        } else {
            System.out.println("Failed to open the port.");
            return;
        }

        setTitle("HMI Magazijnrobot");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2)); //gridlayout voor aantal panels etc.
        setSize(1320,800);   //Grootte van hoofdscherm

        voorraad = new Voorraad();

        DB_connectie.updateMagazijn(voorraad); //update voorraad vanuit database
        DB_connectie.updateOrders(orderButtons); //update orders vanuit database

        panel = new MagazijnOverzicht(voorraad); //object met waardes meegeven naar panel indien nodig
        add(panel);

        JPanel jp = new JPanel(); //panel om knoppen in te doen, later voeg je dit aan de scrollbar toe
        try {
            jp.setLayout(new GridLayout(orderButtons.size(), 0));
        } catch (Exception e) {
            System.out.println("Foutcode: " + e.getMessage());;
        }

        jp.setPreferredSize(new Dimension(600, orderButtons.size() * 160)); //grootte van van de panel

        for (int i = 0; i < orderButtons.size(); i++) { //for loop om elke knop toe te voegen aan de panel
            jp.add(orderButtons.get(i));
            orderButtons.get(i).addActionListener(this);
        }

        JScrollPane scrollPane = new JScrollPane(jp); //maak scrollbar panel aan met het panel vol knoppen erin

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // deze 2 regels zorgen dat de scrollbars altijd te zien zijn
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25); //bepaald scrollsnelheid van de scrollbar
        add(scrollPane);


        panel3 = new OrderPickVolgorde(voorraad, panel);
        add(panel3);

        JScrollPane scrolpan3 = new JScrollPane(panel3);
        scrolpan3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrolpan3.getVerticalScrollBar().setUnitIncrement(25);
        scrolpan3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrolpan3);




        InvoegenVoorraadNoodstopPanel panel2 = new InvoegenVoorraadNoodstopPanel();

        add(panel2);
        setVisible(true);


        Scanner input = new Scanner(System.in);
        //hieronder alles over communicatie tussen arduino en JAVA
        serialPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
                if (serialPortEvent.getEventType() == SerialPort.LISTENING_EVENT_DATA_RECEIVED) {
                    byte[] newData = serialPortEvent.getReceivedData();

                    for (byte b : newData) {
                        if (bytesToRead == -1) {
                            switch (b){
                                case 'a': //robot coordinaten
                                    modus = "robotcoordinaten";
                                    bytesToRead = 2; //1ste byte is X coordinaat, 2de byte is Y coordinaat
                                    break;
                                case 'b': //bevestiging product gepakt
                                    modus = "productbevestiging";
                                    bytesToRead = 2;
                                    break;
                            }

//                            System.out.println(modus);
//                            bytesToRead = b & 0xFF; // Treat the first byte as an unsigned value
                            System.out.println("Expecting " + bytesToRead + " bytes.");
                        }
                        else {
                            bytes.add(b);
                        }

                        if (bytes.size() == bytesToRead) {
                            switch (modus){
                                case "robotcoordinaten":
                                    verwerkRobotCoords(bytes);
                                    break;
                                case "productbevestiging":
                                    System.out.println("Product op locatie: " + bytes + " is gepakt");
                                    break;
                            }
//                            processBytes();
                            bytes.clear();
                            bytesToRead = -1; // Reset for the next message
                            counter = 0;
                        }
                    }
                }
            }

            private void processBytes() {
                System.out.println("Processing bytes: " + bytes);
                // Convert bytes to string if needed
                byte[] byteArray = new byte[bytes.size()];
                for (int i = 0; i < bytes.size(); i++) {
                    byteArray[i] = bytes.get(i);
                }
            }

            private void verwerkRobotCoords(ArrayList<Byte> bytes){
                System.out.println("Robot zijn coordinaten zijn: " + bytes.get(0) + "," + bytes.get(1));
                int xCoord = bytes.get(0);
            }


        });

        while (true) {
            if(counter < 1 ){
                System.out.println("Stuur getal voor modus 101 is robotcord, 102 is product gepakt cord: ");
            }
            Integer blinks = input.nextInt();
            if (blinks == 0) break;
            Thread.sleep(50);
            serialPort.getOutputStream().write(blinks.byteValue());
            counter++;
        }

    }



    public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < orderButtons.size(); i++) {
                if (e.getSource() == orderButtons.get(i)){
                    OrderDialoog dialoog = new OrderDialoog(this, true, "Order: " + (orderButtons.get(i).getOrderID()), orderButtons.get(i).getCustomerID(), orderButtons.get(i).getOrderID(), voorraad);
                    if(dialoog.isUitvoerenOK()){

                        panel3.setHuidigeOrder(orderButtons.get(i).getOrderID());
                    }
                }
            }
            repaint();
    }
}


