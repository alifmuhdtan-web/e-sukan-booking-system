<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.esukan.model.Facility" %>
<%@ page import="com.esukan.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    Facility facility = (Facility) request.getAttribute("facility");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Libang Libu - Facility Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modern.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/logo.png">
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
                <a href="${pageContext.request.contextPath}/student/dashboard" class="nav-link">Dashboard</a>
                <a href="${pageContext.request.contextPath}/facilities" class="nav-link">Facilities</a>
                <a href="${pageContext.request.contextPath}/equipment" class="nav-link">Equipment</a>
                <div class="user-dropdown">
                    <button class="user-btn">👤 <span><%= user.getUsername() %></span> ▼</button>
                    <div class="dropdown-menu">
                        <a href="${pageContext.request.contextPath}/profile">Profile</a>
                        <a href="${pageContext.request.contextPath}/my-rentals">My Rentals</a>
                        <hr>
                        <a href="${pageContext.request.contextPath}/logout">Logout</a>
                    </div>
                </div>
            </div>
        </div>
    </nav>
    
    <main class="container">
        <div class="fade-in-up">
            <% if (facility == null) { %>
                <div class="empty-state">
                    <div class="empty-icon">🏟️</div>
                    <h3>Facility not found</h3>
                    <a href="${pageContext.request.contextPath}/facilities" class="btn btn-primary">Back to Facilities</a>
                </div>
            <% } else { %>
                <div class="card">
                    <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: var(--spacing-md);">
                        <span class="badge badge-primary"><%= facility.getFacilityType() %></span>
                        <span class="badge badge-success">Available</span>
                    </div>
                    <h1><%= facility.getFacilityName() %></h1>
                    <p class="text-secondary" style="margin: var(--spacing-md) 0;"><%= facility.getDescription() != null ? facility.getDescription() : "No description available." %></p>
                    
                    <div class="stats-grid" style="margin-top: var(--spacing-lg);">
                        <div class="stat-card">
                            <div class="stat-icon">📍</div>
                            <div class="stat-label">Location</div>
                            <div class="stat-value" style="font-size: 1rem;"><%= facility.getLocation() %></div>
                        </div>
                        <div class="stat-card">
                            <div class="stat-icon">👥</div>
                            <div class="stat-label">Capacity</div>
                            <div class="stat-value" style="font-size: 1rem;"><%= facility.getCapacity() %> people</div>
                        </div>
                        <div class="stat-card">
                            <div class="stat-icon">🕐</div>
                            <div class="stat-label">Operating Hours</div>
                            <div class="stat-value" style="font-size: 1rem;"><%= facility.getOpeningTime() %> - <%= facility.getClosingTime() %></div>
                        </div>
                        <div class="stat-card">
                            <div class="stat-icon">💰</div>
                            <div class="stat-label">Rate</div>
                            <div class="stat-value" style="font-size: 1rem;">RM <%= facility.getHourlyRate() %>/hour</div>
                        </div>
                    </div>
                    
                    <div style="display: flex; gap: var(--spacing-md); margin-top: var(--spacing-xl);">
                        <a href="${pageContext.request.contextPath}/CreateBookingServlet?facilityId=<%= facility.getFacilityId() %>" class="btn btn-primary">📅 Book Now</a>
                        <a href="${pageContext.request.contextPath}/facilities" class="btn btn-outline">← Back to Facilities</a>
                    </div>
                </div>
            <% } %>
        </div>
        
        <div class="footer">
            <div class="footer-logo">
                <img src="${pageContext.request.contextPath}/assets/logo.png" alt="Libang Libu" class="footer-logo-img">
                <span>Libang Libu - E-Sukan System</span>
            </div>
            <p>© 2024 Libang Libu - All Rights Reserved.</p>
        </div>
    </main>
    
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>