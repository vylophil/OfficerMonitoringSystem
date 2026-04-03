/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Position;

import java.sql.*;
import java.util.*;
import javax.swing.*;
import DataBaseConnection.DBConn;

public class PositionDAO {

    // CREATES THE POSITION
    public void addPosition(Position pos) {
        String sql = "INSERT INTO position (name, description) VALUES (?, ?)";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, pos.getName());
            pstmt.setString(2, pos.getDescription());
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Position added successfully!");
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding position: " + e.getMessage());
        }
    }

    // READ THE POSITION
    public List<Position> getAllPositions() {
        List<Position> list = new ArrayList<>();
        String sql = "SELECT * FROM position";
        try (Connection conn = DBConn.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Position pos = new Position();
                pos.setPositionID(rs.getInt("positionID"));
                pos.setName(rs.getString("name"));
                pos.setDescription(rs.getString("description"));
                list.add(pos);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching positions: " + e.getMessage());
        }
        return list;
    }

    // GET POSITION BY ID
    public Position getPositionById(int positionID) {
        String sql = "SELECT * FROM position WHERE positionID = ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, positionID);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Position pos = new Position();
                pos.setPositionID(rs.getInt("positionID"));
                pos.setName(rs.getString("name"));
                pos.setDescription(rs.getString("description"));
                return pos;
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching position: " + e.getMessage());
        }
        return null;
    }

    // UPDATES THE POSITION
    public void updatePosition(Position pos) {
        String sql = "UPDATE position SET name = ?, description = ? WHERE positionID = ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, pos.getName());
            pstmt.setString(2, pos.getDescription());
            pstmt.setInt(3, pos.getPositionID());
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Position updated successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No position found with the given ID!", 
                                            "Not Found", JOptionPane.WARNING_MESSAGE);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating position: " + e.getMessage());
        }
    }

    public void deletePosition(int positionID) {
        String sql = "DELETE FROM position WHERE positionID = ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, positionID);
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Position deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No position found with the given ID!", 
                                            "Not Found", JOptionPane.WARNING_MESSAGE);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting position: " + e.getMessage());
        }
    }
}