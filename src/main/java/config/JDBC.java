package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBC {

    public static Connection getConnection() {
        Connection c = null;
        try {
            String url = "jdbc:mysql://localhost:3306/pizzastore";
            String username = "root";
            String password = "Tankhuong@25";
            c = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, "SQL connection error", e);
        }
        return c;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, "Error closing the connection", e);
            }
        }
    }
}
