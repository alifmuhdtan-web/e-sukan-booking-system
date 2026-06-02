package com.esukan.dao;

import com.esukan.model.User;
import com.esukan.util.DatabaseUtil;
import java.sql.*;
import java.util.Optional;

public class UserDAO {
    
    // SQL Queries
    private static final String INSERT_SQL = 
        "INSERT INTO users (username, email, password_hash, full_name, phone_number, matric_number, user_role, is_active) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SELECT_BY_USERNAME_SQL = 
        "SELECT * FROM users WHERE username = ?";
    
    private static final String SELECT_BY_EMAIL_SQL = 
        "SELECT * FROM users WHERE email = ?";
    
    private static final String UPDATE_LAST_LOGIN_SQL = 
        "UPDATE users SET last_login = CURRENT_TIMESTAMP WHERE user_id = ?";
    
    private static final String UPDATE_PROFILE_SQL = 
        "UPDATE users SET full_name = ?, phone_number = ? WHERE user_id = ?";
    
    private static final String UPDATE_PASSWORD_SQL = 
        "UPDATE users SET password_hash = ? WHERE user_id = ?";
    
    // Map ResultSet to User object
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setFullName(rs.getString("full_name"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setMatricNumber(rs.getString("matric_number"));
        user.setUserRole(User.UserRole.valueOf(rs.getString("user_role")));
        user.setActive(rs.getBoolean("is_active"));
        user.setLastLogin(rs.getTimestamp("last_login"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setUpdatedAt(rs.getTimestamp("updated_at"));
        return user;
    }
    
    // Create new user
    public User create(User user) throws SQLException {
        String generatedColumns[] = {"user_id"};
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL, generatedColumns)) {
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPasswordHash());
            pstmt.setString(4, user.getFullName());
            pstmt.setString(5, user.getPhoneNumber());
            pstmt.setString(6, user.getMatricNumber());
            pstmt.setString(7, user.getUserRole().toString());
            pstmt.setBoolean(8, user.isActive());
            
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setUserId(rs.getInt(1));
                }
            }
            return user;
        }
    }
    
    // Find user by username (for login)
    public Optional<User> findByUsername(String username) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_USERNAME_SQL)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToUser(rs));
            }
        }
        return Optional.empty();
    }
    
    // Find user by email (for registration check)
    public Optional<User> findByEmail(String email) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_EMAIL_SQL)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToUser(rs));
            }
        }
        return Optional.empty();
    }
    
    // Update last login timestamp
    public boolean updateLastLogin(int userId) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_LAST_LOGIN_SQL)) {
            pstmt.setInt(1, userId);
            return pstmt.executeUpdate() > 0;
        }
    }
    
    // Update user profile
    public boolean updateProfile(int userId, String fullName, String phoneNumber) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_PROFILE_SQL)) {
            pstmt.setString(1, fullName);
            pstmt.setString(2, phoneNumber);
            pstmt.setInt(3, userId);
            return pstmt.executeUpdate() > 0;
        }
    }
    
    // Update password
    public boolean updatePassword(int userId, String newPasswordHash) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_PASSWORD_SQL)) {
            pstmt.setString(1, newPasswordHash);
            pstmt.setInt(2, userId);
            return pstmt.executeUpdate() > 0;
        }
    }
    
    // Check if username exists
    public boolean usernameExists(String username) throws SQLException {
        return findByUsername(username).isPresent();
    }
    
    // Check if email exists
    public boolean emailExists(String email) throws SQLException {
        return findByEmail(email).isPresent();
    }
}