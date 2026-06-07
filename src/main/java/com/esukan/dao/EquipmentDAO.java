package com.esukan.dao;

import com.esukan.model.Equipment;
import com.esukan.util.DatabaseUtil;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDAO {

    public List<Equipment> getAllEquipment() {
        List<Equipment> list = new ArrayList<>();
        String sql = "SELECT equipment_id, equipment_name, equipment_type, quantity_total, " +
                     "quantity_available, daily_rate, deposit_amount, condition_status, is_active " +
                     "FROM equipment ORDER BY equipment_name";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Equipment eq = new Equipment(
                    rs.getInt("equipment_id"),
                    rs.getString("equipment_name"),
                    rs.getString("equipment_type"),
                    rs.getInt("quantity_total"),
                    rs.getInt("quantity_available"),
                    rs.getBigDecimal("daily_rate"),
                    rs.getBigDecimal("deposit_amount"),
                    rs.getString("condition_status"),
                    rs.getBoolean("is_active")
                );
                list.add(eq);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean create(Equipment equipment) {
        String sql = "INSERT INTO equipment (equipment_name, equipment_type, quantity_total, quantity_available, " +
                     "daily_rate, deposit_amount, condition_status, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, equipment.getName());
            ps.setString(2, equipment.getType());
            ps.setInt(3, equipment.getQuantity());
            ps.setInt(4, equipment.getQuantityAvailable());
            ps.setBigDecimal(5, equipment.getDailyRate() == null ? BigDecimal.ZERO : equipment.getDailyRate());
            ps.setBigDecimal(6, equipment.getDeposit() == null ? BigDecimal.ZERO : equipment.getDeposit());
            ps.setString(7, equipment.getConditionStatus() == null ? "GOOD" : equipment.getConditionStatus());
            ps.setBoolean(8, equipment.isActive());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM equipment WHERE equipment_id = ?";
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
