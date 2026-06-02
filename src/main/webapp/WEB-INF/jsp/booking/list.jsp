<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.esukan.model.Booking" %>
<%
    List<Booking> bookingList = (List<Booking>) request.getAttribute("bookingList");
    String success = (String) request.getAttribute("success");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <title>My Bookings - E-Sukan</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background: #f5f5f5; }
        .navbar { background: #667eea; padding: 15px 30px; color: white; }
        .navbar a { color: white; text-decoration: none; margin-left: 20px; float: right; }
        .container { max-width: 1000px; margin: 30px auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #333; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
        th { background: #667eea; color: white; }
        .status-CONFIRMED { color: green; font-weight: bold; }
        .status-CANCELLED { color: red; font-weight: bold; }
        .status-COMPLETED { color: blue; font-weight: bold; }
        .btn-cancel { background: #dc3545; color: white; padding: 5px 10px; text-decoration: none; border-radius: 5px; font-size: 12px; }
        .btn-cancel:hover { background: #c82333; }
        .success { background: #d4edda; color: #155724; padding: 10px; border-radius: 5px; margin-bottom: 20px; }
        .error { background: #f8d7da; color: #721c24; padding: 10px; border-radius: 5px; margin-bottom: 20px; }
        .book-link { display: inline-block; background: #667eea; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; margin-top: 20px; }
        .empty { text-align: center; padding: 40px; color: #666; }
    </style>
</head>
<body>
    <div class="navbar">
        <strong>🏟️ E-Sukan</strong>
        <a href="logout">Logout</a>
        <a href="CreateBookingServlet?facilityId=1">New Booking</a>
    </div>
    <div class="container">
        <h1>My Bookings</h1>
        
        <% if (success != null) { %>
            <div class="success"><%= success %></div>
        <% } %>
        
        <% if (error != null) { %>
            <div class="error"><%= error %></div>
        <% } %>
        
        <% if (bookingList == null || bookingList.isEmpty()) { %>
            <div class="empty">
                <p>You have no bookings yet.</p>
                <a href="CreateBookingServlet?facilityId=1" class="book-link">Book a Facility</a>
            </div>
        <% } else { %>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
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
                        <td><%= booking.getFacilityName() %></td>
                        <td><%= booking.getBookingDate() %></td>
                        <td><%= booking.getStartTime() %> - <%= booking.getEndTime() %></td>
                        <td>RM <%= String.format("%.2f", booking.getTotalCost()) %></td>
                        <td class="status-<%= booking.getStatus() %>"><%= booking.getStatus() %></td>
                        <td>
                            <% if ("CONFIRMED".equals(booking.getStatus())) { %>
                                <a href="CancelBookingServlet?id=<%= booking.getId() %>" 
                                   class="btn-cancel" 
                                   onclick="return confirm('Are you sure you want to cancel this booking?')">Cancel</a>
                            <% } else { %>
                                -
                            <% } %>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        <% } %>
    </div>
</body>
</html>