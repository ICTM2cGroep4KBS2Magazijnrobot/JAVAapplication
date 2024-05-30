import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderInvoegen extends JDialog {
    private JTextField customerIDTextField;
    private JTextField arrayListTextField;

    public OrderInvoegen(Frame owner) {
        super(owner, "Order Invoegen", true);

        JLabel customerIDLabel = new JLabel("Klant ID:");
        JLabel arrayListLabel = new JLabel("Productenlijst:");

        customerIDTextField = new JTextField(10);
        arrayListTextField = new JTextField(20);

        JButton submitButton = new JButton("OK");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int klantnmr = Integer.parseInt(customerIDTextField.getText());
                String arrayListInput = arrayListTextField.getText();
                ArrayList<Integer> arrayList = intArray(arrayListInput);
                int Resultaat = 0;
                Resultaat = DB_connectie.OrderInvoer(arrayList, klantnmr);
                if (Resultaat == 2) {
                    JOptionPane.showMessageDialog(null, "Order toegevoegd");
                } else if (Resultaat == 1) {
                    JOptionPane.showMessageDialog(null, "Geen bestaande klant");
                } else if (Resultaat == 3) {
                    JOptionPane.showMessageDialog(null, "Geen product meegegeven");
                }
                setVisible(false);
            }
        });

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(customerIDLabel, constraints);

        constraints.gridx = 1;
        panel.add(customerIDTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(arrayListLabel, constraints);

        constraints.gridx = 1;
        panel.add(arrayListTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(submitButton, constraints);

        getContentPane().add(panel);
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
    }

    private ArrayList<Integer> intArray(String input) {
        ArrayList<Integer> producten = new ArrayList<>();
        String[] numberStrings = input.split(",");
        for (String numberString : numberStrings) {
            try {
                int number = Integer.parseInt(numberString.trim());
                producten.add(number);
            } catch (NumberFormatException e) {
                System.out.println("Invoer moet een nummer zijn.: " + numberString);
            }
        }
        return producten;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                OrderInvoegen dialog = new OrderInvoegen(new JFrame());
                dialog.setVisible(true);
                System.exit(0);
            }
        });
    }
}