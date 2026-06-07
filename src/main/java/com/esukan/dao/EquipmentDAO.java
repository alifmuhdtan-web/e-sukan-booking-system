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
    
    
    
    private static final String INSERT_EQUIPMENT_SQL = 
        "INSERT INTO equipment (equipment_name, equipment_type, facility_id, description, " +
        "quantity_total, quantity_available, daily_rate, deposit_amount, condition_status) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SELECT_ALL_EQUIPMENT_SQL = 
        "SELECT e.*, f.facility_name FROM equipment e " +
        "LEFT JOIN facilities f ON e.facility_id = f.facility_id " +
        "WHERE e.is_active = true ORDER BY e.equipment_name";
    
    private static final String SELECT_AVAILABLE_EQUIPMENT_SQL = 
        "SELECT e.*, f.facility_name FROM equipment e " +
        "LEFT JOIN facilities f ON e.facility_id = f.facility_id " +
        "WHERE e.is_active = true AND e.quantity_available > 0 ORDER BY e.equipment_name";
    
    private static final String SELECT_EQUIPMENT_BY_ID_SQL = 
        "SELECT e.*, f.facility_name FROM equipment e " +
        "LEFT JOIN facilities f ON e.facility_id = f.facility_id " +
        "WHERE e.equipment_id = ?";
    
    private static final String UPDATE_EQUIPMENT_SQL = 
        "UPDATE equipment SET equipment_name=?, equipment_type=?, facility_id=?, description=?, " +
        "quantity_total=?, quantity_available=?, daily_rate=?, deposit_amount=?, condition_status=? " +
        "WHERE equipment_id = ?";
    
    private static final String DELETE_EQUIPMENT_SQL = 
        "UPDATE equipment SET is_active = false WHERE equipment_id = ?";
    
    
    
    private static final String INSERT_RENTAL_SQL = 
        "INSERT INTO equipment_rental (user_id, equipment_id, quantity_rented, rental_date, " +
        "expected_return_date, total_amount, deposit_paid, rental_status, condition_on_rental) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SELECT_RENTALS_BY_USER_SQL = 
        "SELECT r.*, e.equipment_name, e.daily_rate, u.username " +
        "FROM equipment_rental r " +
        "JOIN equipment e ON r.equipment_id = e.equipment_id " +
        "JOIN users u ON r.user_id = u.user_id " +
        "WHERE r.user_id = ? ORDER BY r.rental_date DESC";
    
    private static final String RETURN_EQUIPMENT_SQL = 
        "UPDATE equipment_rental SET actual_return_date = ?, condition_on_return = ?, " +
        "rental_status = 'RETURNED' WHERE rental_id = ? AND user_id = ?";
    
    
    
    private Equipment mapEquipment(ResultSet rs) throws SQLException {
        Equipment e = new Equipment();
        e.setEquipmentId(rs.getInt("equipment_id"));
        e.setEquipmentName(rs.getString("equipment_name"));
        e.setEquipmentType(Equipment.EquipmentType.valueOf(rs.getString("equipment_type")));
        e.setFacilityId(rs.getInt("facility_id"));
        e.setDescription(rs.getString("description"));
        e.setQuantityTotal(rs.getInt("quantity_total"));
        e.setQuantityAvailable(rs.getInt("quantity_available"));
        e.setDailyRate(rs.getBigDecimal("daily_rate"));
        e.setDepositAmount(rs.getBigDecimal("deposit_amount"));
        e.setConditionStatus(Equipment.ConditionStatus.valueOf(rs.getString("condition_status")));
        e.setActive(rs.getBoolean("is_active"));
        try { e.setFacilityName(rs.getString("facility_name")); } catch(Exception ex) {}
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
        try { r.setEquipmentName(rs.getString("equipment_name")); } catch(Exception ex) {}
        try { r.setUsername(rs.getString("username")); } catch(Exception ex) {}
        try { r.setDailyRate(rs.getBigDecimal("daily_rate")); } catch(Exception ex) {}
        return r;
    }
    
    
    
    public List<Equipment> findAll() throws SQLException {
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
    
    public Optional<Equipment> findById(int id) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_EQUIPMENT_BY_ID_SQL)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapEquipment(rs));
        }
        return Optional.empty();
    }
    
    public Equipment createEquipment(Equipment e) throws SQLException {
        String[] generated = {"equipment_id"};
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_EQUIPMENT_SQL, generated)) {
            ps.setString(1, e.getEquipmentName());
            ps.setString(2, e.getEquipmentType().toString());
            ps.setObject(3, e.getFacilityId() > 0 ? e.getFacilityId() : null);
            ps.setString(4, e.getDescription());
            ps.setInt(5, e.getQuantityTotal());
            ps.setInt(6, e.getQuantityAvailable());
            ps.setBigDecimal(7, e.getDailyRate());
            ps.setBigDecimal(8, e.getDepositAmount());
            ps.setString(9, e.getConditionStatus().toString());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) e.setEquipmentId(rs.getInt(1));
            }
        }
        return e;
    }
    
    public boolean updateEquipment(Equipment e) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_EQUIPMENT_SQL)) {
            ps.setString(1, e.getEquipmentName());
            ps.setString(2, e.getEquipmentType().toString());
            ps.setObject(3, e.getFacilityId() > 0 ? e.getFacilityId() : null);
            ps.setString(4, e.getDescription());
            ps.setInt(5, e.getQuantityTotal());
            ps.setInt(6, e.getQuantityAvailable());
            ps.setBigDecimal(7, e.getDailyRate());
            ps.setBigDecimal(8, e.getDepositAmount());
            ps.setString(9, e.getConditionStatus().toString());
            ps.setInt(10, e.getEquipmentId());
            return ps.executeUpdate() > 0;
        }
    }
    
    public boolean deleteEquipment(int id) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_EQUIPMENT_SQL)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
    
    
    
    public EquipmentRental createRental(EquipmentRental rental) throws SQLException {
        String[] generated = {"rental_id"};
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_RENTAL_SQL, generated)) {
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
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) rental.setRentalId(rs.getInt(1));
            }
        }
        return rental;
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
    
    public boolean returnEquipment(int rentalId, int userId, String conditionOnReturn) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(RETURN_EQUIPMENT_SQL)) {
            ps.setDate(1, new Date(System.currentTimeMillis()));
            ps.setString(2, conditionOnReturn);
            ps.setInt(3, rentalId);
            ps.setInt(4, userId);
            return ps.executeUpdate() > 0;
        }
    }
}