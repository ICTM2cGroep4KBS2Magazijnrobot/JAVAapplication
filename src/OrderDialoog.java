import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrderDialoog extends JDialog implements ActionListener {

    private JTextField jtfAantal, jtfTekst;
    String picklist = "";
    private Voorraad voorraad;
    private int index = 0;

    private JButton jbOrderUitvoeren, jbOrderAanpassen, jbOrderVerwijderen, jbAnnuleren;

    private ArrayList<Integer> stockitemids = new ArrayList<>();
    private boolean isUitvoerenOK = false;


    OrderDialoog(JFrame frame, boolean modal, String ordernaam, int CustomerID, int OrderID, Voorraad voorraad){
        super(frame, modal);
        setTitle(ordernaam);
        setSize(700,600);
        setLayout(new GridLayout(3,2));
        this.voorraad = voorraad;

        DB_connectie.getOrderlines(stockitemids, OrderID);

        for (Integer stockitem: stockitemids){
            if (stockitem != stockitemids.getLast()){
                picklist += stockitem + " - ";
            }
            else{
                picklist += stockitem;

            }
        }

        JPanel jp = new JPanel();
        try {
            jp.setLayout(new GridLayout(index, 1));
        } catch (Exception e) {
            System.out.println("Foutcode: " + e.getMessage());;
        }
        if (stockitemids.size() == 1){
            jp.setPreferredSize(new Dimension(315, stockitemids.size()*80)); //grootte van van de panel
        }else {
            jp.setPreferredSize(new Dimension(315, stockitemids.size() * 50)); //grootte van van de panel
        }
        jp.add(new JLabel("Inhoud van order:"));
        for (int i = 0; i < stockitemids.size(); i++) {
//            if (i == 0) {
//                jp.add(new JLabel("Inhoud van order:"));
//            }
            if (i >= 0) {
                jp.add(new JLabel("------------------------------------------------------"));
                try {
                    Product product = voorraad.getArtikel(stockitemids.get(i));
                    jp.add(new JLabel(stockitemids.get(i).toString() + " - " + product.getNaam()));
                } catch (Exception e) {
                    System.out.println("Error mbt geen database connectie. Foutcode: " + e.getMessage());
                    ;
                }
            }
            index = i + 1;
        }


        add(new JLabel(ordernaam));
        JScrollPane scrollPane = new JScrollPane(jp);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // deze 2 regels zorgen dat de scrollbars altijd te zien zijn
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25); //bepaald scrollsnelheid van de scrollbar
        add(scrollPane);

        jbOrderUitvoeren = new JButton("Order Uitvoeren");
        add(jbOrderUitvoeren);
        jbOrderUitvoeren.addActionListener(this);

        jbOrderAanpassen = new JButton("Order Aanpassen");
        add(jbOrderAanpassen);
        jbOrderAanpassen.addActionListener(this);

        jbOrderVerwijderen = new JButton("Order Verwijderen");
        add(jbOrderVerwijderen);
        jbOrderVerwijderen.addActionListener(this);

        jbAnnuleren = new JButton("Annuleren");
        add(jbAnnuleren);
        jbAnnuleren.addActionListener(this);

//
//
//
//        jbOk = new JButton("OK");
//        add(jbOk);
//        jbOk.addActionListener(this);
//
//        jbAnnuleren = new JButton("Annuleren");
//        add(jbAnnuleren);
//        jbAnnuleren.addActionListener(this);


        setVisible(true);
    }

    public boolean isUitvoerenOK() {
        return isUitvoerenOK;
    }

    public String getPicklist() {
        return picklist;
    }

    //getters maken voor invoer ophalen, bij invoer een getal moet je integer.parseint gebruiken
    public int getJtfAantal() {
        return Integer.parseInt(jtfAantal.getText());
    }

    public String getJtfTekst() {
        return jtfTekst.getText();
    }

    public void actionPerformed(ActionEvent e) {
        try{
            if(e.getSource() == jbAnnuleren){
                setVisible(false);
            }
            else if(e.getSource() == jbOrderUitvoeren){
                isUitvoerenOK = true;
                setVisible(false);
            }
        }
        catch(Exception ignored){
        }
//        if (e.getSource() == jbOrderUitvoeren){
//            setVisible(false);
        }
    }
//}
