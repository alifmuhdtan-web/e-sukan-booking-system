<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.esukan.model.Equipment" %>
<%
    List<Equipment> equipmentList = (List<Equipment>) request.getAttribute("equipmentList");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Equipment Rental - E-Sukan</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f5f5f5; margin: 0; padding: 0; }
        .navbar { background: #667eea; padding: 15px 30px; color: white; display: flex; justify-content: space-between; }
        .navbar a { color: white; text-decoration: none; margin-left: 20px; }
        .container { max-width: 1200px; margin: 30px auto; padding: 20px; }
        h1 { color: #333; }
        .grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 20px; }
        .card { background: white; border-radius: 10px; padding: 20px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        .card h3 { color: #667eea; }
        .price { font-size: 20px; font-weight: bold; color: #28a745; }
        .btn { background: #667eea; color: white; padding: 10px 15px; text-decoration: none; border-radius: 5px; display: inline-block; margin-top: 10px; }
        .footer { text-align: center; padding: 20px; color: #666; }
    </style>
</head>
<body>
    <div class="navbar">
        <div><strong>E-Sukan</strong> - Equipment Rental</div>
        <div>
            <a href="${pageContext.request.contextPath}/student/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/my-rentals">My Rentals</a>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </div>
    </div>
    <div class="container">
        <h1>Available Equipment for Rent</h1>
        <div class="grid">
            <% if (equipmentList != null && !equipmentList.isEmpty()) { %>
                <% for (Equipment eq : equipmentList) { %>
                    <div class="card">
                        <h3><%= eq.getEquipmentName() %></h3>
                        <p>Type: <%= eq.getEquipmentType() %></p>
                        <p>Available: <%= eq.getQuantityAvailable() %> / <%= eq.getQuantityTotal() %></p>
                        <p>Condition: <%= eq.getConditionStatus() %></p>
                        <p>Deposit: RM <%= eq.getDepositAmount() %></p>
                        <div class="price">RM <%= eq.getDailyRate() %> / day</div>
                        <a href="${pageContext.request.contextPath}/equipment/rent?id=<%= eq.getEquipmentId() %>" class="btn">Rent Now</a>
                    </div>
                <% } %>
            <% } else { %>
                <p>No equipment available at the moment.</p>
            <% } %>
        </div>
    </div>
    <div class="footer"><p>E-Sukan System</p></div>
</body>
</html>