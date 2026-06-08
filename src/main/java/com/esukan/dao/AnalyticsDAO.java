package com.esukan.dao;

import com.esukan.util.DatabaseUtil;
import java.sql.*;
import java.util.*;

public class AnalyticsDAO {
    
    // ==================== Dashboard Statistics ====================
    
    public Map<String, Object> getDashboardStats() throws SQLException {
        Map<String, Object> stats = new HashMap<>();
        
        String sql = "SELECT " +
            "(SELECT COUNT(*) FROM users WHERE user_role = 'STUDENT' AND is_active = true) as total_students, " +
            "(SELECT COUNT(*) FROM facilities WHERE is_available = true) as total_facilities, " +
            "(SELECT COUNT(*) FROM equipment WHERE is_active = true) as total_equipment, " +
            "(SELECT COUNT(*) FROM bookings WHERE booking_status = 'CONFIRMED' AND booking_date >= CURDATE()) as upcoming_bookings, " +
            "(SELECT COUNT(*) FROM equipment_rental WHERE rental_status = 'ACTIVE') as active_rentals, " +
            "(SELECT COALESCE(SUM(total_cost), 0) FROM bookings WHERE MONTH(created_at) = MONTH(CURDATE()) AND YEAR(created_at) = YEAR(CURDATE())) as monthly_revenue";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                stats.put("totalStudents", rs.getInt("total_students"));
                stats.put("totalFacilities", rs.getInt("total_facilities"));
                stats.put("totalEquipment", rs.getInt("total_equipment"));
                stats.put("upcomingBookings", rs.getInt("upcoming_bookings"));
                stats.put("activeRentals", rs.getInt("active_rentals"));
                stats.put("monthlyRevenue", rs.getDouble("monthly_revenue"));
            }
        }
        return stats;
    }
    
    // ==================== Peak Usage Hours ====================
    
    public List<Map<String, Object>> getPeakUsageHours() throws SQLException {
        List<Map<String, Object>> peakHours = new ArrayList<>();
        
        String sql = "SELECT " +
                     "HOUR(start_time) as hour, " +
                     "COUNT(*) as booking_count, " +
                     "DAYNAME(booking_date) as day_of_week " +
                     "FROM bookings " +
                     "WHERE booking_status = 'CONFIRMED' " +
                     "AND booking_date >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
                     "GROUP BY HOUR(start_time), DAYNAME(booking_date) " +
                     "ORDER BY booking_count DESC " +
                     "LIMIT 10";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> hour = new HashMap<>();
                hour.put("hour", rs.getInt("hour"));
                hour.put("bookingCount", rs.getInt("booking_count"));
                hour.put("dayOfWeek", rs.getString("day_of_week"));
                peakHours.add(hour);
            }
        }
        return peakHours;
    }
    
    // ==================== Popular Facilities ====================
    
    public List<Map<String, Object>> getPopularFacilities() throws SQLException {
        List<Map<String, Object>> popular = new ArrayList<>();
        
        String sql = "SELECT " +
                     "f.facility_id, " +
                     "f.facility_name, " +
                     "f.facility_type, " +
                     "COUNT(b.booking_id) as total_bookings, " +
                     "COALESCE(SUM(b.total_cost), 0) as total_revenue " +
                     "FROM facilities f " +
                     "LEFT JOIN bookings b ON f.facility_id = b.facility_id " +
                     "AND b.booking_status = 'CONFIRMED' " +
                     "AND b.created_at >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
                     "GROUP BY f.facility_id " +
                     "ORDER BY total_bookings DESC " +
                     "LIMIT 5";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> facility = new HashMap<>();
                facility.put("facilityId", rs.getInt("facility_id"));
                facility.put("facilityName", rs.getString("facility_name"));
                facility.put("facilityType", rs.getString("facility_type"));
                facility.put("totalBookings", rs.getInt("total_bookings"));
                facility.put("totalRevenue", rs.getDouble("total_revenue"));
                popular.add(facility);
            }
        }
        return popular;
    }
    
    // ==================== Equipment Utilization ====================
    
    public List<Map<String, Object>> getEquipmentUtilization() throws SQLException {
        List<Map<String, Object>> utilization = new ArrayList<>();
        
        String sql = "SELECT " +
                     "e.equipment_id, " +
                     "e.equipment_name, " +
                     "e.equipment_type, " +
                     "e.quantity_total, " +
                     "e.quantity_available, " +
                     "(e.quantity_total - e.quantity_available) as rented_count, " +
                     "ROUND(((e.quantity_total - e.quantity_available) / NULLIF(e.quantity_total, 0)) * 100, 2) as utilization_rate " +
                     "FROM equipment e " +
                     "WHERE e.is_active = true AND e.quantity_total > 0 " +
                     "ORDER BY utilization_rate DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> equip = new HashMap<>();
                equip.put("equipmentId", rs.getInt("equipment_id"));
                equip.put("equipmentName", rs.getString("equipment_name"));
                equip.put("equipmentType", rs.getString("equipment_type"));
                equip.put("quantityTotal", rs.getInt("quantity_total"));
                equip.put("quantityAvailable", rs.getInt("quantity_available"));
                equip.put("rentedCount", rs.getInt("rented_count"));
                equip.put("utilizationRate", rs.getDouble("utilization_rate"));
                utilization.add(equip);
            }
        }
        return utilization;
    }
    
    // ==================== Maintenance Alerts ====================
    
    public List<Map<String, Object>> getEquipmentMaintenanceAlerts() throws SQLException {
        List<Map<String, Object>> alerts = new ArrayList<>();
        
        String sql = "SELECT " +
                     "equipment_id, " +
                     "equipment_name, " +
                     "equipment_type, " +
                     "condition_status, " +
                     "last_maintenance_date, " +
                     "next_maintenance_date, " +
                     "DATEDIFF(COALESCE(next_maintenance_date, CURDATE()), CURDATE()) as days_until_maintenance " +
                     "FROM equipment " +
                     "WHERE is_active = true " +
                     "AND (condition_status IN ('POOR', 'DAMAGED') " +
                     "OR next_maintenance_date <= DATE_ADD(CURDATE(), INTERVAL 7 DAY)) " +
                     "ORDER BY days_until_maintenance ASC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> alert = new HashMap<>();
                alert.put("equipmentId", rs.getInt("equipment_id"));
                alert.put("equipmentName", rs.getString("equipment_name"));
                alert.put("equipmentType", rs.getString("equipment_type"));
                alert.put("conditionStatus", rs.getString("condition_status"));
                alert.put("lastMaintenanceDate", rs.getDate("last_maintenance_date"));
                alert.put("nextMaintenanceDate", rs.getDate("next_maintenance_date"));
                alert.put("daysUntilMaintenance", rs.getInt("days_until_maintenance"));
                alerts.add(alert);
            }
        }
        return alerts;
    }
    
    // ==================== Weekly Booking Trend ====================
    
    public List<Map<String, Object>> getWeeklyBookingTrend() throws SQLException {
        List<Map<String, Object>> trend = new ArrayList<>();
        
        String sql = "SELECT " +
                     "DATE(booking_date) as date, " +
                     "COUNT(*) as bookings_count, " +
                     "COALESCE(SUM(total_cost), 0) as revenue " +
                     "FROM bookings " +
                     "WHERE booking_date >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
                     "AND booking_status = 'CONFIRMED' " +
                     "GROUP BY DATE(booking_date) " +
                     "ORDER BY date ASC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> day = new HashMap<>();
                day.put("date", rs.getDate("date"));
                day.put("bookingsCount", rs.getInt("bookings_count"));
                day.put("revenue", rs.getDouble("revenue"));
                trend.add(day);
            }
        }
        return trend;
    }
    
    // ==================== Monthly Booking Trend ====================
    
    public List<Map<String, Object>> getMonthlyBookingTrend() throws SQLException {
        List<Map<String, Object>> trend = new ArrayList<>();
        
        String sql = "SELECT " +
                     "DATE_FORMAT(booking_date, '%Y-%m') as month, " +
                     "COUNT(*) as bookings_count, " +
                     "COALESCE(SUM(total_cost), 0) as revenue " +
                     "FROM bookings " +
                     "WHERE booking_date >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH) " +
                     "AND booking_status = 'CONFIRMED' " +
                     "GROUP BY DATE_FORMAT(booking_date, '%Y-%m') " +
                     "ORDER BY month ASC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> month = new HashMap<>();
                month.put("month", rs.getString("month"));
                month.put("bookingsCount", rs.getInt("bookings_count"));
                month.put("revenue", rs.getDouble("revenue"));
                trend.add(month);
            }
        }
        return trend;
    }
    
    // ==================== Recent Activities ====================
    
    public List<Map<String, Object>> getRecentActivities(int limit) throws SQLException {
        List<Map<String, Object>> activities = new ArrayList<>();
        
        String sql = "SELECT * FROM (" +
                     "SELECT " +
                     "'booking' as type, " +
                     "b.booking_id as id, " +
                     "b.booking_reference as reference, " +
                     "b.created_at as activity_date, " +
                     "u.username, " +
                     "f.facility_name as name, " +
                     "b.booking_status as status " +
                     "FROM bookings b " +
                     "JOIN users u ON b.user_id = u.user_id " +
                     "JOIN facilities f ON b.facility_id = f.facility_id " +
                     "UNION ALL " +
                     "SELECT " +
                     "'rental' as type, " +
                     "r.rental_id as id, " +
                     "NULL as reference, " +
                     "r.created_at as activity_date, " +
                     "u.username, " +
                     "e.equipment_name as name, " +
                     "r.rental_status as status " +
                     "FROM equipment_rental r " +
                     "JOIN users u ON r.user_id = u.user_id " +
                     "JOIN equipment e ON r.equipment_id = e.equipment_id " +
                     ") AS activities " +
                     "WHERE activity_date IS NOT NULL " +
                     "ORDER BY activity_date DESC " +
                     "LIMIT ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> activity = new HashMap<>();
                activity.put("type", rs.getString("type"));
                activity.put("id", rs.getInt("id"));
                activity.put("reference", rs.getString("reference"));
                
                Timestamp ts = rs.getTimestamp("activity_date");
                activity.put("activityDate", ts != null ? ts.toString() : "N/A");
                
                activity.put("username", rs.getString("username"));
                activity.put("name", rs.getString("name"));
                activity.put("status", rs.getString("status"));
                activities.add(activity);
            }
        }
        return activities;
    }
    
    // ==================== Booking by Facility Type ====================
    
    public List<Map<String, Object>> getBookingsByFacilityType() throws SQLException {
        List<Map<String, Object>> distribution = new ArrayList<>();
        
        String sql = "SELECT " +
                     "f.facility_type, " +
                     "COUNT(b.booking_id) as booking_count, " +
                     "COALESCE(SUM(b.total_cost), 0) as total_revenue " +
                     "FROM facilities f " +
                     "LEFT JOIN bookings b ON f.facility_id = b.facility_id " +
                     "AND b.booking_status = 'CONFIRMED' " +
                     "GROUP BY f.facility_type " +
                     "ORDER BY booking_count DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> type = new HashMap<>();
                type.put("facilityType", rs.getString("facility_type"));
                type.put("bookingCount", rs.getInt("booking_count"));
                type.put("totalRevenue", rs.getDouble("total_revenue"));
                distribution.add(type);
            }
        }
        return distribution;
    }
    
    // ==================== Top Students ====================
    
    public List<Map<String, Object>> getTopStudents(int limit) throws SQLException {
        List<Map<String, Object>> topStudents = new ArrayList<>();
        
        String sql = "SELECT " +
                     "u.user_id, " +
                     "u.username, " +
                     "u.full_name, " +
                     "COUNT(b.booking_id) as total_bookings, " +
                     "COALESCE(SUM(b.total_cost), 0) as total_spent " +
                     "FROM users u " +
                     "LEFT JOIN bookings b ON u.user_id = b.user_id AND b.booking_status = 'CONFIRMED' " +
                     "WHERE u.user_role = 'STUDENT' " +
                     "GROUP BY u.user_id " +
                     "ORDER BY total_bookings DESC " +
                     "LIMIT ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> student = new HashMap<>();
                student.put("userId", rs.getInt("user_id"));
                student.put("username", rs.getString("username"));
                student.put("fullName", rs.getString("full_name"));
                student.put("totalBookings", rs.getInt("total_bookings"));
                student.put("totalSpent", rs.getDouble("total_spent"));
                topStudents.add(student);
            }
        }
        return topStudents;
    }
}