/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Elected_To;

/**
 *
 * @author mariane
 */
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.*;
import DataBaseConnection.DBConn;

public class Elected_ToDAO {

    // CREATE
    public void addElectedTo(Elected_To e) {
        String sql = "INSERT INTO elected_to (OfficerID, PositionID, Department, Date_From, Date_To) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, e.getOfficerID());
            ps.setInt(2, e.getPositionID());
            ps.setString(3, e.getDepartment());
            ps.setString(4, e.getDateFrom());
            ps.setString(5, e.getDateTo());
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Election record saved successfully!");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error saving election record: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // UPDATE ELECTED_TO RECORD
    public void updateElectedTo(Elected_To e) {
        String sql = "UPDATE elected_to SET OfficerID = ?, PositionID = ?, Department = ?, Date_From = ?, Date_To = ? WHERE electID = ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, e.getOfficerID());
            ps.setInt(2, e.getPositionID());
            ps.setString(3, e.getDepartment());
            ps.setString(4, e.getDateFrom());
            ps.setString(5, e.getDateTo());
            ps.setInt(6, e.getElectID());
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Election record updated successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No record found with the given electID!", 
                                            "Not Found", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error updating election record: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // DELETES THE ELECTED_TO RECORD using electID
    public void deleteElectedTo(int electID) {
        String sql = "DELETE FROM elected_to WHERE electID = ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, electID);
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Elected_To record deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No record found with the given electID!", 
                                            "Not Found", JOptionPane.WARNING_MESSAGE);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting Elected_To record: " + e.getMessage());
        }
    }

    // READ ALL ELECTED_TO RECORDS 
    public List<String[]> getAllElectedTo() {
        List<String[]> list = new ArrayList<>();
        String sql = """
            SELECT e.electID, 
                   CONCAT(o.Fname, ' ', o.MI, ' ', o.Lname) AS officerName,
                   p.Name AS positionName,
                   e.Department, e.Date_From, e.Date_To
            FROM elected_to e
            JOIN officer o ON e.OfficerID = o.OfficerID
            JOIN position p ON e.PositionID = p.PositionID
        """;
        try (Connection conn = DBConn.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new String[]{
                    String.valueOf(rs.getInt("electID")),
                    rs.getString("officerName"),
                    rs.getString("positionName"),
                    rs.getString("Department"),
                    rs.getString("Date_From"),
                    rs.getString("Date_To")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching elected_to records: " + ex.getMessage());
        }
        
        return list;
    }
}