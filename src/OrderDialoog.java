import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrderDialoog extends JDialog implements ActionListener {

    private JTextField jtfAantal, jtfTekst;
    String picklist = "Te pakken artikelnummer(s): ";

    private JButton jbOrderUitvoeren, jbOrderAanpassen, jbOrderVerwijderen, jbAnnuleren;

    private ArrayList<Integer> stockitemids = new ArrayList<>();
    private boolean isUitvoerenOK = false;


    OrderDialoog(JFrame frame, boolean modal, String ordernaam, int CustomerID, int OrderID){
        super(frame, modal);
        setTitle(ordernaam);
        setSize(500,250);
        setLayout(new GridLayout(3,2));

        DB_connectie.getOrderlines(stockitemids, OrderID);

        for (Integer stockitem: stockitemids){
            if (stockitem != stockitemids.getLast()){
                picklist += stockitem + " - ";

            }
            else{
                picklist += stockitem;

            }
        }
        add(new JLabel(ordernaam));
        add(new JLabel(picklist));

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
            if(e.getSource() == jbOrderUitvoeren){
                isUitvoerenOK = true;
                setVisible(false);
            }
        }
        catch(Exception ignored){

        }
    }
}
