import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame implements ActionListener {

    private ArrayList<JButton> orders = new ArrayList<>();
    private ArrayList<ArrayList<Product>> geheleVoorraad = new ArrayList<>(5);
    private ArrayList<Product> rij1 = new ArrayList<>(5);

    MainFrame(){
        setTitle("Magazijnrobot");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setLayout(new FlowLayout(FlowLayout.LEFT));
        setLayout(new GridLayout(2, 2)); //gridlayout voor aantal panels etc.
        setSize(1320,800);   //Grootte van hoofdscherm

        Voorraad voorraad = new Voorraad();
        voorraad.setRijElement(0, 1, new Product("rood", 5));
        voorraad.setRijElement(2, 0, new Product("groen", 5));

        System.out.println(voorraad.getRijElement(0, 0));
        System.out.println(voorraad.getRijElement(0, 1));
        System.out.println(voorraad.getRijElement(2, 0));
//
        MagazijnOverzicht panel = new MagazijnOverzicht(voorraad); //object met waardes meegeven naar panel indien nodig
        add(panel);
//
//        for (int i = 0; i < 5; i++) {
//            geheleVoorraad.add(new ArrayList<Product>(5));
//        }
//
//        for (int i = 0; i < 5; i++) {
//            rij1.add(null);
//        }
//        rij1.set(4, new Product("rood", 5));
//        rij1.set(0, new Product("rood", 6));
//        rij1.set(2, new Product("rood", 3));
//        geheleVoorraad.set(0, rij1);
////        geheleVoorraad.get(1).set(2, new Product("rood", 5));
//        System.out.println(geheleVoorraad.get(0).get(0));

        orders.add(new JButton("Order 1"));
        orders.add(new JButton("Order 2"));
        orders.add(new JButton("Order 3"));
        orders.add(new JButton("Order Teun"));

        JPanel jp = new JPanel(); //panel om knoppen in te doen, later voeg je dit aan de scrollbar toe
        jp.setLayout(new GridLayout(15, 0)); //grid layout voor de knoppen: lange rij van boven naar beneden.
        jp.setPreferredSize(new Dimension(600, 800)); //grootte van van de panel

        for (int i = 0; i < orders.size(); i++) { //for loop om elke knop toe te voegen aan de panel
            jp.add(orders.get(i));
            orders.get(i).addActionListener(this);
        }

        JScrollPane scrollPane = new JScrollPane(jp); //maak scrollbar panel aan met het panel vol knoppen erin

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // deze 2 regels zorgen dat de scrollbars altijd te zien zijn
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25); //bepaald scrollsnelheid van de scrollbar
        add(scrollPane);

//        add(new JButton("Koek")); //dit nog vervangen met pickvolgorde?
        OrderPickVolgorde panel3 = new OrderPickVolgorde();
        add(panel3);

        InvoegenVoorraadNoodstopPanel panel2 = new InvoegenVoorraadNoodstopPanel();

        add(panel2);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < orders.size(); i++) {
                if (e.getSource() == orders.get(i)){
                    OrderDialoog dialoog = new OrderDialoog(this, true, "Order: " + (i + 1));

                }
            }
            repaint();
    }
}
