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
        "INSERT INTO equipment (equipment_name, equipment_type, facility_id, description, " +
        "quantity_total, quantity_available, daily_rate, deposit_amount, condition_status, is_active) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
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
        "WHERE e.equipment_id = ? AND e.is_active = true";
    
    private static final String UPDATE_EQUIPMENT_SQL = 
        "UPDATE equipment SET equipment_name=?, equipment_type=?, facility_id=?, description=?, " +
        "quantity_total=?, quantity_available=?, daily_rate=?, deposit_amount=?, condition_status=? " +
        "WHERE equipment_id = ?";
    
    private static final String DELETE_EQUIPMENT_SQL = 
        "UPDATE equipment SET is_active = false WHERE equipment_id = ?";
    
    private static final String UPDATE_QUANTITY_SQL = 
        "UPDATE equipment SET quantity_available = quantity_available + ? WHERE equipment_id = ?";
    
    // ==================== Rental Queries ====================
    
    private static final String INSERT_RENTAL_SQL = 
        "INSERT INTO equipment_rental (user_id, equipment_id, quantity_rented, rental_date, " +
        "expected_return_date, total_amount, deposit_paid, rental_status, condition_on_rental, notes) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SELECT_RENTALS_BY_USER_SQL = 
        "SELECT r.*, e.equipment_name, e.daily_rate, u.username FROM equipment_rental r " +
        "JOIN equipment e ON r.equipment_id = e.equipment_id " +
        "JOIN users u ON r.user_id = u.user_id " +
        "WHERE r.user_id = ? ORDER BY r.rental_date DESC";
    
    private static final String SELECT_ACTIVE_RENTALS_SQL = 
        "SELECT r.*, e.equipment_name, e.daily_rate, u.username FROM equipment_rental r " +
        "JOIN equipment e ON r.equipment_id = e.equipment_id " +
        "JOIN users u ON r.user_id = u.user_id " +
        "WHERE r.rental_status = 'ACTIVE' ORDER BY r.expected_return_date";
    
    private static final String SELECT_RENTAL_BY_ID_SQL = 
        "SELECT r.*, e.equipment_name, e.daily_rate, u.username FROM equipment_rental r " +
        "JOIN equipment e ON r.equipment_id = e.equipment_id " +
        "JOIN users u ON r.user_id = u.user_id " +
        "WHERE r.rental_id = ?";
    
    private static final String RETURN_EQUIPMENT_SQL = 
        "UPDATE equipment_rental SET actual_return_date = ?, condition_on_return = ?, " +
        "rental_status = 'RETURNED' WHERE rental_id = ? AND user_id = ?";
    
    private static final String COUNT_ACTIVE_RENTALS_BY_USER_SQL = 
        "SELECT COUNT(*) FROM equipment_rental WHERE user_id = ? AND rental_status = 'ACTIVE'";
    
    // ==================== Mapping Methods ====================
    
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
    
    // ==================== Equipment CRUD Methods ====================
    
    public List<Equipment> getAllEquipment() throws SQLException {
        List<Equipment> list = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_EQUIPMENT_SQL)) {
            while (rs.next()) list.add(mapEquipment(rs));
        }
        return list;
    }
    
    public List<Equipment> findAll() throws SQLException {
        return getAllEquipment();
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
    
    public Optional<Equipment> getEquipmentById(int id) throws SQLException {
        return findById(id);
    }
    
    public void addEquipment(Equipment e) throws SQLException {
        createEquipment(e);
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
            ps.setBoolean(10, true);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) e.setEquipmentId(rs.getInt(1));
            }
        }
        return e;
    }
    
    public void updateEquipment(Equipment e) throws SQLException {
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
    
    public boolean updateQuantity(int equipmentId, int quantityChange) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_QUANTITY_SQL)) {
            ps.setInt(1, quantityChange);
            ps.setInt(2, equipmentId);
            return ps.executeUpdate() > 0;
        }
    }
    
    // ==================== Rental CRUD Methods ====================
    
    public EquipmentRental createRental(EquipmentRental rental) throws SQLException {
        // First, update equipment quantity
        updateQuantity(rental.getEquipmentId(), -rental.getQuantityRented());
        
        // Then create rental record
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
            ps.setString(10, rental.getNotes());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) rental.setRentalId(rs.getInt(1));
            }
        }
        return rental;
    }
    
    public List<EquipmentRental> getRentalsByUser(int userId) throws SQLException {
        return findRentalsByUser(userId);
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
    
    public List<EquipmentRental> findAllActiveRentals() throws SQLException {
        List<EquipmentRental> list = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ACTIVE_RENTALS_SQL)) {
            while (rs.next()) list.add(mapRental(rs));
        }
        return list;
    }
    
    public Optional<EquipmentRental> findRentalById(int rentalId) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_RENTAL_BY_ID_SQL)) {
            ps.setInt(1, rentalId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapRental(rs));
        }
        return Optional.empty();
    }
    
    public boolean returnEquipment(int rentalId, int userId, String conditionOnReturn, boolean refundDeposit) throws SQLException {
        // Get rental to know quantity
        Optional<EquipmentRental> rentalOpt = findRentalById(rentalId);
        if (!rentalOpt.isPresent()) {
            return false;
        }
        EquipmentRental rental = rentalOpt.get();
        
        // Update equipment quantity (add back the rented quantity)
        updateQuantity(rental.getEquipmentId(), rental.getQuantityRented());
        
        // Update rental record
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(RETURN_EQUIPMENT_SQL)) {
            ps.setDate(1, new Date(System.currentTimeMillis()));
            ps.setString(2, conditionOnReturn);
            ps.setInt(3, rentalId);
            ps.setInt(4, userId);
            ps.executeUpdate();
        }
        
        // Update deposit refund if needed
        if (refundDeposit) {
            try (Connection conn = DatabaseUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement("UPDATE equipment_rental SET deposit_refunded = true WHERE rental_id = ?")) {
                ps.setInt(1, rentalId);
                ps.executeUpdate();
            }
        }
        
        return true;
    }
    
    public int countActiveRentalsByUser(int userId) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(COUNT_ACTIVE_RENTALS_BY_USER_SQL)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}