package com.esukan.servlet;

import com.esukan.model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ManagerDashboardServlet", urlPatterns = {"/manager/dashboard"})
public class ManagerDashboardServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        if (user.getUserRole() != User.UserRole.MANAGER) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Manager access only");
            return;
        }
        
        response.setContentType("text/html");
        response.getWriter().println("<!DOCTYPE html>");
        response.getWriter().println("<html>");
        response.getWriter().println("<head>");
        response.getWriter().println("<meta charset='UTF-8'>");
        response.getWriter().println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        response.getWriter().println("<title>Libang Libu - Manager Dashboard</title>");
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
        response.getWriter().println("<a href='" + request.getContextPath() + "/manager/dashboard' class='nav-link active'><i class='fas fa-tachometer-alt'></i> Dashboard</a>");
        response.getWriter().println("<a href='" + request.getContextPath() + "/manager/facilities' class='nav-link'><i class='fas fa-building'></i> Facilities</a>");
        response.getWriter().println("<a href='" + request.getContextPath() + "/manager/equipment' class='nav-link'><i class='fas fa-dumbbell'></i> Equipment</a>");
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
        
        // Welcome Section
        response.getWriter().println("<div class='dashboard-welcome fade-in-up'>");
        response.getWriter().println("<h1>Welcome, " + user.getFullName() + "!</h1>");
        response.getWriter().println("<p>Manage facilities, equipment, and view analytics</p>");
        response.getWriter().println("</div>");
        
        // Stats Cards with Font Awesome Icons
        response.getWriter().println("<div class='stats-grid'>");
        
        // Facilities Card
        response.getWriter().println("<div class='stat-card fade-in-up'>");
        response.getWriter().println("<div class='stat-icon'><i class='fas fa-building fa-2x'></i></div>");
        response.getWriter().println("<div class='stat-label'>FACILITIES</div>");
        response.getWriter().println("<div class='stat-value'>8</div>");
        response.getWriter().println("<div style='margin-top: 16px;'><a href='" + request.getContextPath() + "/manager/facilities' class='btn btn-outline btn-sm'><i class='fas fa-edit'></i> Manage</a></div>");
        response.getWriter().println("</div>");
        
        // Equipment Card
        response.getWriter().println("<div class='stat-card fade-in-up'>");
        response.getWriter().println("<div class='stat-icon'><i class='fas fa-dumbbell fa-2x'></i></div>");
        response.getWriter().println("<div class='stat-label'>EQUIPMENT</div>");
        response.getWriter().println("<div class='stat-value'>9</div>");
        response.getWriter().println("<div style='margin-top: 16px;'><a href='" + request.getContextPath() + "/manager/equipment' class='btn btn-outline btn-sm'><i class='fas fa-edit'></i> Manage</a></div>");
        response.getWriter().println("</div>");
        
        // Reports Card
        response.getWriter().println("<div class='stat-card fade-in-up'>");
        response.getWriter().println("<div class='stat-icon'><i class='fas fa-chart-line fa-2x'></i></div>");
        response.getWriter().println("<div class='stat-label'>REPORTS</div>");
        response.getWriter().println("<div class='stat-value'>Generate</div>");
        response.getWriter().println("<div style='margin-top: 16px;'><button class='btn btn-outline btn-sm' onclick='alert(\"Report generation coming soon\")'><i class='fas fa-download'></i> View</button></div>");
        response.getWriter().println("</div>");
        response.getWriter().println("</div>");
        
        // Footer - Copyright 2026
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