import javax.swing.*;
import java.awt.*;

public class OrderPickVolgorde extends JPanel {
    OrderPickVolgorde(){ //in constructor object met waardes van dialoog meegeven
        setPreferredSize(new Dimension(1900, 780));
        setBackground(Color.WHITE);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("default", Font.PLAIN, 30));

        g.drawString("Pickvolgorde: ", getWidth() / 2 - 80, 40);
    }


}

