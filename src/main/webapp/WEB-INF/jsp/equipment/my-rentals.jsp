<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.esukan.model.EquipmentRental" %>
<%
    List<EquipmentRental> rentals = (List<EquipmentRental>) request.getAttribute("rentals");
    String success = (String) request.getAttribute("success");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <title>My Rentals - E-Sukan</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f5f5f5; margin: 0; padding: 0; }
        .navbar { background: #667eea; padding: 15px 30px; color: white; display: flex; justify-content: space-between; }
        .navbar a { color: white; text-decoration: none; margin-left: 20px; }
        .container { max-width: 1000px; margin: 30px auto; background: white; padding: 20px; border-radius: 10px; }
        h1 { color: #333; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background: #667eea; color: white; }
        .status-ACTIVE { color: green; font-weight: bold; }
        .status-RETURNED { color: blue; }
        .status-OVERDUE { color: red; font-weight: bold; }
        .success { background: #d4edda; color: #155724; padding: 10px; border-radius: 5px; margin-bottom: 15px; }
        .error { background: #f8d7da; color: #721c24; padding: 10px; border-radius: 5px; margin-bottom: 15px; }
        .btn { background: #667eea; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block; margin-top: 20px; }
    </style>
</head>
<body>
    <div class="navbar">
        <div><strong>E-Sukan</strong> - My Rentals</div>
        <div><a href="${pageContext.request.contextPath}/equipment">Rent Equipment</a><a href="${pageContext.request.contextPath}/logout">Logout</a></div>
    </div>
    <div class="container">
        <h1>My Equipment Rentals</h1>
        <% if (success != null) { %>
            <div class="success"><%= success %></div>
        <% } %>
        <% if (error != null) { %>
            <div class="error"><%= error %></div>
        <% } %>
        <% if (rentals == null || rentals.isEmpty()) { %>
            <p>You have no equipment rentals yet.</p>
            <a href="${pageContext.request.contextPath}/equipment" class="btn">Browse Equipment</a>
        <% } else { %>
            <table>
                <thead><tr><th>ID</th><th>Equipment</th><th>Quantity</th><th>Rental Date</th><th>Return By</th><th>Total</th><th>Deposit</th><th>Status</th></tr></thead>
                <tbody>
                    <% for (EquipmentRental rental : rentals) { %>
                        <tr class="status-<%= rental.getRentalStatus() %>">
                            <td><%= rental.getRentalId() %></td>
                            <td><%= rental.getEquipmentName() %></td>
                            <td><%= rental.getQuantityRented() %></td>
                            <td><%= rental.getRentalDate() %></td>
                            <td><%= rental.getExpectedReturnDate() %></td>
                            <td>RM <%= rental.getTotalAmount() %></td>
                            <td>RM <%= rental.getDepositPaid() %></td>
                            <td><%= rental.getRentalStatus() %></td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <% } %>
    </div>
</body>
</html>