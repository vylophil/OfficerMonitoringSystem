package Officer;

import DataBaseConnection.DBConn;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

public class OfficerDAO {

    // Adds an Officer
    public void addOfficer(Officer officer) {
        String sql = "INSERT INTO officer (Fname, MI, Lname, Gender, Bdate, Phone_num) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, officer.getFirstName());
            pstmt.setString(2, officer.getMiddleInitial());
            pstmt.setString(3, officer.getLastName());
            pstmt.setString(4, officer.getGender());
            pstmt.setString(5, officer.getBirthDate());
            pstmt.setString(6, officer.getPhoneNumber());

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Officer added successfully!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding officer: " + e.getMessage());
        }
    }

    // Updates an Officer
    public void updateOfficer(Officer o) {
        try (Connection conn = DBConn.getConnection()) {
            String sql = "UPDATE officer SET Fname=?, MI=?, Lname=?, Gender=?, Bdate=?, Phone_num=? WHERE OfficerID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, o.getFirstName());
            ps.setString(2, o.getMiddleInitial());
            ps.setString(3, o.getLastName());
            ps.setString(4, o.getGender());
            ps.setString(5, o.getBirthDate());
            ps.setString(6, o.getPhoneNumber());
            ps.setInt(7, o.getOfficerID());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Officer updated successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating officer: " + e.getMessage());
        }
    }

    // Deletes an Officer
    public void deleteOfficer(int officerID) {
        try (Connection conn = DBConn.getConnection()) {
            String sql = "DELETE FROM officer WHERE OfficerID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, officerID);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Officer deleted!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting officer: " + e.getMessage());
        }
    }

    // Retrieve List of All Officers
    public List<Officer> getAllOfficers() {
        List<Officer> list = new ArrayList<>();
        try (Connection conn = DBConn.getConnection()) {
            String sql = "SELECT * FROM officer";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Officer o = new Officer(
                    rs.getInt("OfficerID"),
                    rs.getString("Fname"),
                    rs.getString("MI"),
                    rs.getString("Lname"),
                    rs.getString("Gender"),
                    rs.getString("Phone_num"),
                    rs.getString("Bdate")
                );
                list.add(o);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching officers: " + e.getMessage());
        }
        return list;
    }
}
