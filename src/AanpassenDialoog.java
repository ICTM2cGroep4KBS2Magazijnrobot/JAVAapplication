import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class AanpassenDialoog extends JDialog implements ActionListener {

    private JTextField jtfID;
    private boolean IsOK = false, verwijderen = false, toevoegen = false;


    private JButton jbAanpassen, jbVerwijderen ,jbAnnuleren;
    private JLabel jlid;

    private ArrayList<Integer> stockitemids = new ArrayList<>();
    private String picklist = "";
    private int index = 0;
    private Voorraad voorraad;
    private int OrderID;


    AanpassenDialoog(Dialog parent, boolean modal, int OrderID, Voorraad voorraad) {
        super(parent, modal);
        this.OrderID = OrderID;
        setTitle("aanpassen order: " + this.OrderID);
        setTitelFoutmelding("");
        setSize(700, 300);
        setLayout(new GridLayout(2,3));


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

        JScrollPane scrollPane = new JScrollPane(jp);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // deze 2 regels zorgen dat de scrollbars altijd te zien zijn
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25); //bepaald scrollsnelheid van de scrollbar
        add(scrollPane);


        add(jlid = new JLabel(
                "------------|  Id: |------------"));

        jtfID = new JTextField(5);
        add(jtfID);


        jbAanpassen = new JButton("Toevoegen Product");
        add(jbAanpassen);
        jbAanpassen.addActionListener(this);

        jbVerwijderen = new JButton("verwijderen product");
        add(jbVerwijderen);
        jbVerwijderen.addActionListener(this);
        
        

        jbAnnuleren = new JButton("Sluiten");
        add(jbAnnuleren);
        jbAnnuleren.addActionListener(this);







        setVisible(true);
    }

    public boolean IsOK() {
        return IsOK;
    }

    private void setTitelFoutmelding(String melding) {
        setTitle("aanpassen order: " + this.OrderID + melding);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        setTitelFoutmelding("");

        if (e.getSource() == jbAanpassen){
            try {
                int ProductID = Integer.parseInt(jtfID.getText());

                DB_connectie.addItem(this.OrderID, ProductID);

            }catch(NumberFormatException nfe) {
                setTitelFoutmelding(" foute input!");
            }

        } else if (e.getSource() == jbVerwijderen){
            try {
                int ProductID = Integer.parseInt(jtfID.getText());


                DB_connectie.deleteItem(this.OrderID, ProductID);

            }catch(NumberFormatException nfe) {
                setTitelFoutmelding(" foute input!");
            }
        } else if (e.getSource() == jbAnnuleren) {
            setVisible(false);
        }

        repaint();

    }
}
