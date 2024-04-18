import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderDialoog extends JDialog implements ActionListener {

    private JTextField jtfAantal, jtfTekst;

    private JButton jbOrderUitvoeren, jbOrderAanpassen, jbOrderVerwijderen, jbAnnuleren;

    private boolean isOK = false;


    OrderDialoog(JFrame frame, boolean modal, String ordernaam){
        super(frame, modal);
        setTitle(ordernaam);
        System.out.println("haal deze zin weg");
        setSize(500,250);
        setLayout(new GridLayout(3,2));

        add(new JLabel(ordernaam));
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

    public boolean isOK() {
        return isOK;
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
        }
        catch(Exception ignored){

        }
    }
}
