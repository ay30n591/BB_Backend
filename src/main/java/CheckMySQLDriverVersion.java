import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CheckMySQLDriverVersion {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/beatbuddy", "root", "1234");

            // Get driver version
            System.out.println("MySQL JDBC Driver Version: " + connection.getMetaData().getDriverVersion());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
