/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseConnection;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author mariane
 */
public class DBConn {

    private static final String URL = "jdbc:mysql://localhost:3307/officer_monitoring"; // your database name
    private static final String USER = "root"; // default user for XAMPP
    private static final String PASSWORD = ""; // leave blank if no password

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Load MySQL driver (optional for newer versions)
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            // JOptionPane.showMessageDialog(null, "Database Connected Successfully!");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "MySQL Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Connection Failed: " + e.getMessage());
        }
        return conn;
    }
}
