<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String facilityId = (String) request.getAttribute("facilityId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Book Facility - E-Sukan</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background: #f5f5f5; }
        .navbar { background: #667eea; padding: 15px 30px; color: white; }
        .navbar a { color: white; text-decoration: none; margin-left: 20px; float: right; }
        .container { max-width: 500px; margin: 50px auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #333; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input, select { width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 5px; }
        button { background: #667eea; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; width: 100%; }
        .error { color: red; }
    </style>
</head>
<body>
    <div class="navbar">
        <strong>🏟️ E-Sukan</strong>
        <a href="logout">Logout</a>
        <a href="MyBookingsServlet">My Bookings</a>
    </div>
    <div class="container">
        <h1>Book Facility</h1>
        
        <% if (request.getParameter("error") != null) { %>
            <p class="error"><%= request.getParameter("error") %></p>
        <% } %>
        
        <form action="CreateBookingServlet" method="post">
            <input type="hidden" name="facilityId" value="<%= facilityId %>">
            
            <div class="form-group">
                <label>Booking Date:</label>
                <input type="date" name="bookingDate" required>
            </div>
            
            <div class="form-group">
                <label>Start Time:</label>
                <select name="startTime" required>
                    <option value="08:00">08:00</option>
                    <option value="09:00">09:00</option>
                    <option value="10:00">10:00</option>
                    <option value="11:00">11:00</option>
                    <option value="12:00">12:00</option>
                    <option value="13:00">13:00</option>
                    <option value="14:00">14:00</option>
                    <option value="15:00">15:00</option>
                    <option value="16:00">16:00</option>
                    <option value="17:00">17:00</option>
                    <option value="18:00">18:00</option>
                    <option value="19:00">19:00</option>
                    <option value="20:00">20:00</option>
                    <option value="21:00">21:00</option>
                </select>
            </div>
            
            <div class="form-group">
                <label>End Time:</label>
                <select name="endTime" required>
                    <option value="09:00">09:00</option>
                    <option value="10:00">10:00</option>
                    <option value="11:00">11:00</option>
                    <option value="12:00">12:00</option>
                    <option value="13:00">13:00</option>
                    <option value="14:00">14:00</option>
                    <option value="15:00">15:00</option>
                    <option value="16:00">16:00</option>
                    <option value="17:00">17:00</option>
                    <option value="18:00">18:00</option>
                    <option value="19:00">19:00</option>
                    <option value="20:00">20:00</option>
                    <option value="21:00">21:00</option>
                    <option value="22:00">22:00</option>
                </select>
            </div>
            
            <div class="form-group">
                <label>Total Cost (RM):</label>
                <input type="number" name="totalCost" step="0.01" required>
            </div>
            
            <button type="submit">Confirm Booking</button>
        </form>
    </div>
</body>
</html>