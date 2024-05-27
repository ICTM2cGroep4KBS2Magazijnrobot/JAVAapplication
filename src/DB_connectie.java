import java.sql.*;
import java.util.ArrayList;

public class DB_connectie {
//    public static String url = "jdbc:mysql://localhost:3306/nerdygadgetskbs2"; // Change this to your own database
  public static String url = "jdbc:mysql://localhost:3306/nerdygadgetskbs2"; // Change this to your own database
    public static String username = "root"; // Change this to your own username
    public static String password = ""; // Change this to your own password

    public DB_connectie(){

    }

public static void  updateQuantityOnHand(int stockItemID, int newQuantity){
        String updateQuery = "UPDATE stockitemholdings SET QuantityOnHand = ? WHERE StockItemID = ?";

    try (Connection connection = DriverManager.getConnection(url, username, password);
         PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

        //  parameters
        preparedStatement.setInt(1, newQuantity);
        preparedStatement.setInt(2, stockItemID);


        int rowsAffected = preparedStatement.executeUpdate();
        System.out.println("Rows affected: " + rowsAffected);

    } catch (SQLException e) {
        System.out.println("Update failed: " + e.getMessage());
    }
}

    public static boolean artikelBestaat(int stockItemID) {
        String checkQuery = "SELECT COUNT(*) FROM stockitemholdings WHERE StockItemID = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(checkQuery)) {

            preparedStatement.setInt(1, stockItemID);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Check failed: " + e.getMessage());
        }
        return false;
    }

    public static Voorraad updateMagazijn(Voorraad voorraad){// Functie voor het updaten van de voorraad en het koppelen van een kleurID
        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM stockitems WHERE StockItemID <= ?");
            preparedStatement.setInt(1, 25); // Eerste getal is de index van de vraagteken, tweede getal is de waarde die je wilt invullen.
            ResultSet rs = preparedStatement.executeQuery();

            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM stockitemholdings WHERE StockItemID <= ?");
            preparedStatement1.setInt(1, 25); // Eerste getal is de index van de vraagteken, tweede getal is de waarde die je wilt invullen.
            ResultSet rs1 = preparedStatement1.executeQuery();

            while (rs.next() && rs1.next()) {
                int xwaarde = rs.getInt(26);
                int ywaarde = rs.getInt(27);
                int kleurID = rs.getInt(4);
                int gewicht = rs.getInt(28);
                int voorraadArtikel = rs1.getInt(2);
                int artikelID = rs.getInt(1);
                String naam = rs.getString(2);
                double btw = rs.getDouble(13);
                double retail = rs.getDouble(15);

                double prijs = retail * (btw/100+1);
                System.out.println(prijs);

                if (xwaarde >= 0 && ywaarde >= 0) {
                    if (kleurID == 1) {
                        voorraad.setRijElement(ywaarde, xwaarde, new Product("rood", gewicht, voorraadArtikel, artikelID, naam, (int) prijs));
                    } else if (kleurID == 2) {
                        voorraad.setRijElement(ywaarde, xwaarde, new Product("geel", gewicht, voorraadArtikel, artikelID, naam, (int) prijs));
                    } else if (kleurID == 3) {
                        voorraad.setRijElement(ywaarde, xwaarde, new Product("blauw", gewicht, voorraadArtikel, artikelID, naam, (int) prijs));
                    }
                }
            }
            return voorraad;
        }
        catch (SQLException e){
            System.out.println("Connectie gefaald " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<OrderButton> updateOrders(ArrayList<OrderButton> orderButtons){// Functie voor het updaten van de orderlijst
        try {

            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM orders");
//            preparedStatement.setInt(1, 25); // Eerste getal is de index van de vraagteken, tweede getal is de waarde die je wilt invullen.
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                orderButtons.add(new OrderButton("Order: " + rs.getInt(1), rs.getInt(1), rs.getInt(2)));
            }
            return orderButtons;
        }
        catch (SQLException e){
            System.out.println("Connectie gefaald " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<Integer> getOrderlines(ArrayList<Integer>  stockitemids, int orderid){
        try {

            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM orderlines WHERE orderid = ?");
            preparedStatement.setInt(1, orderid); // Eerste getal is de index van de vraagteken, tweede getal is de waarde die je wilt invullen.
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                stockitemids.add(rs.getInt(3));
            }
            return stockitemids;
        }
        catch (SQLException e){
            System.out.println("Connectie gefaald " + e.getMessage());
            return null;
        }
    }
    public static ArrayList<String> GetCustomer(ArrayList<String> Customer, int orderid) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT CustomerID FROM `orders` WHERE OrderID = ?");
            preparedStatement.setInt(1, orderid);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int customerID = rs.getInt("CustomerID");
                PreparedStatement customerStatement = connection.prepareStatement("SELECT * FROM `customers` WHERE CustomerID = ?");
                customerStatement.setInt(1, customerID);
                ResultSet customerRS = customerStatement.executeQuery();
                while (customerRS.next()) {
                    String customerName = customerRS.getString("CustomerName");
                    String customerPO1 = customerRS.getString("PostalAddressLine1");
                    String customerPO2 = customerRS.getString("PostalAddressLine2");

                    Customer.add(customerName);
                    Customer.add(customerPO1);
                    Customer.add(customerPO2);
                }
            }
        } catch (SQLException e) {
            System.out.println("Connectie gefaald " + e.getMessage());
            return null;
        }
        return Customer;
    }
}

