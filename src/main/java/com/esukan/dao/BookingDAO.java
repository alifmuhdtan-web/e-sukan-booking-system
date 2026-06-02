package com.esukan.dao;

import com.esukan.model.Booking;
import com.esukan.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    // Create a new booking
    public boolean createBooking(Booking booking) {
        boolean status = false;
        // Exclude created_at and updated_at - they auto-populate
        String sql = "INSERT INTO bookings (user_id, facility_id, booking_date, start_time, end_time, total_cost, payment_status, booking_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getFacilityId());
            ps.setDate(3, booking.getBookingDate());
            ps.setTime(4, booking.getStartTime());
            ps.setTime(5, booking.getEndTime());
            ps.setDouble(6, booking.getTotalCost());
            ps.setString(7, "PENDING");
            ps.setString(8, "CONFIRMED");
            
            status = ps.executeUpdate() > 0;
            
            if (status) {
                System.out.println("✓ Booking created successfully for user: " + booking.getUserId());
            }
            
        } catch (SQLException e) {
            System.err.println("SQL Error creating booking: " + e.getMessage());
            e.printStackTrace();
        }
        
        return status;
    }

    // Get all bookings for a specific user
    public List<Booking> getBookingsByUser(int userId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT b.booking_id, b.user_id, b.facility_id, f.facility_name, " +
                     "b.booking_date, b.start_time, b.end_time, b.total_cost, b.booking_status " +
                     "FROM bookings b " +
                     "JOIN facilities f ON b.facility_id = f.facility_id " +
                     "WHERE b.user_id = ? ORDER BY b.booking_date DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("booking_id"));
                b.setUserId(rs.getInt("user_id"));
                b.setFacilityId(rs.getInt("facility_id"));
                b.setFacilityName(rs.getString("facility_name"));
                b.setBookingDate(rs.getDate("booking_date"));
                b.setStartTime(rs.getTime("start_time"));
                b.setEndTime(rs.getTime("end_time"));
                b.setTotalCost(rs.getDouble("total_cost"));
                b.setStatus(rs.getString("booking_status"));
                list.add(b);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }

    // Cancel a booking
    public boolean cancelBooking(int bookingId) {
        boolean status = false;
        String sql = "UPDATE bookings SET booking_status = 'CANCELLED' WHERE booking_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, bookingId);
            status = ps.executeUpdate() > 0;
            
            if (status) {
                System.out.println("✓ Booking cancelled successfully: " + bookingId);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return status;
    }
    
    // Check if a time slot is available
    public boolean checkAvailability(int facilityId, Date bookingDate, Time startTime, Time endTime) {
        String sql = "SELECT COUNT(*) FROM bookings WHERE facility_id = ? AND booking_date = ? " +
                     "AND booking_status = 'CONFIRMED' AND ((start_time < ? AND end_time > ?) " +
                     "OR (start_time < ? AND end_time > ?) OR (start_time >= ? AND end_time <= ?))";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, facilityId);
            ps.setDate(2, bookingDate);
            ps.setTime(3, endTime);
            ps.setTime(4, startTime);
            ps.setTime(5, startTime);
            ps.setTime(6, endTime);
            ps.setTime(7, startTime);
            ps.setTime(8, endTime);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return true;
    }
    
    // Get a single booking by ID
    public Booking getBookingById(int bookingId) {
        Booking booking = null;
        String sql = "SELECT b.booking_id, b.user_id, b.facility_id, f.facility_name, " +
                     "b.booking_date, b.start_time, b.end_time, b.total_cost, b.booking_status " +
                     "FROM bookings b " +
                     "JOIN facilities f ON b.facility_id = f.facility_id " +
                     "WHERE b.booking_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                booking = new Booking();
                booking.setId(rs.getInt("booking_id"));
                booking.setUserId(rs.getInt("user_id"));
                booking.setFacilityId(rs.getInt("facility_id"));
                booking.setFacilityName(rs.getString("facility_name"));
                booking.setBookingDate(rs.getDate("booking_date"));
                booking.setStartTime(rs.getTime("start_time"));
                booking.setEndTime(rs.getTime("end_time"));
                booking.setTotalCost(rs.getDouble("total_cost"));
                booking.setStatus(rs.getString("booking_status"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return booking;
    }
}