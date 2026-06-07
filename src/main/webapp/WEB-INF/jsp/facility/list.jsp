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
    <title>Facilities - E-Sukan</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f5f5f5; }
        .navbar { background: #667eea; padding: 15px 30px; color: white; display: flex; justify-content: space-between; align-items: center; }
        .navbar a { color: white; text-decoration: none; margin-left: 20px; }
        .container { max-width: 1200px; margin: 30px auto; padding: 0 20px; }
        h1 { color: #333; margin-bottom: 10px; }
        .subtitle { color: #666; margin-bottom: 20px; }
        .search-bar { display: flex; gap: 10px; margin-bottom: 20px; }
        .search-bar input { flex: 1; padding: 10px; border: 1px solid #ddd; border-radius: 5px; }
        .search-bar button { padding: 10px 20px; background: #667eea; color: white; border: none; border-radius: 5px; cursor: pointer; }
        .filter-buttons { display: flex; flex-wrap: wrap; gap: 10px; margin-bottom: 30px; }
        .filter-btn { background: #e2e8f0; padding: 8px 16px; border-radius: 5px; text-decoration: none; color: #333; transition: all 0.3s; }
        .filter-btn:hover, .filter-btn.active { background: #667eea; color: white; }
        .facilities-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(350px, 1fr)); gap: 20px; }
        .card { background: white; border-radius: 10px; overflow: hidden; box-shadow: 0 2px 10px rgba(0,0,0,0.1); transition: transform 0.3s; }
        .card:hover { transform: translateY(-5px); }
        .card-image { background: #667eea; height: 150px; display: flex; align-items: center; justify-content: center; color: white; font-size: 48px; }
        .card-info { padding: 20px; }
        .card-info h3 { color: #333; margin-bottom: 10px; }
        .facility-type { display: inline-block; background: #667eea; color: white; padding: 4px 8px; border-radius: 5px; font-size: 12px; margin-bottom: 10px; }
        .location, .capacity, .hours, .price { margin: 8px 0; color: #666; font-size: 14px; }
        .price { font-size: 18px; font-weight: bold; color: #38a169; }
        .btn { display: inline-block; background: #667eea; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; margin-top: 15px; text-align: center; }
        .btn:hover { background: #5a67d8; }
        .btn-outline { background: transparent; border: 2px solid #667eea; color: #667eea; }
        .btn-outline:hover { background: #667eea; color: white; }
        .footer { text-align: center; padding: 20px; color: #666; margin-top: 30px; }
        .empty { text-align: center; padding: 60px; color: #666; background: white; border-radius: 10px; }
    </style>
</head>
<body>
    <div class="navbar">
        <div><strong>E-Sukan</strong> - Facilities</div>
        <div>
            <a href="${pageContext.request.contextPath}/student/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/MyBookingsServlet">My Bookings</a>
            <a href="${pageContext.request.contextPath}/equipment">Rent Equipment</a>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </div>
    </div>
    
    <div class="container">
        <h1><%= title != null ? title : "Sports Facilities" %></h1>
        <p class="subtitle">Book your favorite sports facility online</p>
        
        <!-- Search Bar -->
        <div class="search-bar">
            <form action="${pageContext.request.contextPath}/facilities/search" method="get" style="display: flex; gap: 10px; width: 100%;">
                <input type="text" name="keyword" placeholder="Search by facility name..." value="<%= keyword != null ? keyword : "" %>">
                <button type="submit">Search</button>
            </form>
        </div>
        
        <!-- Filter by Type -->
        <div class="filter-buttons">
            <a href="${pageContext.request.contextPath}/facilities" class="filter-btn <%= selectedType == null ? "active" : "" %>">All</a>
            <% for (Facility.FacilityType type : types) { %>
                <a href="${pageContext.request.contextPath}/facilities/type?type=<%= type %>" 
                   class="filter-btn <%= type.toString().equals(selectedType) ? "active" : "" %>">
                    <%= type %>
                </a>
            <% } %>
        </div>
        
        <!-- Facilities Grid -->
        <div class="facilities-grid">
            <% if (facilities == null || facilities.isEmpty()) { %>
                <div class="empty">
                    <p>No facilities available at the moment.</p>
                    <a href="${pageContext.request.contextPath}/facilities" class="btn btn-outline" style="margin-top: 20px;">View All Facilities</a>
                </div>
            <% } else { %>
                <% for (Facility facility : facilities) { %>
                    <div class="card">
                        <div class="card-image">
                            🏟️
                        </div>
                        <div class="card-info">
                            <span class="facility-type"><%= facility.getFacilityType() %></span>
                            <h3><%= facility.getFacilityName() %></h3>
                            <p class="location">📍 <%= facility.getLocation() %></p>
                            <p class="capacity">👥 Capacity: <%= facility.getCapacity() %> people</p>
                            <p class="hours">🕐 <%= facility.getOpeningTime() %> - <%= facility.getClosingTime() %></p>
                            <p class="price">RM <%= facility.getHourlyRate() %> / hour</p>
                            <a href="${pageContext.request.contextPath}/facility/detail?id=<%= facility.getFacilityId() %>" class="btn">View Details</a>
                        </div>
                    </div>
                <% } %>
            <% } %>
        </div>
    </div>
    
    <div class="footer">
        <p>E-Sukan - Campus Facility & Equipment Booking System</p>
    </div>
</body>
</html>