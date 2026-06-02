package com.esukan.servlet;

import com.esukan.dao.UserDAO;
import com.esukan.model.User;
import com.esukan.util.PasswordUtil;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    
    private UserDAO userDAO;
    
    @Override
    public void init() {
        userDAO = new UserDAO();
    }
    
    // Show login page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp")
               .forward(request, response);
    }
    
    // Process login
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Validation
        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("error", "Username is required");
            request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp")
                   .forward(request, response);
            return;
        }
        
        if (password == null || password.isEmpty()) {
            request.setAttribute("error", "Password is required");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp")
                   .forward(request, response);
            return;
        }
        
        try {
            // Find user by username
            Optional<User> userOpt = userDAO.findByUsername(username);
            
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                
                // Verify password
                if (PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
                    
                    // Check if account is active
                    if (!user.isActive()) {
                        request.setAttribute("error", "Your account has been deactivated. Please contact administrator.");
                        request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp")
                               .forward(request, response);
                        return;
                    }
                    
                    // Login successful - create session
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    session.setAttribute("userId", user.getUserId());
                    session.setAttribute("username", user.getUsername());
                    session.setAttribute("fullName", user.getFullName());
                    session.setAttribute("userRole", user.getUserRole().toString());
                    session.setMaxInactiveInterval(30 * 60); // 30 minutes timeout
                    
                    // Update last login
                    userDAO.updateLastLogin(user.getUserId());
                    
                    // Redirect based on role
                    if (user.getUserRole() == User.UserRole.MANAGER) {
                        response.sendRedirect(request.getContextPath() + "/manager/dashboard");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/student/dashboard");
                    }
                    return;
                }
            }
            
            // Login failed
            request.setAttribute("error", "Invalid username or password");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp")
                   .forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "System error. Please try again later.");
            request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp")
                   .forward(request, response);
        }
    }
}