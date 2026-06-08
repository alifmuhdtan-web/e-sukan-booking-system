package com.esukan.servlet;

import com.esukan.dao.BookingDAO;
import com.esukan.dao.EquipmentDAO;
import com.esukan.model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "StudentDashboardServlet", urlPatterns = {"/student/dashboard"})
public class StudentDashboardServlet extends HttpServlet {
    private BookingDAO bookingDAO;
    private EquipmentDAO equipmentDAO;
    
    @Override
    public void init() {
        bookingDAO = new BookingDAO();
        equipmentDAO = new EquipmentDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        int totalBookings = 0;
        int activeRentals = 0;
        
        try {
            totalBookings = bookingDAO.countByUser(user.getUserId());
            activeRentals = equipmentDAO.countActiveRentalsByUser(user.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        response.setContentType("text/html");
        response.getWriter().println("<!DOCTYPE html>");
        response.getWriter().println("<html>");
        response.getWriter().println("<head>");
        response.getWriter().println("<meta charset='UTF-8'>");
        response.getWriter().println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        response.getWriter().println("<title>Libang Libu - Student Dashboard</title>");
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
        response.getWriter().println("<a href='" + request.getContextPath() + "/student/dashboard' class='nav-link active'><i class='fas fa-tachometer-alt'></i> Dashboard</a>");
        response.getWriter().println("<a href='" + request.getContextPath() + "/facilities' class='nav-link'><i class='fas fa-building'></i> Facilities</a>");
        response.getWriter().println("<a href='" + request.getContextPath() + "/equipment' class='nav-link'><i class='fas fa-dumbbell'></i> Equipment</a>");
        response.getWriter().println("<div class='user-dropdown'>");
        response.getWriter().println("<button class='user-btn'>");
        response.getWriter().println("<i class='fas fa-user-circle'></i> <span>" + user.getUsername() + "</span> <i class='fas fa-chevron-down'></i>");
        response.getWriter().println("</button>");
        response.getWriter().println("<div class='dropdown-menu'>");
        response.getWriter().println("<a href='" + request.getContextPath() + "/profile'><i class='fas fa-user'></i> Profile</a>");
        response.getWriter().println("<a href='" + request.getContextPath() + "/my-rentals'><i class='fas fa-box'></i> My Rentals</a>");
        response.getWriter().println("<hr>");
        response.getWriter().println("<a href='" + request.getContextPath() + "/logout'><i class='fas fa-sign-out-alt'></i> Logout</a>");
        response.getWriter().println("</div>");
        response.getWriter().println("</div>");
        response.getWriter().println("</div>");
        response.getWriter().println("</div>");
        response.getWriter().println("</nav>");
        
        response.getWriter().println("<main class='container'>");
        response.getWriter().println("<div class='dashboard-welcome fade-in-up'>");
        response.getWriter().println("<h1>Welcome back, " + user.getFullName() + "!</h1>");
        response.getWriter().println("<p>Ready to book a facility or rent equipment?</p>");
        response.getWriter().println("</div>");
        
        // Stats Cards - Only 2 cards (NO POINTS)
        response.getWriter().println("<div class='stats-grid'>");
        
        // Card 1: Total Bookings
        response.getWriter().println("<div class='stat-card fade-in-up'>");
        response.getWriter().println("<div class='stat-icon'><i class='fas fa-calendar-alt fa-2x'></i></div>");
        response.getWriter().println("<div class='stat-label'>TOTAL BOOKINGS</div>");
        response.getWriter().println("<div class='stat-value'>" + totalBookings + "</div>");
        response.getWriter().println("</div>");
        
        // Card 2: Active Rentals
        response.getWriter().println("<div class='stat-card fade-in-up'>");
        response.getWriter().println("<div class='stat-icon'><i class='fas fa-box fa-2x'></i></div>");
        response.getWriter().println("<div class='stat-label'>ACTIVE RENTALS</div>");
        response.getWriter().println("<div class='stat-value'>" + activeRentals + "</div>");
        response.getWriter().println("</div>");
        
        response.getWriter().println("</div>");
        
        // Quick Actions
        response.getWriter().println("<div class='card' style='margin-bottom: var(--spacing-xl);'>");
        response.getWriter().println("<h3><i class='fas fa-bolt'></i> Quick Actions</h3>");
        response.getWriter().println("<div style='display: flex; gap: var(--spacing-md); flex-wrap: wrap; margin-top: var(--spacing-md);'>");
        response.getWriter().println("<a href='" + request.getContextPath() + "/CreateBookingServlet?facilityId=1' class='btn btn-primary'><i class='fas fa-calendar-plus'></i> Book Facility</a>");
        response.getWriter().println("<a href='" + request.getContextPath() + "/equipment' class='btn btn-outline'><i class='fas fa-dumbbell'></i> Rent Equipment</a>");
        response.getWriter().println("<a href='" + request.getContextPath() + "/MyBookingsServlet' class='btn btn-outline'><i class='fas fa-list'></i> My Bookings</a>");
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