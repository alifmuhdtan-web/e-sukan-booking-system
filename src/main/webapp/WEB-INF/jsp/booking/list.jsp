<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.esukan.model.Booking" %>
<%@ page import="com.esukan.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    List<Booking> bookingList = (List<Booking>) request.getAttribute("bookingList");
    String success = (String) request.getAttribute("success");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Libang Libu - My Bookings</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modern.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">
</head>
<body>
    <nav class="navbar">
        <div class="nav-container">
            <div class="nav-brand">
                <img src="${pageContext.request.contextPath}/assets/logo.png" alt="Libang Libu" class="nav-logo">
                <div class="nav-brand-text">
                    <span class="nav-brand-name">Libang Libu</span>
                    <span class="nav-brand-sub">E-Sukan System</span>
                </div>
            </div>
            <div class="nav-menu">
                <a href="${pageContext.request.contextPath}/student/dashboard" class="nav-link"><i class="fas fa-tachometer-alt"></i> Dashboard</a>
                <a href="${pageContext.request.contextPath}/facilities" class="nav-link"><i class="fas fa-building"></i> Facilities</a>
                <a href="${pageContext.request.contextPath}/equipment" class="nav-link"><i class="fas fa-dumbbell"></i> Equipment</a>
                <div class="user-dropdown">
                    <button class="user-btn">
                        <i class="fas fa-user-circle"></i> <span><%= user.getUsername() %></span> <i class="fas fa-chevron-down"></i>
                    </button>
                    <div class="dropdown-menu">
                        <a href="${pageContext.request.contextPath}/profile"><i class="fas fa-user"></i> Profile</a>
                        <a href="${pageContext.request.contextPath}/my-rentals"><i class="fas fa-box"></i> My Rentals</a>
                        <hr>
                        <a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a>
                    </div>
                </div>
            </div>
        </div>
    </nav>
    
    <main class="container">
        <div class="fade-in-up">
            <h1><i class="fas fa-calendar-alt"></i> My Bookings</h1>
            <p class="text-secondary" style="margin-bottom: 24px;">View and manage your facility bookings</p>
            
            <% if (success != null) { %>
                <div style="background: #D1FAE5; color: #065F46; padding: 12px 16px; border-radius: 8px; margin-bottom: 20px; border-left: 4px solid #10B981;">
                    <i class="fas fa-check-circle"></i> <%= success %>
                </div>
            <% } %>
            
            <% if (error != null) { %>
                <div style="background: #FEE2E2; color: #991B1B; padding: 12px 16px; border-radius: 8px; margin-bottom: 20px; border-left: 4px solid #EF4444;">
                    <i class="fas fa-exclamation-circle"></i> <%= error %>
                </div>
            <% } %>
            
            <% if (bookingList == null || bookingList.isEmpty()) { %>
                <div style="text-align: center; padding: 60px; background: white; border-radius: 16px;">
                    <i class="fas fa-calendar-times" style="font-size: 48px; color: #94A3B8; margin-bottom: 16px; display: block;"></i>
                    <h3>No bookings yet</h3>
                    <p style="color: #64748B;">Book a facility to get started</p>
                    <a href="${pageContext.request.contextPath}/facilities" class="btn btn-primary" style="margin-top: 16px;"><i class="fas fa-calendar-plus"></i> Browse Facilities</a>
                </div>
            <% } else { %>
                <div style="overflow-x: auto; background: white; border-radius: 16px; box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Reference</th>
                                <th>Facility</th>
                                <th>Date</th>
                                <th>Time</th>
                                <th>Cost (RM)</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Booking booking : bookingList) { %>
                                <tr>
                                    <td><%= booking.getId() %></td>
                                    <td><%= booking.getBookingReference() != null ? booking.getBookingReference() : "N/A" %></td>
                                    <td><%= booking.getFacilityName() != null ? booking.getFacilityName() : "Facility " + booking.getFacilityId() %></td>
                                    <td><%= booking.getBookingDate() %></td>
                                    <td><%= booking.getStartTime() %> - <%= booking.getEndTime() %></td>
                                    <td>RM <%= booking.getTotalCost() %></td>
                                    <td>
                                        <span class="badge <%= "CONFIRMED".equals(booking.getStatus()) ? "badge-success" : "badge-warning" %>">
                                            <%= booking.getStatus() %>
                                        </span>
                                    </td>
                                    <td>
                                        <% if ("CONFIRMED".equals(booking.getStatus())) { %>
                                            <a href="${pageContext.request.contextPath}/CancelBookingServlet?id=<%= booking.getId() %>" 
                                               class="btn btn-danger btn-sm" 
                                               onclick="return confirm('Are you sure you want to cancel this booking?')">
                                                <i class="fas fa-times"></i> Cancel
                                            </a>
                                        <% } else { %>
                                            <span class="text-secondary">-</span>
                                        <% } %>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            <% } %>
        </div>
        
        <div class="footer">
            <div class="footer-logo">
                <img src="${pageContext.request.contextPath}/assets/logo.png" alt="Libang Libu" class="footer-logo-img">
                <span>Libang Libu - E-Sukan System</span>
            </div>
            <p>© 2026 Libang Libu - All Rights Reserved.</p>
        </div>
    </main>
    
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>