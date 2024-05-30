import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import java.util.Scanner;


public class MainFrame extends JFrame implements ActionListener {

    private ArrayList<OrderButton> orderButtons = new ArrayList<>(); // index van deze lijst moet overeenkomen met daadwerkelijke orders
    private Voorraad voorraad;

    private MagazijnOverzicht magazijnOverzichtPanel;
    private OrderPickVolgorde panel3;
    private static ArrayList<Byte> bytes = new ArrayList<>();

    private ArrayList<Doos> finalDozenLijst = new ArrayList<>();
    private int huidigeDoosNummer = 0;
    private int huidigProductNummer = 0;
    private static int bytesToRead = -1;
    private String modus;
    private SerialPort serialPort;
    private Date HuidigeTijd;
    private JPanel orderButtonPanel;

    private boolean dropoff = false;



    MainFrame() throws IOException, InterruptedException {
        serialPort = SerialPort.getCommPort("dev/cu.usbmodem1422301"); // Replace "COM10" with your port
        serialPort.setComPortParameters(115200, 8, 1, 0); // Baud rate, Data bits, Stop bits, Parity
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0); // Non-blocking read

//        SerialPort serialPort = SerialPort.getCommPort("dev/cu.usbmodem1412401"); // Replace "COM10" with your port
//        serialPort.setComPortParameters(9600, 8, 1, 0); // Baud rate, Data bits, Stop bits, Parity
//        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0); // Non-blocking read

        if (serialPort.openPort()) {
            System.out.println("Port opened successfully.");
        } else {
            System.out.println("Failed to open the port.");
            JOptionPane.showMessageDialog(null, "Geen port gedetecteerd, sluit een port aan");
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

        orderButtonPanel = new JPanel(); //panel om knoppen in te doen, later voeg je dit aan de scrollbar toe
        try {
            orderButtonPanel.setLayout(new GridLayout(orderButtons.size(), 0));
        } catch (Exception e) {
            System.out.println("Foutcode: " + e.getMessage());
        }

        orderButtonPanel.setPreferredSize(new Dimension(600, orderButtons.size() * 160)); //grootte van van de panel

        for (OrderButton orderButton : orderButtons) { //for loop om elke knop toe te voegen aan de panel
            orderButtonPanel.add(orderButton);
            orderButton.addActionListener(this);
        }

        JScrollPane scrollPane = new JScrollPane(orderButtonPanel); //maak scrollbar panel aan met het panel vol knoppen erin

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
                                            if(!dropoff){
                                                huidigProductNummer++;
                                            }
                                            else{
                                                dropoff = false;
                                            }

                                        }
                                        else{
                                            huidigeDoosNummer++;
                                            huidigProductNummer = 0;
                                            try {
                                                goToDropoff();
                                                dropoff = true;
                                                System.out.println("Doos is klaar");
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                        if(huidigeDoosNummer == finalDozenLijst.size()){
                                            System.out.println("Order Compleet!");
                                            huidigeDoosNummer = 0;

                                            finalDozenLijst.clear();
                                            break loop2;
                                        }

                                        if (dropoff == false){
                                            stuurProduct();
                                        }



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



                                    updateRepaint();
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
        revalidate();
        repaint();
    }

    public void RemoveButton(int orderID){
        for (int i = 0; i < orderButtons.size(); i++) {
            if(orderButtons.get(i).getOrderID() == orderID){
                orderButtonPanel.remove(orderButtons.get(i));
                System.out.println("Button verwijderd");
                updateRepaint();

            }
        }
    }
    public void AddButton(int orderID){
        for (int i = 0; i < orderButtons.size(); i++) {
            if(orderButtons.get(i).getOrderID() == orderID){
                orderButtonPanel.add(orderButtons.get(i));
                System.out.println("Button verwijderd");
                updateRepaint();

            }
        }
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
        try {
            ArrayList<Integer> coords = voorraad.getCoordinaten(finalDozenLijst.get(huidigeDoosNummer).getInhoud().get(huidigProductNummer).getArtikelID());
            serialPort.getOutputStream().write(coords.get(0).byteValue());
            serialPort.getOutputStream().write(coords.get(1).byteValue());
            System.out.println("Gestuurde coordinaten zijn: " + coords.get(0) + "-" + coords.get(1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch(ArrayIndexOutOfBoundsException e2){
            System.out.println("Sorry: " + e2);
        }
    }
    public void actionPerformed(ActionEvent e) {
            if(magazijnOverzichtPanel.getRobotstatus() != 3){
                for (OrderButton orderButton : orderButtons) {
                    if (e.getSource() == orderButton) {
                        OrderDialoog dialoog = new OrderDialoog(this, true, "Order: " + (orderButton.getOrderID()), orderButton.getCustomerID(), orderButton.getOrderID(), voorraad, this);
                        if (dialoog.isUitvoerenOK()) {
                            finalDozenLijst = panel3.setHuidigeOrder(orderButton.getOrderID());

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
        revalidate();
        repaint();
    }
}




