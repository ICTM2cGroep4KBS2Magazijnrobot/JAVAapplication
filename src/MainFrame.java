import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.util.Scanner;

import static javax.swing.SwingUtilities.invokeLater;


public class MainFrame extends JFrame implements ActionListener {

    private ArrayList<OrderButton> orderButtons = new ArrayList<>(); // index van deze lijst moet overeenkomen met daadwerkelijke orders
    private Voorraad voorraad;

    private MagazijnOverzicht magazijnOverzichtPanel;
    private OrderPickVolgorde panel3;
    private static ArrayList<Byte> bytes = new ArrayList<>();

    private ArrayList<Doos> finalDozenLijst = new ArrayList<>();
    private boolean getFinalDozenlijst = true;
    private int huidigeDoosNummer = 0;
    private int huidigProductNummer = 0;
    private static int bytesToRead = -1;
    private String modus;
    private SerialPort serialPort;
    private Date HuidigeTijd;




    MainFrame() throws IOException, InterruptedException {
        serialPort = SerialPort.getCommPort("/dev/tty.usbmodem1442301"); // Replace "COM10" with your port
        serialPort.setComPortParameters(115200, 8, 1, 0); // Baud rate, Data bits, Stop bits, Parity
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0); // Non-blocking read

//        SerialPort serialPort = SerialPort.getCommPort("dev/cu.usbmodem1412401"); // Replace "COM10" with your port
//        serialPort.setComPortParameters(9600, 8, 1, 0); // Baud rate, Data bits, Stop bits, Parity
//        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0); // Non-blocking read

        if (serialPort.openPort()) {
            System.out.println("Port opened successfully.");
        } else {
            System.out.println("Failed to open the port.");
            //return;
        }

