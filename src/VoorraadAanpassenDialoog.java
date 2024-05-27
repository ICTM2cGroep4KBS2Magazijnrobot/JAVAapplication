import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VoorraadAanpassenDialoog  extends JDialog {
    private String DialogHeader = "Voorraad Aanpassen Dialog";
    private JButton voorraadAanpassen;
    private JLabel AanpassenArtikel;
    private JLabel GewildeVoorraad;
    private Voorraad voorraad;
    MainFrame mainFrame;
    private JTextField jtfAanpassenArtikel;
    private JTextField jtfGewildeVoorraad;

    public VoorraadAanpassenDialoog(Frame frame, boolean modal, Voorraad voorraad, MainFrame mainFrame) {
//        super(frame, modal);
        setPreferredSize(( new Dimension(500,250)));
        setBackground(Color.WHITE);
        setLayout(new FlowLayout());

        this.voorraad = voorraad;
        this.mainFrame = mainFrame;
        AanpassenArtikel = new JLabel("Aanpassen Artikel");
        add(AanpassenArtikel);


        jtfAanpassenArtikel = new JTextField(7);
        add(jtfAanpassenArtikel);
        //jtfAanpassenArtikel.addActionListener(this);

        GewildeVoorraad = new JLabel("Gewilde Voorraad");
        add(GewildeVoorraad);

        jtfGewildeVoorraad = new JTextField(7);
        add(jtfGewildeVoorraad);
       // jtfGewildeVoorraad.addActionListener(this);

        voorraadAanpassen = new JButton("voorraad Aanpassen");
        add(voorraadAanpassen);
        voorraadAanpassen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int artikelID = Integer.parseInt(jtfAanpassenArtikel.getText());
                    int nieuweVoorraad = Integer.parseInt(jtfGewildeVoorraad.getText());

                    if (nieuweVoorraad<0) {
                        JOptionPane.showMessageDialog(null, "voorraad kan niet negatief zijn. Voer een geldig positief nummer in. " , "fout", JOptionPane.ERROR_MESSAGE);
                        return;// het stop uitvoering van de methode als het is geen geldige nummer.
                    }
                    if (!DB_connectie.artikelBestaat(artikelID)) {
                        JOptionPane.showMessageDialog(null, "Artikel ID bestaat niet. Voer een geldig artikel ID in.", "Fout", JOptionPane.ERROR_MESSAGE);
                        return; // Stop de uitvoering van de methode als het artikel ID ongeldig is
                    }
                    // Roep de updateVoorraadHoeveelheid methode aan
                    DB_connectie.updateQuantityOnHand(artikelID, nieuweVoorraad);

                    DB_connectie.updateMagazijn(voorraad);
                    JOptionPane.showMessageDialog(null, "Voorraad succesvol aangepast.");
                    dispose();
                    mainFrame.updateRepaint();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ongeldige invoer. Zorg ervoor dat je geldige nummers invoert.", "Fout", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        pack();

        setVisible(true);

    }


}
