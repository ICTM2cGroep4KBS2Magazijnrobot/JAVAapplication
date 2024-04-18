import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/nerdygadgetskbs2";
        String username = "root";
        String password = "mysql";

        Connection connection = DriverManager.getConnection(url, username, password);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM stockitems WHERE StockItemID = ?");
        preparedStatement.setInt(1, 4);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            String ID = rs.getString(1);
            String naam = rs.getString(2);
            String prijs = rs.getString(14);

            System.out.println("ID: " + ID + ", Naam: " + naam + ", Prijs: €" + prijs);
        }

        connection.close();
    }
}