        setTitle("HMI Magazijnrobot");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2)); //gridlayout voor aantal panels etc.
        setSize(1320,800);   //Grootte van hoofdscherm

        voorraad = new Voorraad();

        DB_connectie.updateMagazijn(voorraad); //update voorraad vanuit database
        DB_connectie.updateOrders(orderButtons); //update orders vanuit database

        magazijnOverzichtPanel = new MagazijnOverzicht(voorraad); //object met waardes meegeven naar panel indien nodig
        add(magazijnOverzichtPanel);

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


        panel3 = new OrderPickVolgorde(voorraad, magazijnOverzichtPanel);
        add(panel3);

        JScrollPane scrolpan3 = new JScrollPane(panel3);
        scrolpan3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrolpan3.getVerticalScrollBar().setUnitIncrement(25);
        scrolpan3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrolpan3);







        Scanner input = new Scanner(System.in);
        //hieronder alles over communicatie tussen arduino en JAVA
        serialPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            public void stuurWaarde(int waarde){
                try {
                    Integer getal = 2;
                    serialPort.getOutputStream().write(getal.byteValue());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
                if (serialPortEvent.getEventType() == SerialPort.LISTENING_EVENT_DATA_RECEIVED) {
                    byte[] newData = serialPortEvent.getReceivedData();

                    loop: for (byte b : newData) {
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
                                case 'c':
                                    modus = "robotstatus";
                                    bytesToRead = 1;
                                    break;
                                case 'd':
                                    modus = "eindeorder";
                                    bytesToRead = 1;
                                    break;
                                case 'e':
                                    modus = "stuurpicking";
                                    bytesToRead = 1;
                                    break;
                                default:
                                    break loop;
                            }

//                            System.out.println("Expecting " + bytesToRead + " bytes.");
                        }
                        else {
                            bytes.add(b);
                        }

                        if (bytes.size() == bytesToRead) {
                            switch (modus){
                                case "robotcoordinaten":
                                    int Xcord = (bytes.get(0) & 0xff);
                                    int Ycord = (bytes.get(1) & 0xff);
                                    long nieuweX = mapBereken(Xcord, 0, 255, 0, 600);
                                    long nieuweY = mapBereken(Ycord, 0, 255, 0, 351);
                                    long nieuwenieweY = mapBereken(nieuweY, 0, 351, 351, 0);

                                    int sendX = (int)nieuweX;
                                    int sendY = (int)nieuwenieweY;


                                    magazijnOverzichtPanel.setRobotCords(sendX, sendY);
                                    repaint();
                                    break;
                                case "productbevestiging":
                                    loop2: if(huidigeDoosNummer < finalDozenLijst.size()){
//                                        finalDozenLijst.get(huidigeDoosNummer).getInhoud().remove(huidigProductNummer);
//                                        repaint();
                                        System.out.println("Bevestiging ontvangen!");
                                        if(huidigProductNummer < finalDozenLijst.get(huidigeDoosNummer).getInhoud().size() -1){
                                            huidigProductNummer++;
                                        }
                                        else{
                                            huidigeDoosNummer++;
                                            huidigProductNummer = 0;
                                            try {
                                                goToDropoff();
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                        if(huidigeDoosNummer == finalDozenLijst.size()){
                                            System.out.println("Order Compleet!");
                                            huidigeDoosNummer = 0;

                                            break loop2;
                                        }

                                        stuurProduct();
                                        //hier nog extra check om huidig doos terug te zetten?
                                    }
//                                    else{
//                                        System.out.println("Order Compleet!");
//                                    }

                                    break;
                                case "robotstatus":
                                    magazijnOverzichtPanel.setRobotstatus(bytes.getFirst());
                                    System.out.println("Status is nu: " + bytes.getFirst());
                                    repaint();
                                    break;
                                case "eindeorder":
                                    long millis=System.currentTimeMillis();
                                    HuidigeTijd = new Date(millis);
                                    DB_connectie.OrderPickCompleted(panel3.getHuidigeOrder(), HuidigeTijd );
                                    System.out.println("Order : "+ panel3.getHuidigeOrder()+ " Is compleet");
                                    //scrolpan3.repaint();

                                    for (int i = 0; i < orderButtons.size(); i++) {
                                        if(orderButtons.get(i).getOrderID() == panel3.getHuidigeOrder()){
                                            orderButtons.remove(i);


                                        }
                                    }
                                    panel3.setHuidigeOrder(0);
                                    scrollPane.updateUI();
                                    revalidate();
                                    repaint();
                                    break;
                                case "stuurpicking":
                                    System.out.println("Begin pakken!");
                                    stuurProduct();
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected value: " + modus);
                            }

                            bytes.clear();
                            bytesToRead = -1; // Reset for the next message

                        }
                    }
                }
            }






        }); // hierin alle code voor seriele data versturing

        InvoegenVoorraadNoodstopPanel panel2 = new InvoegenVoorraadNoodstopPanel(serialPort, voorraad, this, magazijnOverzichtPanel);

        add(panel2);
        setVisible(true);

        //hieronder handmatige testterminal
        while (true) {
            Integer blinks = input.nextInt();
            if (blinks == 99) break;
            Thread.sleep(50);
            serialPort.getOutputStream().write(blinks.byteValue());
        }

    }

    // om voorraad te updaten en repaint doen.
    public void updateRepaint() {

        for (int i = 0; i < orderButtons.size(); i++) {
            if(orderButtons.get(i).getOrderID() == panel3.getHuidigeOrder()){
                orderButtons.remove(i);


            }
        }
        revalidate();
        repaint();
    }

    public long mapBereken(long x, long in_min, long in_max, long out_min, long out_max){
            return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;

    }

    private void startPicking() throws IOException {
        Integer getal = 9; //commando naar Arduino om startprocedure terug te sturen
        serialPort.getOutputStream().write(getal.byteValue());
    }
    private void goToDropoff() throws IOException {
        Integer getal = 9; //commando naar Arduino om startprocedure terug te sturen
        serialPort.getOutputStream().write(getal.byteValue());
        serialPort.getOutputStream().write(getal.byteValue());
    }
    private void stuurProduct(){
        ArrayList<Integer> coords = voorraad.getCoordinaten(finalDozenLijst.get(huidigeDoosNummer).getInhoud().get(huidigProductNummer).getArtikelID());
        try {
            serialPort.getOutputStream().write(coords.get(0).byteValue());
            serialPort.getOutputStream().write(coords.get(1).byteValue());
            System.out.println("Gestuurde coordinaten zijn: " + coords.get(0) + "-" + coords.get(1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void actionPerformed(ActionEvent e) {
            if(magazijnOverzichtPanel.getRobotstatus() != 3){
                for (int i = 0; i < orderButtons.size(); i++) {
                    if (e.getSource() == orderButtons.get(i)){
                        OrderDialoog dialoog = new OrderDialoog(this, true, "Order: " + (orderButtons.get(i).getOrderID()), orderButtons.get(i).getCustomerID(), orderButtons.get(i).getOrderID(), voorraad, this);
                        if(dialoog.isUitvoerenOK()){
                            finalDozenLijst = panel3.setHuidigeOrder(orderButtons.get(i).getOrderID());

                            stuurProduct();
//                            try {
//                                startPicking();
//                            } catch (IOException ex) {
//                                System.out.println("Error: " + ex);
//                                throw new RuntimeException(ex);
//                            }


                        }
                    }
                }
            }
        // Now schedule a repaint for the entire JFrame
        repaint();
    }
}




