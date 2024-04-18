import javax.swing.*;
import java.awt.*;

public class InvoegenVoorraadNoodstopPanel extends JPanel{
    JButton jbOrderInvoeren, jbVoorraadWeergeven, jbNoodstop;
    InvoegenVoorraadNoodstopPanel(){ //in constructor object met waardes van dialoog meegeven
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.RED);
        setLayout(new GridLayout(3,1));
        System.out.println("haal deze zin weg");

        jbOrderInvoeren = new JButton("Order Invoeren");
        add(jbOrderInvoeren);

        jbVoorraadWeergeven = new JButton("Voorraad Weergeven");
        add(jbVoorraadWeergeven);

        jbNoodstop = new JButton("Noodstop");
        jbNoodstop.setBackground(Color.RED);
        add(jbNoodstop);


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("default", Font.PLAIN, 14));

//        tekenRechthoek1(g);
//        tekenRechthoek2(g);
    }

    private void tekenRechthoek1(Graphics g){
        g.setColor(Color.YELLOW);
        g.fillRect(0, 200, getWidth(), 50);
    }
    private void tekenRechthoek2(Graphics g){
        g.setColor(Color.BLUE);
        g.fillRect(0, 250, getWidth(), getHeight());
    }

}
