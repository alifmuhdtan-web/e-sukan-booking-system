package com.esukan.util;

import java.security.MessageDigest;

public class PasswordUtil {
    
    
    
    private static final String SALT = "E-Sukan2024!@#SecureSalt";
    
    public static String hashPassword(String password) {
        try {
            
            String saltedPassword = password + SALT;
            
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(saltedPassword.getBytes("UTF-8"));
            
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to hash password", e);
        }
    }
    
    public static boolean verifyPassword(String plainPassword, String storedHash) {
        String computedHash = hashPassword(plainPassword);
        return computedHash.equals(storedHash);
    }
    
    
    public static void main(String[] args) {
        String hash = hashPassword("password123");
        System.out.println("Hash of 'password123': " + hash);
        System.out.println("Length: " + hash.length());
    }
}