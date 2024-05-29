import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrderDialoog extends JDialog implements ActionListener {

    private JTextField jtfAantal, jtfTekst;
    String picklist = "";
    private Voorraad voorraad;
    private int index = 0;
    private int orderid;
    private JButton jbOrderUitvoeren, jbOrderAanpassen, jbOrderVerwijderen, jbAnnuleren, jbPakbon;

    private ArrayList<Integer> stockitemids = new ArrayList<>();
    private boolean isUitvoerenOK = false;
    private MainFrame mainframe;


    OrderDialoog(JFrame frame, boolean modal, String ordernaam, int CustomerID, int OrderID, Voorraad voorraad, MainFrame mainframe){
        super(frame, modal);
        setTitle(ordernaam);
        setSize(700,600);
        setLayout(new GridLayout(3,2));
        this.voorraad = voorraad;
        this.orderid = OrderID;
        this.mainframe = mainframe;
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

        add(new JLabel(""));

        jbOrderUitvoeren = new JButton("Order Uitvoeren");
        add(jbOrderUitvoeren);
        jbOrderUitvoeren.addActionListener(this);

        jbOrderAanpassen = new JButton("Order Aanpassen");
        add(jbOrderAanpassen);
        jbOrderAanpassen.addActionListener(this);

        jbOrderVerwijderen = new JButton("Order Verwijderen");
        add(jbOrderVerwijderen);
        jbOrderVerwijderen.addActionListener(this);

        jbPakbon = new JButton("Pakbon");
        add(jbPakbon);
        jbPakbon.addActionListener(this);

        jbAnnuleren = new JButton("Annuleren");
        add(jbAnnuleren);
        jbAnnuleren.addActionListener(this);




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
            } else if (e.getSource() == jbOrderAanpassen) {
                AanpassenDialoog Aanpassendialoog = new AanpassenDialoog(this,true,orderid, voorraad, this);
                if(Aanpassendialoog.IsOK()){
                    setVisible(false);
                }


            } else if (e.getSource() == jbOrderVerwijderen) {

                int result = JOptionPane.showConfirmDialog(this, "Druk op OK om de order te verwijderen", "Weet je het zeker?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    DB_connectie.OrderRemove(orderid);
                    JOptionPane.showMessageDialog(null, "Order verwijderd");
                    mainframe.updateRepaint();
                    mainframe.Removebutton();
                    setVisible(false);
                }
            } else if (e.getSource() == jbPakbon) {
                showPakbon(stockitemids);
            }

        }
        catch(Exception ignored){
        }
        revalidate();
        repaint();
        mainframe.updateRepaint();
        }

    public void updateRepaint() {
        repaint();
    }
    public void showPakbon(ArrayList<Integer> stockitemids) {
        ArrayList<String> customerInfo = DB_connectie.GetCustomer(new ArrayList<>(), orderid);
        JLabel addressL = new JLabel("Adres:");
        JTextField address = new JTextField();
        address.setEditable(false);
        JLabel naamL = new JLabel("Naam:");
        JTextField naam = new JTextField();
        naam.setEditable(false);
        JLabel stadL = new JLabel("Stad:");
        JTextField stad = new JTextField();
        stad.setEditable(false);

        if (!customerInfo.isEmpty()) {
            naam.setText(customerInfo.get(0));
            stad.setText(customerInfo.get(2));
            address.setText(customerInfo.get(1));
        }

        String[] namen = { "ID", "Naam", "Aantal", "Prijs" };
        DefaultTableModel tableModel = new DefaultTableModel(namen, 0);

        double totaal = 0.0;
        ArrayList<Product> productenlijst = new ArrayList<Product>();

        for (int i = 0; i < stockitemids.size(); i++) {
            try {
                Product product = voorraad.getArtikel(stockitemids.get(i));
                productenlijst.add(product);
            } catch (Exception e) {
                System.out.println("error");
            }
        }

        for (int i = 0; i < productenlijst.size(); i++) {
            int aantal = 1;
            Product currentProduct = productenlijst.get(i);
            double prijs = currentProduct.getPrijs();
            for (int j = i + 1; j < productenlijst.size(); j++) {
                Product nextProduct = productenlijst.get(j);
                if (currentProduct.getArtikelID() == nextProduct.getArtikelID()) {
                    aantal++;
                    totaal = totaal + nextProduct.getPrijs();
                    productenlijst.remove(j);
                    j--;
                }
            }
            totaal = totaal + prijs;
            Object[] rowData = { currentProduct.getArtikelID(), currentProduct.getNaam(), aantal, prijs };
            tableModel.addRow(rowData);

        }
        Object[] totalRow = { "Totaal Prijs:", "", "", totaal };
        tableModel.addRow(totalRow); // extra rij voor de totaal prijs

        JTable table = new JTable(tableModel);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));
        inputPanel.add(naamL);
        inputPanel.add(naam);
        inputPanel.add(stadL);
        inputPanel.add(stad);
        inputPanel.add(addressL);
        inputPanel.add(address);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, panel, "Pakbon", JOptionPane.PLAIN_MESSAGE);
    }}
