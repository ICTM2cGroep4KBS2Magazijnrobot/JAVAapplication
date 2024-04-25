import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InvoegenVoorraadNoodstopPanel extends JPanel implements ActionListener{
    private boolean noodstop = false;

    JButton jbOrderInvoeren, jbVoorraadWeergeven, jbNoodstop;
    InvoegenVoorraadNoodstopPanel(){ //in constructor object met waardes van dialoog meegeven
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.RED);
        setLayout(new GridLayout(2,1));


        jbOrderInvoeren = new JButton("Order Invoeren");
        add(jbOrderInvoeren);

        jbNoodstop = new JButton("Noodstop");
        jbNoodstop.setBackground(Color.RED);
        add(jbNoodstop);
        jbNoodstop.addActionListener(this);


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("default", Font.PLAIN, 14));
//        tekenNoodstop(g);

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
//    private void tekenNoodstop(Graphics g){
//        if (isNoodstop()){
//            g.setColor(Color.RED);
//            g.fillRect(0, 0, getWidth(), 50);
//        }
//    }

    public boolean isNoodstop() {
        return noodstop;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jbOrderInvoeren){
            System.out.println("Order Invoeren");
        }
        if(e.getSource() == jbNoodstop){ // functie voor noodstop alert. Print tot nu toe alleen het resultaat in de console
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "Druk op OK om het systeem vrij te geven", "Noodstop geactiveerd", JOptionPane.WARNING_MESSAGE);
            System.out.println("Noodstop geactiveerd");
            int num = JOptionPane.OK_OPTION;
            boolean bool = num == 0;
            if (bool) {
                System.out.println("Noodstop gedeactiveerd");
            }
        }
    }
}
