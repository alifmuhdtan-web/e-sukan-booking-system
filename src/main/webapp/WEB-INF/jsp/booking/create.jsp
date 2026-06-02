<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String facilityId = request.getParameter("facilityId");
    if (facilityId == null) {
        facilityId = (String) request.getAttribute("facilityId");
    }
    String error = request.getParameter("error");
    if (error == null) {
        error = (String) request.getAttribute("error");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Facility - E-Sukan</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f5f5;
        }
        .navbar {
            background: #667eea;
            padding: 15px 30px;
            color: white;
            display: flex;
            justify-content: space-between;
        }
        .navbar a {
            color: white;
            text-decoration: none;
            margin-left: 20px;
        }
        .container {
            max-width: 600px;
            margin: 50px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            margin-bottom: 20px;
            text-align: center;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #555;
        }
        input, select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
        }
        input:focus, select:focus {
            outline: none;
            border-color: #667eea;
        }
        button {
            width: 100%;
            padding: 12px;
            background: #667eea;
            border: none;
            border-radius: 5px;
            color: white;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            margin-top: 10px;
        }
        button:hover {
            background: #5a67d8;
        }
        .error-message {
            background: #fed7d7;
            color: #c53030;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
            text-align: center;
        }
        .back-link {
            text-align: center;
            margin-top: 20px;
        }
        .back-link a {
            color: #667eea;
            text-decoration: none;
        }
        .info {
            background: #e6f7ff;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
            text-align: center;
            color: #0050b3;
        }
    </style>
</head>
<body>
    <div class="navbar">
        <div><strong>🏟️ E-Sukan</strong> - Book Facility</div>
        <div>
            <a href="${pageContext.request.contextPath}/student/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </div>
    </div>
    <div class="container">
        <h1>Book Facility</h1>
        
        <% if (error != null) { %>
            <div class="error-message">
                <strong>Error:</strong> <%= error %>
            </div>
        <% } %>
        
        <div class="info">
            💡 Rate: RM 15.00 per hour for Badminton Court
        </div>
        
        <form action="${pageContext.request.contextPath}/CreateBookingServlet" method="post">
            <input type="hidden" name="facilityId" value="<%= facilityId != null ? facilityId : "1" %>">
            
            <div class="form-group">
                <label>Booking Date:</label>
                <input type="date" name="bookingDate" required>
            </div>
            
            <div class="form-group">
                <label>Start Time:</label>
                <select name="startTime" required>
                    <option value="">Select start time</option>
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
                    <option value="">Select end time</option>
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
                <input type="number" name="totalCost" step="0.01" placeholder="e.g., 30.00" required>
            </div>
            
            <button type="submit">Confirm Booking</button>
        </form>
        
        <div class="back-link">
            <a href="${pageContext.request.contextPath}/student/dashboard">← Back to Dashboard</a>
        </div>
    </div>
</body>
</html>