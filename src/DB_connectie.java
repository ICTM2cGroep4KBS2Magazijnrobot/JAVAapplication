import java.sql.*;

public class DB_connectie {
    public static String url = "jdbc:mysql://localhost:3306/nerdygadgetskbs2"; // Change this to your own database
    public static String username = "root"; // Change this to your own username
    public static String password = "mysql"; // Change this to your own password

    public DB_connectie() throws SQLException {

    }

    public static Voorraad updateMagazijn(Voorraad voorraad){
        try {


            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM stockitems WHERE StockItemID <= ?");
            preparedStatement.setInt(1, 25); // Eerste getal is de index van de vraagteken, tweede getal is de waarde die je wilt invullen.
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int xwaarde = rs.getInt(26);
                int ywaarde = rs.getInt(27);
                int kleurID = rs.getInt(4);
                int gewicht = rs.getInt(28);
                int voorraadArtikel = rs.getInt(29);
                String artikelID = rs.getString(1);

                String ID = rs.getString(1);
                String naam = rs.getString(2);
                String prijs = rs.getString(14);

                if (xwaarde >= 0 && ywaarde >= 0) {
                    if (kleurID == 1) {
                        voorraad.setRijElement(ywaarde, xwaarde, new Product("rood", gewicht, voorraadArtikel, artikelID));
                    } else if (kleurID == 2) {
                        voorraad.setRijElement(ywaarde, xwaarde, new Product("geel", gewicht, voorraadArtikel, artikelID));
                    } else if (kleurID == 3) {
                        voorraad.setRijElement(ywaarde, xwaarde, new Product("blauw", gewicht, voorraadArtikel, artikelID));
                    }
                }
                System.out.println("ID: " + artikelID + ", Naam: " + naam + ", Prijs: €" + prijs + " Opslagplek is: " + xwaarde + ", " + ywaarde + "Voorraad is: " + voorraadArtikel);

            }
            return voorraad;
        }
        catch (SQLException e){
            System.out.println("Connectie gefaald " + e.getMessage());
            return null;
        }
    }
}
