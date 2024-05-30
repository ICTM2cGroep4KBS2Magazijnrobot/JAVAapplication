import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class AanpassenDialoog extends JDialog implements ActionListener {

    private JTextField jtfID;
    private boolean IsOK = false;


    private JButton jbAanpassen, jbVerwijderen ,jbAnnuleren;
    private JLabel jlid;

    private ArrayList<Integer> stockitemids = new ArrayList<>();
    private String picklist = "";
    private int index = 0;
    private Voorraad voorraad;
    private int orderID;
    private OrderDialoog dialoog;
    private JPanel productPanel;


    AanpassenDialoog(Dialog parent, boolean modal, int OrderID, Voorraad voorraad, OrderDialoog dialoog) {
        super(parent, modal);
        this.orderID = OrderID;
        this.dialoog = dialoog;
        setTitle("aanpassen order: " + this.orderID);
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

        productPanel = new JPanel();
        try {
            productPanel.setLayout(new GridLayout(index, 1));
        } catch (Exception e) {
            System.out.println("Foutcode: " + e.getMessage());;
        }
        if (stockitemids.size() == 1){
            productPanel.setPreferredSize(new Dimension(315, stockitemids.size()*80)); //grootte van van de panel
        }else {
            productPanel.setPreferredSize(new Dimension(315, stockitemids.size() * 50)); //grootte van van de panel
        }
        productPanel.add(new JLabel("Inhoud van order:"));
        for (int i = 0; i < stockitemids.size(); i++) {
//            if (i == 0) {
//                jp.add(new JLabel("Inhoud van order:"));
//            }
            if (i >= 0) {
                productPanel.add(new JLabel("------------------------------------------------------"));
                try {
                    Product product = voorraad.getArtikel(stockitemids.get(i));
                    productPanel.add(new JLabel(stockitemids.get(i).toString() + " - " + product.getNaam()));
                } catch (Exception e) {
                    System.out.println("Error mbt geen database connectie. Foutcode: " + e.getMessage());
                    ;
                }
            }
            index = i + 1;
        }

        JScrollPane scrollPane = new JScrollPane(productPanel);
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
        setTitle("aanpassen order: " + this.orderID + melding);
    }
    public void RemoveProduct(int productID){
        for (int i = 0; i < stockitemids.size(); i++) {
            if(stockitemids.get(i) == productID){
                stockitemids.remove(stockitemids.get(i));
                productPanel.remove(stockitemids.get(i));
                System.out.println("Product verwijderd");
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        setTitelFoutmelding("");

        if (e.getSource() == jbAanpassen){
            try {
                int ProductID = Integer.parseInt(jtfID.getText());

                if (!DB_connectie.artikelBestaat(ProductID)) {
                    JOptionPane.showMessageDialog(null, "Artikel ID bestaat niet. Voer een geldig artikel ID in.", "Fout", JOptionPane.ERROR_MESSAGE);
                    return; // Stop de uitvoering van de methode als het artikel ID ongeldig is
                }

                DB_connectie.addItem(this.orderID, ProductID, true);
                JOptionPane.showMessageDialog(null, "Artikel toegevoegd aan de order");



            }catch(NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Ongeldige invoer. Zorg ervoor dat je geldige nummers invoert.", "Fout", JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == jbVerwijderen){
            try {
                int ProductID = Integer.parseInt(jtfID.getText());

                if (!DB_connectie.artikelBestaat(ProductID)) {
                    JOptionPane.showMessageDialog(null, "Artikel ID bestaat niet. Voer een geldig artikel ID in.", "Fout", JOptionPane.ERROR_MESSAGE);
                    return; // Stop de uitvoering van de methode als het artikel ID ongeldig is
                }

                DB_connectie.deleteItem(this.orderID, ProductID);
                RemoveProduct(ProductID);
                JOptionPane.showMessageDialog(null, "Artikel verwijderd");

            }catch(NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Ongeldige invoer. Zorg ervoor dat je geldige nummers invoert.", "Fout", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == jbAnnuleren) {
            setVisible(false);
        }

        repaint();
        dialoog.updateRepaint();

    }
}
