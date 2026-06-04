
package com.esukan.dao;

import com.esukan.model.Equipment;
import com.esukan.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDAO {

    
    public List<Equipment> getAllEquipment() {
        List<Equipment> list = new ArrayList<>();
        String sql = "SELECT * FROM equipment";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                list.add(new Equipment(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getInt("quantity"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    
    public boolean addEquipment(Equipment eq) {
        String sql = "INSERT INTO equipment (name, type, quantity, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, eq.getName());
            ps.setString(2, eq.getType());
            ps.setInt(3, eq.getQuantity());
            ps.setString(4, eq.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean deleteEquipment(int id) {
        String sql = "DELETE FROM equipment WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}