import javax.swing.*;
import java.awt.*;

public class VoorraadAanpassenDialoog  extends JDialog{
    private String DialogHeader = "Voorraad Aanpassen Dialog";
    private JButton voorraadAanpassen;
    private JButton AanpassenArtikel;
    private JButton GewildeVoorraad;
    private Voorraad voorraad;
    private JTextField jtfAanpassenArtikel;
    private JTextField jtfGewildeVoorraad;


    public VoorraadAanpassenDialoog(Frame frame, boolean modal) {
//        super(frame, modal);
        setPreferredSize(( new Dimension(500,250)));
        this.voorraad = voorraad;
        setBackground(Color.WHITE);
        setLayout(new FlowLayout());

        AanpassenArtikel = new JButton("Aanpassen Artikel");
        add(AanpassenArtikel);

        jtfAanpassenArtikel = new JTextField(5);
        add(jtfAanpassenArtikel);

        GewildeVoorraad = new JButton("Gewilde Voorraad");
        add(GewildeVoorraad);

        jtfGewildeVoorraad = new JTextField(5);
        add(jtfGewildeVoorraad);

        voorraadAanpassen = new JButton("voorraad Aanpassen");
        add(voorraadAanpassen);

        pack();

        setVisible(true);

    }




}
