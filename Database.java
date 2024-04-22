import java.sql.*;

public class Database {
    public String url = "jdbc:mysql://localhost:3306/nerdygadgetskbs2"; // Change this to your own database
    public String username = "root"; // Change this to your own username
    public String password = "mysql"; // Change this to your own password

    public boolean isDatabaseAvailable() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
