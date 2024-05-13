import java.sql.*;
import java.util.ArrayList;

public class DB_connectie {
    public static String url = "jdbc:mysql://localhost:3306/nerdygadgetskbs2"; // Change this to your own database
    //public static String url = "jdbc:mysql://localhost:3307/nerdygadgets2"; // Change this to your own database

    public static String username = "root"; // Change this to your own username
    public static String password = ""; // Change this to your own password

    public DB_connectie(){

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

                if (xwaarde >= 0 && ywaarde >= 0) {
                    if (kleurID == 1) {
                        voorraad.setRijElement(ywaarde, xwaarde, new Product("rood", gewicht, voorraadArtikel, artikelID, naam));
                    } else if (kleurID == 2) {
                        voorraad.setRijElement(ywaarde, xwaarde, new Product("geel", gewicht, voorraadArtikel, artikelID, naam));
                    } else if (kleurID == 3) {
                        voorraad.setRijElement(ywaarde, xwaarde, new Product("blauw", gewicht, voorraadArtikel, artikelID, naam));
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

}
