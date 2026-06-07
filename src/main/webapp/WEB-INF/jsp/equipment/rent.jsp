<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.esukan.model.Equipment" %>
<%
    Equipment equipment = (Equipment) request.getAttribute("equipment");
    String error = request.getParameter("error");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Rent Equipment - E-Sukan</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f5f5f5; margin: 0; padding: 0; }
        .navbar { background: #667eea; padding: 15px 30px; color: white; display: flex; justify-content: space-between; }
        .navbar a { color: white; text-decoration: none; margin-left: 20px; }
        .container { max-width: 500px; margin: 50px auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        h1 { color: #333; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input, select { width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 5px; }
        button { background: #667eea; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; width: 100%; }
        .error { color: red; background: #ffe0e0; padding: 10px; border-radius: 5px; margin-bottom: 15px; }
        .info { background: #e6f7ff; padding: 10px; border-radius: 5px; margin-bottom: 20px; }
    </style>
</head>
<body>
    <div class="navbar">
        <div><strong>E-Sukan</strong> - Rent Equipment</div>
        <div><a href="${pageContext.request.contextPath}/equipment">Back</a><a href="${pageContext.request.contextPath}/logout">Logout</a></div>
    </div>
    <div class="container">
        <h1>Rent Equipment</h1>
        <% if (error != null) { %>
            <div class="error"><%= error %></div>
        <% } %>
        <% if (equipment != null) { %>
            <div class="info">
                <strong><%= equipment.getEquipmentName() %></strong><br>
                Daily Rate: RM <%= equipment.getDailyRate() %><br>
                Deposit: RM <%= equipment.getDepositAmount() %><br>
                Available: <%= equipment.getQuantityAvailable() %> units
            </div>
            <form action="${pageContext.request.contextPath}/equipment/rent" method="post">
                <input type="hidden" name="equipmentId" value="<%= equipment.getEquipmentId() %>">
                <div class="form-group">
                    <label>Quantity:</label>
                    <input type="number" name="quantity" min="1" max="<%= equipment.getQuantityAvailable() %>" value="1" required>
                </div>
                <div class="form-group">
                    <label>Expected Return Date:</label>
                    <input type="date" name="expectedReturnDate" required>
                </div>
                <div class="form-group">
                    <label>Condition on Rental:</label>
                    <select name="conditionOnRental">
                        <option>Excellent</option><option>Good</option><option>Fair</option>
                    </select>
                </div>
                <button type="submit">Confirm Rental</button>
            </form>
        <% } else { %>
            <p>Equipment not found.</p>
        <% } %>
    </div>
</body>
</html>