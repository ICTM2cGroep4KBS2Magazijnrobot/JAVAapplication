package Tijdelijk;

import java.sql.*;

public class Template {
    public String url = "jdbc:mysql://localhost:3306/nerdygadgetskbs2"; // Change this to your own database
    public String username = "root"; // Change this to your own username
    public String password = "mysql"; // Change this to your own password

    public Template() throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);

        Statement statement = connection.createStatement();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM stockitems WHERE StockItemID <= ?");
        preparedStatement.setInt(1, 4); // Eerste getal is de index van de vraagteken, tweede getal is de waarde die je wilt invullen.
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            String ID = rs.getString(1);
            String naam = rs.getString(2);
            String prijs = rs.getString(14);

            System.out.println("ID: " + ID + ", Naam: " + naam + ", Prijs: €" + prijs);
        }

        // Voor updaten van een rij
        int update = statement.executeUpdate("UPDATE stockitems SET StockItemName = 'Hele grote knuffel' WHERE StockItemID = 100");
        System.out.println("Aantal rijen aangepast: " + update);

        connection.close(); // Close the connection
    }
}
