package com.esukan.dao; // Sesuaikan mengikut nama package asal projek korang

import com.esukan.model.Facility;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacilityDAO {
    
    // Gunakan fungsi connection yang sedia ada dalam projek korang. 
    // Contoh di bawah mengandaikan ada kelas DBConnection.
    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // Menghubungkan menggunakan user khusus yang Safwan set tadi
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/esukan_db", "esukan_user", "1234!");
    }

    // 1. Ambil SEMUA fasiliti (Untuk list.jsp)
    public List<Facility> getAllFacilities() {
        List<Facility> facilities = new ArrayList<>();
        String query = "SELECT * FROM facilities";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                facilities.add(new Facility(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getString("description"),
                    rs.getDouble("price_per_hour"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facilities;
    }

    // 2. Ambil SATU fasiliti berdasarkan ID (Untuk detail.jsp)
    public Facility getFacilityById(int id) {
        String query = "SELECT * FROM facilities WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Facility(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getDouble("price_per_hour"),
                        rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. TAMBAH fasiliti baru (Manager CRUD)
    public boolean insertFacility(Facility facility) {
        String query = "INSERT INTO facilities (name, type, description, price_per_hour, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, facility.getName());
            ps.setString(2, facility.getType());
            ps.setString(3, facility.getDescription());
            ps.setDouble(4, facility.getPricePerHour());
            ps.setString(5, facility.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. KEMASKINI fasiliti (Manager CRUD)
    public boolean updateFacility(Facility facility) {
        String query = "UPDATE facilities SET name=?, type=?, description=?, price_per_hour=?, status=? WHERE id=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, facility.getName());
            ps.setString(2, facility.getType());
            ps.setString(3, facility.getDescription());
            ps.setDouble(4, facility.getPricePerHour());
            ps.setString(5, facility.getStatus());
            ps.setInt(6, facility.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 5. PADAM fasiliti (Manager CRUD)
    public boolean deleteFacility(int id) {
        String query = "DELETE FROM facilities WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}