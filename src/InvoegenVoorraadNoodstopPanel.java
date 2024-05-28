
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
    Voorraad voorraad;
    MainFrame mainFrame;
    JButton jbOrderInvoeren, jbVoorraadWeergeven, jbNoodstop, jbOrderAanpassen;
    InvoegenVoorraadNoodstopPanel(SerialPort serialPort, Voorraad voorraad, MainFrame mainFrame) { //in constructor object met waardes van dialoog meegeven
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.RED);
        setLayout(new GridLayout(3,1));
        this.serialPort = serialPort;
        this.voorraad = voorraad;
        this.mainFrame = mainFrame;

        jbOrderInvoeren = new JButton("Order Invoeren");
        add(jbOrderInvoeren);
        jbOrderInvoeren.addActionListener(this);


        jbOrderAanpassen = new JButton("Voorraad Aanpassen");
        add(jbOrderAanpassen);
        jbOrderAanpassen.addActionListener(this);

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
        if (e.getSource() == jbOrderInvoeren) {
            System.out.println("Order Invoeren");

            OrderInvoegen orderInvoegenDialog = new OrderInvoegen((Frame) SwingUtilities.getWindowAncestor(this));
            orderInvoegenDialog.setVisible(true);
        }
        if(e.getSource() == jbNoodstop){ // functie voor noodstop alert. Print tot nu toe alleen het resultaat in de console
            Component frame = null;
            try {
                Integer getal = 3; //voor nu het nummer om noodstop aan te zetten
                serialPort.getOutputStream().write(getal.byteValue());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

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
        if(e.getSource() == jbOrderAanpassen){
            System.out.println("Voorraad Aanpassen");
            VoorraadAanpassenDialoog dialoog = new VoorraadAanpassenDialoog(new Frame(), true, voorraad, mainFrame);
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 250);


        }
    }
}
