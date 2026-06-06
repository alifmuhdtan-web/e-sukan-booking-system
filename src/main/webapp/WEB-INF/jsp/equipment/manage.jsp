<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.esukan.model.Equipment" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Equipment</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f6f9; padding: 20px; }
        .container { max-width: 900px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        form { background: #ecf0f1; padding: 15px; border-radius: 5px; margin-bottom: 20px; }
        input, select, button { padding: 8px; margin: 5px 0; width: 100%; box-sizing: border-box; }
        button { background: #2ecc71; color: white; border: none; font-weight: bold; cursor: pointer; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        th, td { padding: 10px; border: 1px solid #ddd; text-align: left; }
        th { background: #34495e; color: white; }
        .btn-delete { color: red; text-decoration: none; font-weight: bold; }
    </style>
</head>
<body>
<div class="container">
    <h2>E-Sports Equipment Management System</h2>
    
    <form action="<%= request.getContextPath() %>/manager/equipment" method="post">
        <h3>Add New Equipment</h3>
        <input type="text" name="name" placeholder="Equipment Name" required>
        <input type="text" name="type" placeholder="Type (e.g. Mouse/Keyboard/Monitor)" required>
        <input type="number" name="quantity" min="1" placeholder="Quantity" required>
        <select name="status">
            <option value="Available">Available</option>
            <option value="Maintenance">Maintenance</option>
        </select>
        <button type="submit">Add Equipment</button>
    </form>

    <h3>Equipment List</h3>
    <table>
        <thead>
            <tr>
                <th>ID</th><th>Name</th><th>Type</th><th>Quantity</th><th>Status</th><th>Action</th>
            </tr>
        </thead>
        <tbody>
            <%
                // Using standard Java code to read data without JSTL
                List<Equipment> equipmentList = (List<Equipment>) request.getAttribute("equipmentList");
                if (equipmentList != null && !equipmentList.isEmpty()) {
                    for (Equipment eq : equipmentList) {
            %>
                <tr>
                    <td><%= eq.getId() %></td>
                    <td><%= eq.getName() %></td>
                    <td><%= eq.getType() %></td>
                    <td><%= eq.getQuantity() %></td>
                    <td><%= eq.getStatus() %></td>
                    <td>
                        <a class="btn-delete" href="<%= request.getContextPath() %>/manager/equipment?action=delete&id=<%= eq.getId() %>" onclick="return confirm('Delete this equipment?')">Delete</a>
                    </td>
                </tr>
            <% 
                    }
                } else {
            %>
                <tr><td colspan="6" style="text-align:center;">No equipment data found.</td></tr>
            <% 
                } 
            %>
        </tbody>
    </table>
    <br>
    <a href="<%= request.getContextPath() %>/storyboard/manager-dashboard.html">← Back to Dashboard</a>
</div>
</body>
</html>