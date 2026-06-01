package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Booking;
import util.DBConnection;

public class BookingDAO {

    public boolean createBooking(Booking booking) {

        boolean status = false;

        try {

            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO bookings "
                    + "(user_id, facility_id, booking_date, start_time, end_time, total_cost, status) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getFacilityId());
            ps.setDate(3, booking.getBookingDate());
            ps.setTime(4, booking.getStartTime());
            ps.setTime(5, booking.getEndTime());
            ps.setDouble(6, booking.getTotalCost());
            ps.setString(7, booking.getStatus());

            status = ps.executeUpdate() > 0;

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public List<Booking> getBookingsByUser(int userId) {

        List<Booking> list = new ArrayList<>();

        try {

            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM bookings WHERE user_id=? ORDER BY booking_date DESC";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Booking b = new Booking();

                b.setId(rs.getInt("id"));
                b.setUserId(rs.getInt("user_id"));
                b.setFacilityId(rs.getInt("facility_id"));
                b.setBookingDate(rs.getDate("booking_date"));
                b.setStartTime(rs.getTime("start_time"));
                b.setEndTime(rs.getTime("end_time"));
                b.setTotalCost(rs.getDouble("total_cost"));
                b.setStatus(rs.getString("status"));

                list.add(b);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean cancelBooking(int bookingId) {

        boolean status = false;

        try {

            Connection conn = DBConnection.getConnection();

            String sql = "UPDATE bookings SET status='CANCELLED' WHERE id=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, bookingId);

            status = ps.executeUpdate() > 0;

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}
