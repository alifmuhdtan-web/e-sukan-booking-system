package com.esukan.servlet;

import com.esukan.dao.AnalyticsDAO;
import com.esukan.model.User;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ManagerDashboardServlet", urlPatterns = {"/manager/dashboard"})
public class ManagerDashboardServlet extends HttpServlet {
    private AnalyticsDAO analyticsDAO;
    
    @Override
    public void init() {
        analyticsDAO = new AnalyticsDAO();
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
        if (user.getUserRole() != User.UserRole.MANAGER) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Manager access only");
            return;
        }
        
        // Declare variables
        Map<String, Object> stats = null;
        List<Map<String, Object>> peakHours = null;
        List<Map<String, Object>> popularFacilities = null;
        List<Map<String, Object>> equipmentUtilization = null;
        List<Map<String, Object>> maintenanceAlerts = null;
        List<Map<String, Object>> weeklyTrend = null;
        List<Map<String, Object>> monthlyTrend = null;
        List<Map<String, Object>> recentActivities = null;
        List<Map<String, Object>> bookingsByType = null;
        List<Map<String, Object>> topStudents = null;
        
        try {
            stats = analyticsDAO.getDashboardStats();
            peakHours = analyticsDAO.getPeakUsageHours();
            popularFacilities = analyticsDAO.getPopularFacilities();
            equipmentUtilization = analyticsDAO.getEquipmentUtilization();
            maintenanceAlerts = analyticsDAO.getEquipmentMaintenanceAlerts();
            weeklyTrend = analyticsDAO.getWeeklyBookingTrend();
            monthlyTrend = analyticsDAO.getMonthlyBookingTrend();
            recentActivities = analyticsDAO.getRecentActivities(10);
            bookingsByType = analyticsDAO.getBookingsByFacilityType();
            topStudents = analyticsDAO.getTopStudents(5);
        } catch (Exception e) {
            e.printStackTrace();
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
        response.getWriter().println("<style>");
        response.getWriter().println(".progress-bar { background: #E2E8F0; border-radius: 10px; overflow: hidden; height: 8px; }");
        response.getWriter().println(".progress-fill { background: #1E3A5F; height: 100%; border-radius: 10px; }");
        response.getWriter().println(".alert-card { border-left: 4px solid #EF4444; background: #FEF2F2; padding: 12px; margin-bottom: 12px; border-radius: 8px; }");
        response.getWriter().println("</style>");
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
        
        // Stats Cards
        response.getWriter().println("<div class='stats-grid'>");
        response.getWriter().println("<div class='stat-card fade-in-up'><div class='stat-icon'><i class='fas fa-users fa-2x'></i></div><div class='stat-label'>STUDENTS</div><div class='stat-value'>" + (stats != null ? stats.getOrDefault("totalStudents", 0) : 0) + "</div></div>");
        response.getWriter().println("<div class='stat-card fade-in-up'><div class='stat-icon'><i class='fas fa-building fa-2x'></i></div><div class='stat-label'>FACILITIES</div><div class='stat-value'>" + (stats != null ? stats.getOrDefault("totalFacilities", 0) : 0) + "</div></div>");
        response.getWriter().println("<div class='stat-card fade-in-up'><div class='stat-icon'><i class='fas fa-dumbbell fa-2x'></i></div><div class='stat-label'>EQUIPMENT</div><div class='stat-value'>" + (stats != null ? stats.getOrDefault("totalEquipment", 0) : 0) + "</div></div>");
        response.getWriter().println("<div class='stat-card fade-in-up'><div class='stat-icon'><i class='fas fa-calendar-check fa-2x'></i></div><div class='stat-label'>UPCOMING</div><div class='stat-value'>" + (stats != null ? stats.getOrDefault("upcomingBookings", 0) : 0) + "</div></div>");
        response.getWriter().println("<div class='stat-card fade-in-up'><div class='stat-icon'><i class='fas fa-box fa-2x'></i></div><div class='stat-label'>ACTIVE RENTALS</div><div class='stat-value'>" + (stats != null ? stats.getOrDefault("activeRentals", 0) : 0) + "</div></div>");
        response.getWriter().println("<div class='stat-card fade-in-up'><div class='stat-icon'><i class='fas fa-chart-line fa-2x'></i></div><div class='stat-label'>MONTHLY REVENUE</div><div class='stat-value'>RM " + (stats != null ? stats.getOrDefault("monthlyRevenue", 0) : 0) + "</div></div>");
        response.getWriter().println("</div>");
        
        // Two Column Layout
        response.getWriter().println("<div style='display: grid; grid-template-columns: 1fr 1fr; gap: 24px; margin-bottom: 32px;'>");
        
        // Peak Usage Hours
        response.getWriter().println("<div class='card'>");
        response.getWriter().println("<h3><i class='fas fa-chart-bar'></i> Peak Usage Hours</h3>");
        response.getWriter().println("<table style='width:100%; border-collapse: collapse;'>");
        response.getWriter().println("<thead><tr><th style='text-align:left; padding:8px;'>Hour</th><th style='text-align:left; padding:8px;'>Day</th><th style='text-align:left; padding:8px;'>Bookings</th></tr></thead><tbody>");
        if (peakHours != null && !peakHours.isEmpty()) {
            for (Map<String, Object> hour : peakHours) {
                response.getWriter().println("<tr><td style='padding:8px;'>" + hour.get("hour") + ":00</td><td style='padding:8px;'>" + hour.get("dayOfWeek") + "</td><td style='padding:8px;'>" + hour.get("bookingCount") + "</td></tr>");
            }
        } else {
            response.getWriter().println("<tr><td colspan='3' style='padding:8px; text-align:center;'>No data available</td></tr>");
        }
        response.getWriter().println("</tbody></table></div>");
        
        // Popular Facilities
        response.getWriter().println("<div class='card'>");
        response.getWriter().println("<h3><i class='fas fa-trophy'></i> Most Popular Facilities</h3>");
        response.getWriter().println("<table style='width:100%; border-collapse: collapse;'>");
        response.getWriter().println("<thead><tr><th style='text-align:left; padding:8px;'>Facility</th><th style='text-align:left; padding:8px;'>Bookings</th><th style='text-align:left; padding:8px;'>Revenue</th></tr></thead><tbody>");
        if (popularFacilities != null && !popularFacilities.isEmpty()) {
            for (Map<String, Object> facility : popularFacilities) {
                response.getWriter().println("<tr><td style='padding:8px;'>" + facility.get("facilityName") + "</td><td style='padding:8px;'>" + facility.get("totalBookings") + "</td><td style='padding:8px;'>RM " + facility.get("totalRevenue") + "</td></tr>");
            }
        } else {
            response.getWriter().println("<tr><td colspan='3' style='padding:8px; text-align:center;'>No data available</td></tr>");
        }
        response.getWriter().println("</tbody></table></div>");
        response.getWriter().println("</div>");
        
        // Equipment Utilization
        response.getWriter().println("<div class='card' style='margin-bottom: 32px;'>");
        response.getWriter().println("<h3><i class='fas fa-chart-pie'></i> Equipment Utilization Rates</h3>");
        if (equipmentUtilization != null && !equipmentUtilization.isEmpty()) {
            for (Map<String, Object> equip : equipmentUtilization) {
                double rate = (double) equip.get("utilizationRate");
                response.getWriter().println("<div style='margin-bottom: 16px;'>");
                response.getWriter().println("<div style='display: flex; justify-content: space-between; margin-bottom: 4px;'>");
                response.getWriter().println("<span>" + equip.get("equipmentName") + "</span>");
                response.getWriter().println("<span>" + String.format("%.1f", rate) + "%</span>");
                response.getWriter().println("</div>");
                response.getWriter().println("<div class='progress-bar'><div class='progress-fill' style='width: " + rate + "%;'></div></div>");
                response.getWriter().println("</div>");
            }
        } else {
            response.getWriter().println("<p>No equipment data available</p>");
        }
        response.getWriter().println("</div>");
        
        // Maintenance Alerts
        response.getWriter().println("<div class='card' style='margin-bottom: 32px;'>");
        response.getWriter().println("<h3><i class='fas fa-exclamation-triangle'></i> Maintenance Alerts</h3>");
        if (maintenanceAlerts != null && !maintenanceAlerts.isEmpty()) {
            for (Map<String, Object> alert : maintenanceAlerts) {
                response.getWriter().println("<div class='alert-card'>");
                response.getWriter().println("<strong>" + alert.get("equipmentName") + "</strong><br>");
                response.getWriter().println("Condition: <span style='color: #EF4444;'>" + alert.get("conditionStatus") + "</span><br>");
                response.getWriter().println("</div>");
            }
        } else {
            response.getWriter().println("<p>No maintenance alerts. All equipment is in good condition.</p>");
        }
        response.getWriter().println("</div>");
        
        // Top Students
        response.getWriter().println("<div class='card' style='margin-bottom: 32px;'>");
        response.getWriter().println("<h3><i class='fas fa-star'></i> Top Students (Most Active)</h3>");
        response.getWriter().println("<table style='width:100%; border-collapse: collapse;'>");
        response.getWriter().println("<thead><tr><th style='text-align:left; padding:8px;'>Name</th><th style='text-align:left; padding:8px;'>Bookings</th><th style='text-align:left; padding:8px;'>Spent (RM)</th></tr></thead><tbody>");
        if (topStudents != null && !topStudents.isEmpty()) {
            for (Map<String, Object> student : topStudents) {
                response.getWriter().println("<tr><td style='padding:8px;'>" + student.get("fullName") + "</td><td style='padding:8px;'>" + student.get("totalBookings") + "</td><td style='padding:8px;'>RM " + student.get("totalSpent") + "</td></tr>");
            }
        } else {
            response.getWriter().println("<tr><td colspan='3' style='padding:8px; text-align:center;'>No data available</td></tr>");
        }
        response.getWriter().println("</tbody></table></div>");
        
        // Recent Activities
        response.getWriter().println("<div class='card' style='margin-bottom: 32px;'>");
        response.getWriter().println("<h3><i class='fas fa-history'></i> Recent Activities</h3>");
        response.getWriter().println("<table style='width:100%; border-collapse: collapse;'>");
        response.getWriter().println("<thead><tr><th style='text-align:left; padding:8px;'>Type</th><th style='text-align:left; padding:8px;'>User</th><th style='text-align:left; padding:8px;'>Item</th><th style='text-align:left; padding:8px;'>Date</th><th style='text-align:left; padding:8px;'>Status</th></tr></thead><tbody>");
        if (recentActivities != null && !recentActivities.isEmpty()) {
            for (Map<String, Object> activity : recentActivities) {
                response.getWriter().println("<tr>");
                response.getWriter().println("<td style='padding:8px;'><span class='badge badge-primary'>" + activity.get("type") + "</span></td>");
                response.getWriter().println("<td style='padding:8px;'>" + activity.get("username") + "</td>");
                response.getWriter().println("<td style='padding:8px;'>" + activity.get("name") + "</td>");
                response.getWriter().println("<td style='padding:8px;'>" + activity.get("activityDate") + "</td>");
                response.getWriter().println("<td style='padding:8px;'><span class='badge badge-success'>" + activity.get("status") + "</span></td>");
                response.getWriter().println("</tr>");
            }
        } else {
            response.getWriter().println("<tr><td colspan='5' style='padding:8px; text-align:center;'>No recent activities</td></tr>");
        }
        response.getWriter().println("</tbody></table></div>");
        
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