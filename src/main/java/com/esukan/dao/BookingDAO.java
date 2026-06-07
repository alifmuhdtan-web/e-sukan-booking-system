package com.esukan.dao;

import com.esukan.model.Booking;
import com.esukan.util.DatabaseUtil;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingDAO {
    
    // SQL Queries
    private static final String INSERT_SQL = 
        "INSERT INTO bookings (user_id, facility_id, booking_date, start_time, end_time, " +
        "total_cost, payment_status, booking_status, special_requests) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SELECT_BY_USER_SQL = 
        "SELECT b.*, f.facility_name FROM bookings b " +
        "JOIN facilities f ON b.facility_id = f.facility_id " +
        "WHERE b.user_id = ? ORDER BY b.booking_date DESC, b.start_time DESC";
    
    private static final String SELECT_BY_ID_SQL = 
        "SELECT b.*, f.facility_name, u.username FROM bookings b " +
        "JOIN facilities f ON b.facility_id = f.facility_id " +
        "JOIN users u ON b.user_id = u.user_id " +
        "WHERE b.booking_id = ?";
    
    private static final String CANCEL_SQL = 
        "UPDATE bookings SET booking_status = 'CANCELLED', cancellation_reason = ?, " +
        "cancelled_at = CURRENT_TIMESTAMP WHERE booking_id = ? AND user_id = ?";
    
    private static final String CHECK_AVAILABILITY_SQL = 
        "SELECT COUNT(*) FROM bookings WHERE facility_id = ? AND booking_date = ? " +
        "AND booking_status = 'CONFIRMED' AND ((start_time < ? AND end_time > ?) " +
        "OR (start_time < ? AND end_time > ?) OR (start_time >= ? AND end_time <= ?))";
    
    private static final String GET_BOOKINGS_BY_USER_SQL = 
        "SELECT b.*, f.facility_name FROM bookings b " +
        "JOIN facilities f ON b.facility_id = f.facility_id " +
        "WHERE b.user_id = ? ORDER BY b.booking_date DESC";
    
    private static final String COUNT_BY_USER_SQL = 
        "SELECT COUNT(*) FROM bookings WHERE user_id = ?";
    
    private Booking mapResultSetToBooking(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setBookingId(rs.getInt("booking_id"));
        booking.setBookingReference(rs.getString("booking_reference"));
        booking.setUserId(rs.getInt("user_id"));
        booking.setFacilityId(rs.getInt("facility_id"));
        booking.setBookingDate(rs.getDate("booking_date"));
        booking.setStartTime(rs.getTime("start_time"));
        booking.setEndTime(rs.getTime("end_time"));
        booking.setDurationHours(rs.getDouble("duration_hours"));
        booking.setTotalCost(rs.getBigDecimal("total_cost"));
        
        String paymentStatus = rs.getString("payment_status");
        if (paymentStatus != null) {
            booking.setPaymentStatus(Booking.PaymentStatus.valueOf(paymentStatus));
        }
        
        String bookingStatus = rs.getString("booking_status");
        if (bookingStatus != null) {
            booking.setBookingStatus(Booking.BookingStatus.valueOf(bookingStatus));
        }
        
        booking.setSpecialRequests(rs.getString("special_requests"));
        booking.setCancellationReason(rs.getString("cancellation_reason"));
        booking.setCancelledAt(rs.getTimestamp("cancelled_at"));
        booking.setCreatedAt(rs.getTimestamp("created_at"));
        booking.setUpdatedAt(rs.getTimestamp("updated_at"));
        
        try { booking.setFacilityName(rs.getString("facility_name")); } catch (Exception e) {}
        try { booking.setUserName(rs.getString("username")); } catch (Exception e) {}
        
        return booking;
    }
    
    // Create booking
    public boolean createBooking(Booking booking) throws SQLException {
        String generatedColumns[] = {"booking_id"};
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL, generatedColumns)) {
            
            pstmt.setInt(1, booking.getUserId());
            pstmt.setInt(2, booking.getFacilityId());
            pstmt.setDate(3, booking.getBookingDate());
            pstmt.setTime(4, booking.getStartTime());
            pstmt.setTime(5, booking.getEndTime());
            pstmt.setBigDecimal(6, booking.getTotalCost());
            pstmt.setString(7, booking.getPaymentStatus().toString());
            pstmt.setString(8, booking.getBookingStatus().toString());
            pstmt.setString(9, booking.getSpecialRequests());
            
            return pstmt.executeUpdate() > 0;
        }
    }
    
    // Get bookings by user ID
    public List<Booking> getBookingsByUser(int userId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(GET_BOOKINGS_BY_USER_SQL)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        }
        return bookings;
    }
    
    // Cancel booking
    public boolean cancelBooking(int bookingId) throws SQLException {
        String sql = "UPDATE bookings SET booking_status = 'CANCELLED', cancelled_at = CURRENT_TIMESTAMP WHERE booking_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookingId);
            return pstmt.executeUpdate() > 0;
        }
    }
    
    // Count bookings by user
    public int countByUser(int userId) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(COUNT_BY_USER_SQL)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    
    // Check availability
    public boolean checkAvailability(int facilityId, Date bookingDate, Time startTime, Time endTime) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(CHECK_AVAILABILITY_SQL)) {
            pstmt.setInt(1, facilityId);
            pstmt.setDate(2, bookingDate);
            pstmt.setTime(3, endTime);
            pstmt.setTime(4, startTime);
            pstmt.setTime(5, startTime);
            pstmt.setTime(6, endTime);
            pstmt.setTime(7, startTime);
            pstmt.setTime(8, endTime);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
            return true;
        }
    }
}