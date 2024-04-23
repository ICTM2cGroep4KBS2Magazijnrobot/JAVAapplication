import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame implements ActionListener {

    private ArrayList<OrderButton> orderButtons = new ArrayList<>(); // index van deze lijst moet overeenkomen met daadwerkelijke orders
    private Voorraad voorraad;
    private JButton testknop;
    MainFrame() {
        setTitle("Magazijnrobot");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2)); //gridlayout voor aantal panels etc.
        setSize(1320,800);   //Grootte van hoofdscherm

        voorraad = new Voorraad();

        DB_connectie.updateMagazijn(voorraad);
        DB_connectie.updateOrders(orderButtons);



        MagazijnOverzicht panel = new MagazijnOverzicht(voorraad); //object met waardes meegeven naar panel indien nodig
        add(panel);


//        orderButtons.add(new JButton("Order 1"));     //test orders
//        orderButtons.add(new JButton("Order 2"));
//        orderButtons.add(new JButton("Order 3"));
//        orderButtons.add(new JButton("Order Teun"));

        JPanel jp = new JPanel(); //panel om knoppen in te doen, later voeg je dit aan de scrollbar toe
        jp.setLayout(new GridLayout(15, 0)); //grid layout voor de knoppen: lange rij van boven naar beneden.
        jp.setPreferredSize(new Dimension(600, 800)); //grootte van van de panel

        for (int i = 0; i < orderButtons.size(); i++) { //for loop om elke knop toe te voegen aan de panel
            jp.add(orderButtons.get(i));
            orderButtons.get(i).addActionListener(this);
        }

        JScrollPane scrollPane = new JScrollPane(jp); //maak scrollbar panel aan met het panel vol knoppen erin

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // deze 2 regels zorgen dat de scrollbars altijd te zien zijn
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25); //bepaald scrollsnelheid van de scrollbar
        add(scrollPane);

        OrderPickVolgorde panel3 = new OrderPickVolgorde();
        add(panel3);

//        testknop = new JButton("Test"); //knop om verwijderen van voorraad te testen
//        add(testknop);
//        testknop.addActionListener(this);

        InvoegenVoorraadNoodstopPanel panel2 = new InvoegenVoorraadNoodstopPanel();

        add(panel2);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < orderButtons.size(); i++) {
                if (e.getSource() == orderButtons.get(i)){
                    OrderDialoog dialoog = new OrderDialoog(this, true, "Order: " + (orderButtons.get(i).getOrderID()), orderButtons.get(i).getCustomerID(), orderButtons.get(i).getOrderID());

                }
            }
            if (e.getSource() == testknop){ // deze is alleen voor het testen van verwijderen van product
                voorraad.removeRijElement(0,3);
                System.out.println("knop ingedrukt");
            }
            repaint();
    }
}
