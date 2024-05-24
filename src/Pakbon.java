import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

    public class Pakbon extends JFrame {
        private ArrayList<Product> productList;
        private JTextArea textArea;

        public Pakbon() {
            productList = new ArrayList<>();

            setTitle("Product List");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 300);
            getContentPane().setBackground(Color.BLACK);

            textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setForeground(Color.WHITE);
            textArea.setBackground(Color.BLACK);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

            JScrollPane scrollPane = new JScrollPane(textArea);
            add(scrollPane);
        }

        public void setProductList(ArrayList<Product> productList) {
            this.productList = productList;
            updateTextArea();
        }

        private void updateTextArea() {
            StringBuilder sb = new StringBuilder();
            for (Product product : productList) {
                sb.append(product.getNaam()).append(" - $").append(product.getArtikelID()).append("\n");
            }
            textArea.setText(sb.toString());
        }
}
