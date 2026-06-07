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
    <title>Facility Details - E-Sukan</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f5f5f5; }
        .navbar { background: #667eea; padding: 15px 30px; color: white; display: flex; justify-content: space-between; }
        .navbar a { color: white; text-decoration: none; margin-left: 20px; }
        .container { max-width: 800px; margin: 50px auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #333; margin-bottom: 10px; }
        .facility-type { display: inline-block; background: #667eea; color: white; padding: 5px 10px; border-radius: 5px; font-size: 14px; margin: 10px 0; }
        .info-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin: 20px 0; }
        .info-item { background: #f7fafc; padding: 15px; border-radius: 5px; }
        .info-item label { font-weight: bold; color: #666; display: block; margin-bottom: 5px; }
        .info-item .value { font-size: 18px; color: #333; }
        .price { font-size: 24px; font-weight: bold; color: #38a169; margin: 20px 0; }
        .btn { display: inline-block; background: #667eea; color: white; padding: 12px 24px; text-decoration: none; border-radius: 5px; font-size: 16px; margin-top: 20px; }
        .btn:hover { background: #5a67d8; }
        .btn-secondary { background: #e2e8f0; color: #333; margin-left: 10px; }
        .btn-secondary:hover { background: #cbd5e0; }
        .error { background: #fed7d7; color: #c53030; padding: 15px; border-radius: 5px; text-align: center; }
        .footer { text-align: center; padding: 20px; color: #666; margin-top: 30px; }
    </style>
</head>
<body>
    <div class="navbar">
        <div><strong>E-Sukan</strong> - Facility Details</div>
        <div>
            <a href="${pageContext.request.contextPath}/facilities">Back to Facilities</a>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </div>
    </div>
    
    <div class="container">
        <% if (facility == null) { %>
            <div class="error">Facility not found.</div>
        <% } else { %>
            <h1><%= facility.getFacilityName() %></h1>
            <span class="facility-type"><%= facility.getFacilityType() %></span>
            
            <p style="margin: 15px 0; color: #666;"><%= facility.getDescription() != null ? facility.getDescription() : "No description available." %></p>
            
            <div class="info-grid">
                <div class="info-item">
                    <label>📍 Location</label>
                    <div class="value"><%= facility.getLocation() %></div>
                </div>
                <div class="info-item">
                    <label>👥 Capacity</label>
                    <div class="value"><%= facility.getCapacity() %> people</div>
                </div>
                <div class="info-item">
                    <label>🕐 Operating Hours</label>
                    <div class="value"><%= facility.getOpeningTime() %> - <%= facility.getClosingTime() %></div>
                </div>
                <div class="info-item">
                    <label>💰 Rate</label>
                    <div class="value">RM <%= facility.getHourlyRate() %> per hour</div>
                </div>
            </div>
            
            <div class="price">Estimated Cost: RM <%= facility.getHourlyRate() %> / hour</div>
            
            <a href="${pageContext.request.contextPath}/CreateBookingServlet?facilityId=<%= facility.getFacilityId() %>" class="btn">Book Now</a>
            <a href="${pageContext.request.contextPath}/facilities" class="btn btn-secondary">Back to List</a>
        <% } %>
    </div>
    
    <div class="footer">
        <p>E-Sukan - Campus Facility & Equipment Booking System</p>
    </div>
</body>
</html>