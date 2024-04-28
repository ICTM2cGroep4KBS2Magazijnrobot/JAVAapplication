import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OrderPickVolgorde extends JPanel {

    private String pickvolgordeHeader = "Geen order geselecteerd ";
    private int huidigeOrder;

    private Voorraad voorraad;
    private ArrayList<Integer> stockitemids = new ArrayList<>();



    OrderPickVolgorde(Voorraad voorraad){ //in constructor object met waardes van dialoog meegeven
        setPreferredSize(new Dimension(1900, 780));
        setBackground(Color.WHITE);
        this.voorraad = voorraad;

    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("default", Font.PLAIN, 30));

        g.drawString(pickvolgordeHeader, getWidth() / 2 - 160, 40);
        stockitemids.clear();

        if (huidigeOrder > 0){ //hier alle orderlines printen van een order.
            g.setFont(new Font("default", Font.PLAIN,15));
            DB_connectie.getOrderlines(stockitemids, huidigeOrder);
            //HIER NU Bin Packing Problem toepassen met gebruik van stockitemids
            //HIER NU Bin Packing Problem toepassen met gebruik van stockitemids
            //Hierna wordt namelijk elk product uit stockitemids geprint op het scherm


            int startX = 10;
            int startY = 70;

            for (int i = 0; i < stockitemids.size(); i++) {
                g.drawString("--------------------------------------------------------------------------------",
                        startX, startY + 10);

                Product product = voorraad.getArtikel(stockitemids.get(i));
                g.drawString(i+ 1 + ". Artikel " + product.getArtikelID() + ": " + product.getNaam(), startX, startY);

                startY+= 30;
            }
        }
    }
    public void setPickvolgordeHeader(String pickvolgordeHeader) {
        this.pickvolgordeHeader = pickvolgordeHeader;
    }

    public void setHuidigeOrder(int huidigOrder) {
        setPickvolgordeHeader("Pickvolgorde Order: " + huidigOrder);
        this.huidigeOrder = huidigOrder;
    }
}

