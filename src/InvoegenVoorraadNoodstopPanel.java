
import com.fazecast.jSerialComm.SerialPort;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class InvoegenVoorraadNoodstopPanel extends JPanel implements ActionListener{
    SerialPort serialPort;
    JButton jbOrderInvoeren, jbVoorraadWeergeven, jbNoodstop;
    InvoegenVoorraadNoodstopPanel(SerialPort serialPort) { //in constructor object met waardes van dialoog meegeven
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.RED);
        setLayout(new GridLayout(2,1));
        this.serialPort = serialPort;

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
    }



    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == jbOrderInvoeren){
            System.out.println("Order Invoeren");
        }
        if(e.getSource() == jbNoodstop){ // functie voor noodstop alert. Print tot nu toe alleen het resultaat in de console
            Component frame = null;
            try {
                Integer getal = 3; //voor nu het nummer om noodstop aan te zetten
                serialPort.getOutputStream().write(getal.byteValue());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
//            JOptionPane.showMessageDialog(frame, "Druk op OK om het systeem vrij te geven", "Noodstop geactiveerd", JOptionPane.WARNING_MESSAGE);
//            System.out.println("Noodstop geactiveerd");
//            int num = JOptionPane.OK_OPTION; // Omweg om de noodstop te deactiveren.
//            boolean bool = num == 0;
            int result = JOptionPane.showConfirmDialog(frame, "Druk op OK om het systeem vrij te geven", "Noodstop geactiveerd", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            System.out.println("Noodstop geactiveerd");
            if (result == JOptionPane.OK_OPTION) {
                try {
                    Integer getal = 2;
                    serialPort.getOutputStream().write(getal.byteValue());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println("Noodstop gedeactiveerd");
            }
        }
    }
}
