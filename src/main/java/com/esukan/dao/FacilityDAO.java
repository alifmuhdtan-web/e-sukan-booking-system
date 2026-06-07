package com.esukan.dao;

import com.esukan.model.Facility;
import com.esukan.util.DatabaseUtil;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FacilityDAO {
    
    
    private static final String INSERT_SQL = 
        "INSERT INTO facilities (facility_name, facility_type, description, location, " +
        "capacity, hourly_rate, image_url, opening_time, closing_time, is_available) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SELECT_BY_ID_SQL = 
        "SELECT * FROM facilities WHERE facility_id = ?";
    
    private static final String SELECT_ALL_SQL = 
        "SELECT * FROM facilities ORDER BY facility_id";
    
    private static final String SELECT_AVAILABLE_SQL = 
        "SELECT * FROM facilities WHERE is_available = true " +
        "AND (maintenance_start_date IS NULL OR maintenance_start_date > CURDATE()) " +
        "ORDER BY facility_id";
    
    private static final String SELECT_BY_TYPE_SQL = 
        "SELECT * FROM facilities WHERE facility_type = ? AND is_available = true " +
        "ORDER BY facility_id";
    
    private static final String UPDATE_SQL = 
        "UPDATE facilities SET facility_name=?, facility_type=?, description=?, location=?, " +
        "capacity=?, hourly_rate=?, image_url=?, opening_time=?, closing_time=?, " +
        "is_available=?, maintenance_start_date=?, maintenance_end_date=? " +
        "WHERE facility_id = ?";
    
    private static final String DELETE_SQL = 
        "DELETE FROM facilities WHERE facility_id = ?";
    
    private static final String SEARCH_BY_NAME_SQL = 
        "SELECT * FROM facilities WHERE facility_name LIKE ? AND is_available = true ORDER BY facility_id";
    
    
    private Facility mapResultSetToFacility(ResultSet rs) throws SQLException {
        Facility facility = new Facility();
        facility.setFacilityId(rs.getInt("facility_id"));
        facility.setFacilityName(rs.getString("facility_name"));
        facility.setFacilityType(Facility.FacilityType.valueOf(rs.getString("facility_type")));
        facility.setDescription(rs.getString("description"));
        facility.setLocation(rs.getString("location"));
        facility.setCapacity(rs.getInt("capacity"));
        facility.setHourlyRate(rs.getBigDecimal("hourly_rate"));
        facility.setImageUrl(rs.getString("image_url"));
        facility.setOpeningTime(rs.getTime("opening_time"));
        facility.setClosingTime(rs.getTime("closing_time"));
        facility.setAvailable(rs.getBoolean("is_available"));
        facility.setMaintenanceStartDate(rs.getTimestamp("maintenance_start_date"));
        facility.setMaintenanceEndDate(rs.getTimestamp("maintenance_end_date"));
        facility.setCreatedAt(rs.getTimestamp("created_at"));
        facility.setUpdatedAt(rs.getTimestamp("updated_at"));
        return facility;
    }
    
    
    public Facility create(Facility facility) throws SQLException {
        String generatedColumns[] = {"facility_id"};
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL, generatedColumns)) {
            
            pstmt.setString(1, facility.getFacilityName());
            pstmt.setString(2, facility.getFacilityType().toString());
            pstmt.setString(3, facility.getDescription());
            pstmt.setString(4, facility.getLocation());
            pstmt.setInt(5, facility.getCapacity());
            pstmt.setBigDecimal(6, facility.getHourlyRate());
            pstmt.setString(7, facility.getImageUrl());
            pstmt.setTime(8, facility.getOpeningTime());
            pstmt.setTime(9, facility.getClosingTime());
            pstmt.setBoolean(10, facility.isAvailable());
            
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    facility.setFacilityId(rs.getInt(1));
                }
            }
            return facility;
        }
    }
    
    
    public Optional<Facility> findById(int id) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToFacility(rs));
            }
        }
        return Optional.empty();
    }
    
    
    public List<Facility> findAll() throws SQLException {
        List<Facility> facilities = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL)) {
            
            while (rs.next()) {
                facilities.add(mapResultSetToFacility(rs));
            }
        }
        return facilities;
    }
    
    
    public List<Facility> findAvailableFacilities() throws SQLException {
        List<Facility> facilities = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_AVAILABLE_SQL)) {
            
            while (rs.next()) {
                facilities.add(mapResultSetToFacility(rs));
            }
        }
        return facilities;
    }
    
    
    public List<Facility> findByType(Facility.FacilityType type) throws SQLException {
        List<Facility> facilities = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_TYPE_SQL)) {
            
            pstmt.setString(1, type.toString());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                facilities.add(mapResultSetToFacility(rs));
            }
        }
        return facilities;
    }
    
    
    public List<Facility> searchByName(String keyword) throws SQLException {
        List<Facility> facilities = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SEARCH_BY_NAME_SQL)) {
            
            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                facilities.add(mapResultSetToFacility(rs));
            }
        }
        return facilities;
    }
    
    
    public boolean update(Facility facility) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_SQL)) {
            
            pstmt.setString(1, facility.getFacilityName());
            pstmt.setString(2, facility.getFacilityType().toString());
            pstmt.setString(3, facility.getDescription());
            pstmt.setString(4, facility.getLocation());
            pstmt.setInt(5, facility.getCapacity());
            pstmt.setBigDecimal(6, facility.getHourlyRate());
            pstmt.setString(7, facility.getImageUrl());
            pstmt.setTime(8, facility.getOpeningTime());
            pstmt.setTime(9, facility.getClosingTime());
            pstmt.setBoolean(10, facility.isAvailable());
            pstmt.setTimestamp(11, facility.getMaintenanceStartDate());
            pstmt.setTimestamp(12, facility.getMaintenanceEndDate());
            pstmt.setInt(13, facility.getFacilityId());
            
            return pstmt.executeUpdate() > 0;
        }
    }
    
    
    public boolean delete(int id) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_SQL)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }
    
    
    public int count() throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM facilities")) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}