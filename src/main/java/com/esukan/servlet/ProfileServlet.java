package com.esukan.servlet;

import com.esukan.model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        
        response.setContentType("text/html");
        response.getWriter().println("<!DOCTYPE html>");
        response.getWriter().println("<html>");
        response.getWriter().println("<head>");
        response.getWriter().println("<meta charset='UTF-8'>");
        response.getWriter().println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        response.getWriter().println("<title>Libang Libu - My Profile</title>");
        response.getWriter().println("<link rel='stylesheet' href='" + request.getContextPath() + "/css/modern.css'>");
        response.getWriter().println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css'>");
        response.getWriter().println("<link rel='icon' type='image/x-icon' href='" + request.getContextPath() + "/favicon.ico'>");
        response.getWriter().println("</head>");
        response.getWriter().println("<body>");
        
        // Navigation
        response.getWriter().println("<nav class='navbar'>");
        response.getWriter().println("<div class='nav-container'>");
        response.getWriter().println("<div class='nav-brand'>");
        response.getWriter().println("<img src='" + request.getContextPath() + "/assets/logo.png' alt='Libang Libu' class='nav-logo'>");
        response.getWriter().println("<div class='nav-brand-text'>");
        response.getWriter().println("<span class='nav-brand-name'>Libang Libu</span>");
        response.getWriter().println("<span class='nav-brand-sub'>E-Sukan System</span>");
        response.getWriter().println("</div>");
        response.getWriter().println("</div>");
        response.getWriter().println("<div class='nav-menu'>");
        
        if (user.getUserRole() == User.UserRole.MANAGER) {
            response.getWriter().println("<a href='" + request.getContextPath() + "/manager/dashboard' class='nav-link'><i class='fas fa-tachometer-alt'></i> Dashboard</a>");
            response.getWriter().println("<a href='" + request.getContextPath() + "/manager/facilities' class='nav-link'><i class='fas fa-building'></i> Facilities</a>");
            response.getWriter().println("<a href='" + request.getContextPath() + "/manager/equipment' class='nav-link'><i class='fas fa-dumbbell'></i> Equipment</a>");
        } else {
            response.getWriter().println("<a href='" + request.getContextPath() + "/student/dashboard' class='nav-link'><i class='fas fa-tachometer-alt'></i> Dashboard</a>");
            response.getWriter().println("<a href='" + request.getContextPath() + "/facilities' class='nav-link'><i class='fas fa-building'></i> Facilities</a>");
            response.getWriter().println("<a href='" + request.getContextPath() + "/equipment' class='nav-link'><i class='fas fa-dumbbell'></i> Equipment</a>");
        }
        
        response.getWriter().println("<div class='user-dropdown'>");
        response.getWriter().println("<button class='user-btn'>");
        response.getWriter().println("<i class='fas fa-user-circle'></i> <span>" + user.getUsername() + "</span> <i class='fas fa-chevron-down'></i>");
        response.getWriter().println("</button>");
        response.getWriter().println("<div class='dropdown-menu'>");
        response.getWriter().println("<a href='" + request.getContextPath() + "/profile'><i class='fas fa-user'></i> Profile</a>");
        response.getWriter().println("<hr>");
        response.getWriter().println("<a href='" + request.getContextPath() + "/logout'><i class='fas fa-sign-out-alt'></i> Logout</a>");
        response.getWriter().println("</div>");
        response.getWriter().println("</div>");
        response.getWriter().println("</div>");
        response.getWriter().println("</div>");
        response.getWriter().println("</nav>");
        
        response.getWriter().println("<main class='container'>");
        response.getWriter().println("<div class='fade-in-up' style='max-width: 600px; margin: 0 auto;'>");
        response.getWriter().println("<div class='card'>");
        response.getWriter().println("<h1><i class='fas fa-user-circle'></i> My Profile</h1>");
        
        response.getWriter().println("<div class='form-group'>");
        response.getWriter().println("<label class='form-label'><i class='fas fa-user'></i> Username</label>");
        response.getWriter().println("<input type='text' class='form-input' value='" + user.getUsername() + "' readonly disabled>");
        response.getWriter().println("</div>");
        
        response.getWriter().println("<div class='form-group'>");
        response.getWriter().println("<label class='form-label'><i class='fas fa-envelope'></i> Email</label>");
        response.getWriter().println("<input type='email' class='form-input' value='" + user.getEmail() + "'>");
        response.getWriter().println("</div>");
        
        response.getWriter().println("<div class='form-group'>");
        response.getWriter().println("<label class='form-label'><i class='fas fa-signature'></i> Full Name</label>");
        response.getWriter().println("<input type='text' class='form-input' value='" + user.getFullName() + "'>");
        response.getWriter().println("</div>");
        
        response.getWriter().println("<div class='form-group'>");
        response.getWriter().println("<label class='form-label'><i class='fas fa-phone'></i> Phone Number</label>");
        response.getWriter().println("<input type='tel' class='form-input' value='" + user.getPhoneNumber() + "'>");
        response.getWriter().println("</div>");
        
        if (user.getMatricNumber() != null && !user.getMatricNumber().isEmpty()) {
            response.getWriter().println("<div class='form-group'>");
            response.getWriter().println("<label class='form-label'><i class='fas fa-id-card'></i> Matric Number</label>");
            response.getWriter().println("<input type='text' class='form-input' value='" + user.getMatricNumber() + "' readonly disabled>");
            response.getWriter().println("</div>");
        }
        
        response.getWriter().println("<div class='form-group'>");
        response.getWriter().println("<label class='form-label'><i class='fas fa-tag'></i> Account Type</label>");
        response.getWriter().println("<input type='text' class='form-input' value='" + user.getUserRole() + "' readonly disabled>");
        response.getWriter().println("</div>");
        
        response.getWriter().println("<div style='display: flex; gap: 16px; margin-top: 24px;'>");
        response.getWriter().println("<button class='btn btn-primary' onclick='alert(\"Profile update feature coming soon\")'><i class='fas fa-save'></i> Update Profile</button>");
        response.getWriter().println("<button class='btn btn-outline' onclick='alert(\"Change password feature coming soon\")'><i class='fas fa-key'></i> Change Password</button>");
        response.getWriter().println("</div>");
        
        response.getWriter().println("</div>");
        response.getWriter().println("</div>");
        
        // Footer
        response.getWriter().println("<div class='footer'>");
        response.getWriter().println("<div class='footer-logo'>");
        response.getWriter().println("<img src='" + request.getContextPath() + "/assets/logo.png' alt='Libang Libu' class='footer-logo-img'>");
        response.getWriter().println("<span>Libang Libu - E-Sukan System</span>");
        response.getWriter().println("</div>");
        response.getWriter().println("<p>© 2026 Libang Libu - All Rights Reserved.</p>");
        response.getWriter().println("</div>");
        
        response.getWriter().println("</main>");
        response.getWriter().println("<script src='" + request.getContextPath() + "/js/app.js'></script>");
        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }
}