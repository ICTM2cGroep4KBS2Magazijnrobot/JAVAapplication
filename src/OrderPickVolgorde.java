import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OrderPickVolgorde extends JPanel {

    private String pickvolgordeHeader = "Geen order geselecteerd";
    private String pickvolgorde = "";
    private ArrayList<Integer> stockitemids = new ArrayList<>();

    OrderPickVolgorde(){ //in constructor object met waardes van dialoog meegeven
        setPreferredSize(new Dimension(1900, 780));
        setBackground(Color.WHITE);

    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("default", Font.PLAIN, 30));

        g.drawString(pickvolgordeHeader, getWidth() / 2 - 170, 40);

        g.drawString(pickvolgorde, getWidth() / 2 - 310, 80);
    }

    public void setPickvolgorde(String pickvolgorde) {
        this.pickvolgorde = pickvolgorde;
    }

    public void setPickvolgordeHeader(String pickvolgordeHeader) {
        this.pickvolgordeHeader = pickvolgordeHeader;
    }
}

