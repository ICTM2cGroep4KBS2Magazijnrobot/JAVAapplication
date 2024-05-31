import javax.swing.*;

public class OrderButton extends JButton {

    private int OrderID;
    private int CustomerID;

    public OrderButton(String text, int orderID, int CustomerID) {
        super(text);
        this.OrderID = orderID;
        this.CustomerID = CustomerID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public int getCustomerID() {
        return CustomerID;
    }
}
