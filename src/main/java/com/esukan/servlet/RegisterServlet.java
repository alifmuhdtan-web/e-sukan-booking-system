package com.esukan.servlet;

import com.esukan.dao.UserDAO;
import com.esukan.model.User;
import com.esukan.util.PasswordUtil;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    
    private UserDAO userDAO;
    
    @Override
    public void init() {
        userDAO = new UserDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/auth/register.jsp")
               .forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fullName = request.getParameter("fullName");
        String phoneNumber = request.getParameter("phoneNumber");
        String matricNumber = request.getParameter("matricNumber");
        String userRole = request.getParameter("userRole");
        
        StringBuilder errors = new StringBuilder();
        
        if (username == null || username.trim().length() < 3) {
            errors.append("Username must be at least 3 characters.<br>");
        }
        
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.append("Invalid email format.<br>");
        }
        
        if (password == null || password.length() < 6) {
            errors.append("Password must be at least 6 characters.<br>");
        }
        
        if (!password.equals(confirmPassword)) {
            errors.append("Passwords do not match.<br>");
        }
        
        if (fullName == null || fullName.trim().isEmpty()) {
            errors.append("Full name is required.<br>");
        }
        
        if (phoneNumber == null || !phoneNumber.matches("^[0-9]{10,11}$")) {
            errors.append("Invalid phone number (10-11 digits).<br>");
        }
        
        try {
            if (userDAO.usernameExists(username)) {
                errors.append("Username already exists.<br>");
            }
            if (userDAO.emailExists(email)) {
                errors.append("Email already registered.<br>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            errors.append("Database error. Please try again.<br>");
        }
        
        if (errors.length() > 0) {
            request.setAttribute("errors", errors.toString());
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("fullName", fullName);
            request.setAttribute("phoneNumber", phoneNumber);
            request.setAttribute("matricNumber", matricNumber);
            request.getRequestDispatcher("/WEB-INF/jsp/auth/register.jsp")
                   .forward(request, response);
            return;
        }
        
        try {
            String hashedPassword = PasswordUtil.hashPassword(password);
            
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPasswordHash(hashedPassword);
            newUser.setFullName(fullName);
            newUser.setPhoneNumber(phoneNumber);
            newUser.setMatricNumber(matricNumber);
            newUser.setUserRole("MANAGER".equals(userRole) ? User.UserRole.MANAGER : User.UserRole.STUDENT);
            newUser.setActive(true);
            
            userDAO.create(newUser);
            
            request.setAttribute("successMessage", "Registration successful! Please login.");
            request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp")
                   .forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errors", "Database error. Please try again later.");
            request.getRequestDispatcher("/WEB-INF/jsp/auth/register.jsp")
                   .forward(request, response);
        }
    }
}