import javax.swing.*;
import java.awt.*;

public class VoorraadAanpassenDialoog  extends JDialog{
    private String DialogHeader = "Voorraad Aanpassen Dialo";
    private JButton voorraadAanpassen;
    private JButton AanpassenArtikel;
    private JButton GewildeVoorraad;
    private JDialog voorraadAanpassenDialoog;
    private Voorraad voorraad;
    private JTextField jtfAanpassenArtikel;
    private JTextField jtfGewildeVoorraad;


    public VoorraadAanpassenDialoog(InvoegenVoorraadNoodstopPanel dialog,boolean modal) {
        setPreferredSize(new Dimension(700, 600));
        this.voorraad = voorraad;
        setBackground(Color.WHITE);
        setLayout(new FlowLayout());
        setModal(true);
        setVisible(true);

        voorraadAanpassenDialoog = new JDialog();
        add(voorraadAanpassenDialoog);

        voorraadAanpassen = new JButton("voorraad Aanpassen");
        add(voorraadAanpassen);

        AanpassenArtikel = new JButton("Aanpassen Artikel");
        add(AanpassenArtikel);

        GewildeVoorraad = new JButton("Gewilde Voorraad");
        add(GewildeVoorraad);

        jtfAanpassenArtikel = new JTextField( );
        add(jtfAanpassenArtikel);

        jtfGewildeVoorraad = new JTextField( );
        add(jtfGewildeVoorraad);

        setVisible(true);

    }




}
