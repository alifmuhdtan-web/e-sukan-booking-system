package com.esukan.model;

import java.sql.Timestamp;

public class User {
    
    public enum UserRole {
        STUDENT, MANAGER
    }
    
    private int userId;
    private String username;
    private String email;
    private String passwordHash;
    private String fullName;
    private String phoneNumber;
    private String matricNumber;
    private UserRole userRole;
    private boolean isActive;
    private Timestamp lastLogin;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Constructors
    public User() {}
    
    public User(String username, String email, String passwordHash, String fullName, 
                String phoneNumber, String matricNumber, UserRole userRole) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.matricNumber = matricNumber;
        this.userRole = userRole;
        this.isActive = true;
    }
    
    // Getters
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getFullName() { return fullName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getMatricNumber() { return matricNumber; }
    public UserRole getUserRole() { return userRole; }
    public boolean isActive() { return isActive; }
    public Timestamp getLastLogin() { return lastLogin; }
    public Timestamp getCreatedAt() { return createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    
    // Setters
    public void setUserId(int userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setMatricNumber(String matricNumber) { this.matricNumber = matricNumber; }
    public void setUserRole(UserRole userRole) { this.userRole = userRole; }
    public void setActive(boolean active) { isActive = active; }
    public void setLastLogin(Timestamp lastLogin) { this.lastLogin = lastLogin; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
    
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}