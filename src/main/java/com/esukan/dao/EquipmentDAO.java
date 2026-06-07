package com.esukan.dao;

import com.esukan.model.Equipment;
import com.esukan.model.EquipmentRental;
import com.esukan.util.DatabaseUtil;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EquipmentDAO {
    
    // ==================== Equipment Queries ====================
    
    private static final String INSERT_EQUIPMENT_SQL = 
        "INSERT INTO equipment (equipment_name, equipment_type, quantity_total, quantity_available, daily_rate, deposit_amount, condition_status, is_active) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SELECT_ALL_EQUIPMENT_SQL = 
        "SELECT * FROM equipment WHERE is_active = true ORDER BY equipment_name";
    
    private static final String SELECT_AVAILABLE_EQUIPMENT_SQL = 
        "SELECT * FROM equipment WHERE is_active = true AND quantity_available > 0 ORDER BY equipment_name";
    
    private static final String SELECT_EQUIPMENT_BY_ID_SQL = 
        "SELECT * FROM equipment WHERE equipment_id = ? AND is_active = true";
    
    private static final String UPDATE_EQUIPMENT_SQL = 
        "UPDATE equipment SET equipment_name=?, equipment_type=?, quantity_total=?, quantity_available=?, daily_rate=?, deposit_amount=?, condition_status=?, is_active=? WHERE equipment_id = ?";
    
    private static final String DELETE_EQUIPMENT_SQL = 
        "UPDATE equipment SET is_active = false WHERE equipment_id = ?";
    
    // ==================== Rental Queries ====================
    
    private static final String INSERT_RENTAL_SQL = 
        "INSERT INTO equipment_rental (user_id, equipment_id, quantity_rented, rental_date, expected_return_date, total_amount, deposit_paid, rental_status, condition_on_rental) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SELECT_RENTALS_BY_USER_SQL = 
        "SELECT r.*, e.equipment_name, e.daily_rate FROM equipment_rental r " +
        "JOIN equipment e ON r.equipment_id = e.equipment_id " +
        "WHERE r.user_id = ? ORDER BY r.rental_date DESC";
    
    private Equipment mapEquipment(ResultSet rs) throws SQLException {
        Equipment e = new Equipment();
        e.setEquipmentId(rs.getInt("equipment_id"));
        e.setEquipmentName(rs.getString("equipment_name"));
        e.setEquipmentType(Equipment.EquipmentType.valueOf(rs.getString("equipment_type")));
        e.setQuantityTotal(rs.getInt("quantity_total"));
        e.setQuantityAvailable(rs.getInt("quantity_available"));
        e.setDailyRate(rs.getBigDecimal("daily_rate"));
        e.setDepositAmount(rs.getBigDecimal("deposit_amount"));
        e.setConditionStatus(Equipment.ConditionStatus.valueOf(rs.getString("condition_status")));
        e.setActive(rs.getBoolean("is_active"));
        return e;
    }
    
    private EquipmentRental mapRental(ResultSet rs) throws SQLException {
        EquipmentRental r = new EquipmentRental();
        r.setRentalId(rs.getInt("rental_id"));
        r.setUserId(rs.getInt("user_id"));
        r.setEquipmentId(rs.getInt("equipment_id"));
        r.setQuantityRented(rs.getInt("quantity_rented"));
        r.setRentalDate(rs.getTimestamp("rental_date"));
        r.setExpectedReturnDate(rs.getDate("expected_return_date"));
        r.setActualReturnDate(rs.getDate("actual_return_date"));
        r.setTotalAmount(rs.getBigDecimal("total_amount"));
        r.setDepositPaid(rs.getBigDecimal("deposit_paid"));
        r.setDepositRefunded(rs.getBoolean("deposit_refunded"));
        r.setRentalStatus(EquipmentRental.RentalStatus.valueOf(rs.getString("rental_status")));
        r.setConditionOnRental(rs.getString("condition_on_rental"));
        r.setConditionOnReturn(rs.getString("condition_on_return"));
        r.setNotes(rs.getString("notes"));
        try { r.setEquipmentName(rs.getString("equipment_name")); } catch(Exception e) {}
        try { r.setDailyRate(rs.getBigDecimal("daily_rate")); } catch(Exception e) {}
        return r;
    }
    
    // ==================== Equipment CRUD ====================
    
    public List<Equipment> getAllEquipment() throws SQLException {
        List<Equipment> list = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_EQUIPMENT_SQL)) {
            while (rs.next()) list.add(mapEquipment(rs));
        }
        return list;
    }
    
    public List<Equipment> findAllAvailable() throws SQLException {
        List<Equipment> list = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_AVAILABLE_EQUIPMENT_SQL)) {
            while (rs.next()) list.add(mapEquipment(rs));
        }
        return list;
    }
    
    public Optional<Equipment> getEquipmentById(int id) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_EQUIPMENT_BY_ID_SQL)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapEquipment(rs));
        }
        return Optional.empty();
    }
    
    public void addEquipment(Equipment e) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_EQUIPMENT_SQL)) {
            ps.setString(1, e.getEquipmentName());
            ps.setString(2, e.getEquipmentType().toString());
            ps.setInt(3, e.getQuantityTotal());
            ps.setInt(4, e.getQuantityAvailable());
            ps.setBigDecimal(5, e.getDailyRate());
            ps.setBigDecimal(6, e.getDepositAmount());
            ps.setString(7, e.getConditionStatus().toString());
            ps.setBoolean(8, true);
            ps.executeUpdate();
        }
    }
    
    public void createEquipment(Equipment e) throws SQLException {
        addEquipment(e);
    }
    
    public void updateEquipment(Equipment e) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_EQUIPMENT_SQL)) {
            ps.setString(1, e.getEquipmentName());
            ps.setString(2, e.getEquipmentType().toString());
            ps.setInt(3, e.getQuantityTotal());
            ps.setInt(4, e.getQuantityAvailable());
            ps.setBigDecimal(5, e.getDailyRate());
            ps.setBigDecimal(6, e.getDepositAmount());
            ps.setString(7, e.getConditionStatus().toString());
            ps.setBoolean(8, e.isActive());
            ps.setInt(9, e.getEquipmentId());
            ps.executeUpdate();
        }
    }
    
    public void deleteEquipment(int id) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_EQUIPMENT_SQL)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    // ==================== Rental CRUD ====================
    
    public void createRental(EquipmentRental rental) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_RENTAL_SQL)) {
            ps.setInt(1, rental.getUserId());
            ps.setInt(2, rental.getEquipmentId());
            ps.setInt(3, rental.getQuantityRented());
            ps.setTimestamp(4, rental.getRentalDate());
            ps.setDate(5, rental.getExpectedReturnDate());
            ps.setBigDecimal(6, rental.getTotalAmount());
            ps.setBigDecimal(7, rental.getDepositPaid());
            ps.setString(8, rental.getRentalStatus().toString());
            ps.setString(9, rental.getConditionOnRental());
            ps.executeUpdate();
        }
    }
    
    public List<EquipmentRental> findRentalsByUser(int userId) throws SQLException {
        List<EquipmentRental> list = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_RENTALS_BY_USER_SQL)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRental(rs));
        }
        return list;
    }
}