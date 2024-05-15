import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainFrame extends JFrame implements ActionListener {

    private ArrayList<OrderButton> orderButtons = new ArrayList<>(); // index van deze lijst moet overeenkomen met daadwerkelijke orders
    private Voorraad voorraad;

    private MagazijnOverzicht panel;
    private OrderPickVolgorde panel3;


    MainFrame() {
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
        add(scrolpan3);




        InvoegenVoorraadNoodstopPanel panel2 = new InvoegenVoorraadNoodstopPanel();

        add(panel2);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

            for (int i = 0; i < orderButtons.size(); i++) {
                if (e.getSource() == orderButtons.get(i)){
                    OrderDialoog dialoog = new OrderDialoog(this, true, "Order: " + (orderButtons.get(i).getOrderID()), orderButtons.get(i).getCustomerID(), orderButtons.get(i).getOrderID(), voorraad);
                    if(dialoog.isUitvoerenOK()){

                        panel3.setHuidigeOrder(orderButtons.get(i).getOrderID());
//                        timeDelay(3);
                        panel3.setDoosnummer(1);


//                       repaint();
//                        panel3.setDoosnummer(1);
//                        int counter = 0;
//                        while(counter < 3) {
////                            panel3.setHuidigeOrder(orderButtons.get(i).getOrderID());
////                            panel.setProductInDoosNummer(counter);
//                            panel3.setDoosnummer(counter);
//                            System.out.println("Doosnummer is nu: " + counter + "\n");
//                            repaint();
//                            try{
//                                Thread.sleep(1000);
//                            }
//                            catch(InterruptedException ex) {
//                                System.out.println("Sleep niet gelukt");
//                            }
//                            counter++;
//                        }
//                        panel3.setHuidigeOrder(orderButtons.get(i).getOrderID());
//                        repaint();
//                        panel3.updateTSP();
//                        panel.setTSP_DozenLijst(panel3.getTSP_Dozenlijst());

                    }
                }
            }
            repaint();
    }

    public void timeDelay(long t){
        try{
            Thread.sleep(t);
            System.out.println("Gelukt");
        }
        catch (InterruptedException e){

        }
    }
}


