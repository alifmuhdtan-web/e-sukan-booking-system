<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.esukan.model.Facility" %>
<%@ page import="com.esukan.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    List<Facility> facilities = (List<Facility>) request.getAttribute("facilities");
    Facility.FacilityType[] types = (Facility.FacilityType[]) request.getAttribute("facilityTypes");
    String title = (String) request.getAttribute("title");
    String keyword = (String) request.getAttribute("keyword");
    String selectedType = (String) request.getAttribute("selectedType");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Libang Libu - Facilities</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modern.css">
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
                <a href="${pageContext.request.contextPath}/student/dashboard" class="nav-link">Dashboard</a>
                <a href="${pageContext.request.contextPath}/facilities" class="nav-link active">Facilities</a>
                <a href="${pageContext.request.contextPath}/equipment" class="nav-link">Equipment</a>
                <div class="user-dropdown">
                    <button class="user-btn">
                        <span>User</span> <span><%= user.getUsername() %></span> <span>▼</span>
                    </button>
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
            <h1><%= title != null ? title : "Sports Facilities" %></h1>
            <p class="text-secondary" style="margin-bottom: var(--spacing-lg);">Book your favorite sports facility online</p>
            
            <!-- Search Bar -->
            <div class="card" style="margin-bottom: var(--spacing-lg);">
                <form action="${pageContext.request.contextPath}/facilities/search" method="get" style="display: flex; gap: var(--spacing-sm);">
                    <input type="text" name="keyword" class="form-input" placeholder="Search by facility name..." value="<%= keyword != null ? keyword : "" %>" style="flex: 1;">
                    <button type="submit" class="btn btn-primary">Search</button>
                </form>
            </div>
            
            <!-- Filter by Type -->
            <div style="display: flex; flex-wrap: wrap; gap: var(--spacing-sm); margin-bottom: var(--spacing-xl);">
                <a href="${pageContext.request.contextPath}/facilities" class="btn btn-outline btn-sm <%= selectedType == null ? "btn-primary" : "btn-outline" %>">All</a>
                <% for (Facility.FacilityType type : types) { %>
                    <a href="${pageContext.request.contextPath}/facilities/type?type=<%= type %>" 
                       class="btn btn-outline btn-sm <%= type.toString().equals(selectedType) ? "btn-primary" : "btn-outline" %>">
                        <%= type %>
                    </a>
                <% } %>
            </div>
            
            <!-- Facilities Grid -->
            <div class="stats-grid">
                <% if (facilities == null || facilities.isEmpty()) { %>
                    <div class="empty-state" style="grid-column: 1/-1;">
                        <h3>No facilities available</h3>
                        <p>Check back later for new facilities</p>
                    </div>
                <% } else { %>
                    <% for (Facility facility : facilities) { %>
                        <div class="stat-card" style="cursor: pointer;" onclick="window.location.href='${pageContext.request.contextPath}/facility/detail?id=<%= facility.getFacilityId() %>'">
                            <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: var(--spacing-md);">
                                <span class="badge badge-primary"><%= facility.getFacilityType() %></span>
                                <span class="badge badge-success">Available</span>
                            </div>
                            <h3><%= facility.getFacilityName() %></h3>
                            <p class="text-secondary" style="margin: var(--spacing-sm) 0;">Location: <%= facility.getLocation() %></p>
                            <p class="text-secondary">Capacity: <%= facility.getCapacity() %> people</p>
                            <p class="text-secondary">Hours: <%= facility.getOpeningTime() %> - <%= facility.getClosingTime() %></p>
                            <div style="margin-top: var(--spacing-md); display: flex; justify-content: space-between; align-items: center;">
                                <span class="stat-value" style="font-size: 1.5rem;">RM <%= facility.getHourlyRate() %></span>
                                <span class="text-secondary">/ hour</span>
                            </div>
                            <a href="${pageContext.request.contextPath}/facility/detail?id=<%= facility.getFacilityId() %>" class="btn btn-primary btn-sm" style="margin-top: var(--spacing-md); width: 100%;">View Details →</a>
                        </div>
                    <% } %>
                <% } %>
            </div>
        </div>
        
        <!-- Footer - Copyright 2026 -->
        <div class="footer">
            <div class="footer-logo">
                <img src="${pageContext.request.contextPath}/assets/logo.png" alt="Libang Libu" class="footer-logo-img">
                <span>Libang Libu - E-Sukan System</span>
            </div>
            <p>© 2026 Libang Libu - All Rights Reserved.</p>
        </div>
    </main>
    
    <script src="${pageContext.request.contextPath}/js/app.js'></script>
</body>
</html>