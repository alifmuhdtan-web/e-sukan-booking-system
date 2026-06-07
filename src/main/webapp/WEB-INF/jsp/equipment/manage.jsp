<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.esukan.model.Facility" %>
<%
    List<Facility> facilities = (List<Facility>) request.getAttribute("facilities");
    Facility editFacility = (Facility) request.getAttribute("facility");
    Boolean isEdit = (Boolean) request.getAttribute("isEdit");
    String success = request.getParameter("success");
    String error = request.getParameter("error");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Facilities - E-Sukan</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; }
        .navbar { background: #667eea; padding: 15px 30px; color: white; display: flex; justify-content: space-between; }
        .navbar a { color: white; text-decoration: none; margin-left: 20px; }
        .container { max-width: 1200px; margin: 30px auto; background: white; padding: 20px; border-radius: 10px; }
        h1 { color: #333; }
        h2 { color: #555; margin: 20px 0 10px; }
        .form-container { background: #f7fafc; padding: 20px; border-radius: 10px; margin-bottom: 30px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input, select, textarea { width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 5px; }
        .row { display: flex; gap: 15px; }
        .row .form-group { flex: 1; }
        button { background: #28a745; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; }
        button:hover { background: #218838; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background: #667eea; color: white; }
        .btn-edit { background: #ffc107; color: #333; padding: 5px 10px; text-decoration: none; border-radius: 5px; }
        .btn-delete { background: #dc3545; color: white; padding: 5px 10px; text-decoration: none; border-radius: 5px; }
        .success { background: #d4edda; color: #155724; padding: 10px; border-radius: 5px; margin-bottom: 15px; }
        .error { background: #f8d7da; color: #721c24; padding: 10px; border-radius: 5px; margin-bottom: 15px; }
        .btn-cancel { background: #6c757d; margin-left: 10px; }
    </style>
</head>
<body>
    <div class="navbar">
        <div><strong>E-Sukan</strong> - Manage Facilities</div>
        <div><a href="${pageContext.request.contextPath}/manager/dashboard">Dashboard</a><a href="${pageContext.request.contextPath}/logout">Logout</a></div>
    </div>
    <div class="container">
        <h1>Facilities Management</h1>
        
        <% if (success != null) { %>
            <div class="success"><%= success %></div>
        <% } %>
        <% if (error != null) { %>
            <div class="error"><%= error %></div>
        <% } %>
        
        <!-- Add/Edit Form -->
        <div class="form-container">
            <h2><%= isEdit != null && isEdit ? "Edit Facility" : "Add New Facility" %></h2>
            <form action="${pageContext.request.contextPath}/manager/facilities" method="post">
                <input type="hidden" name="action" value="<%= isEdit != null && isEdit ? "update" : "create" %>">
                <% if (isEdit != null && isEdit && editFacility != null) { %>
                    <input type="hidden" name="facilityId" value="<%= editFacility.getFacilityId() %>">
                <% } %>
                
                <div class="form-group">
                    <label>Facility Name:</label>
                    <input type="text" name="facilityName" value="<%= editFacility != null ? editFacility.getFacilityName() : "" %>" required>
                </div>
                
                <div class="row">
                    <div class="form-group">
                        <label>Type:</label>
                        <select name="facilityType">
                            <option value="BADMINTON" <%= editFacility != null && editFacility.getFacilityType().toString().equals("BADMINTON") ? "selected" : "" %>>Badminton</option>
                            <option value="BASKETBALL" <%= editFacility != null && editFacility.getFacilityType().toString().equals("BASKETBALL") ? "selected" : "" %>>Basketball</option>
                            <option value="VOLLEYBALL" <%= editFacility != null && editFacility.getFacilityType().toString().equals("VOLLEYBALL") ? "selected" : "" %>>Volleyball</option>
                            <option value="TENNIS" <%= editFacility != null && editFacility.getFacilityType().toString().equals("TENNIS") ? "selected" : "" %>>Tennis</option>
                            <option value="FUTSAL" <%= editFacility != null && editFacility.getFacilityType().toString().equals("FUTSAL") ? "selected" : "" %>>Futsal</option>
                            <option value="GYM" <%= editFacility != null && editFacility.getFacilityType().toString().equals("GYM") ? "selected" : "" %>>Gym</option>
                            <option value="SWIMMING" <%= editFacility != null && editFacility.getFacilityType().toString().equals("SWIMMING") ? "selected" : "" %>>Swimming</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Location:</label>
                        <input type="text" name="location" value="<%= editFacility != null ? editFacility.getLocation() : "" %>" required>
                    </div>
                </div>
                
                <div class="form-group">
                    <label>Description:</label>
                    <textarea name="description" rows="2"><%= editFacility != null ? editFacility.getDescription() : "" %></textarea>
                </div>
                
                <div class="row">
                    <div class="form-group">
                        <label>Capacity:</label>
                        <input type="number" name="capacity" value="<%= editFacility != null ? editFacility.getCapacity() : "10" %>" required>
                    </div>
                    <div class="form-group">
                        <label>Hourly Rate (RM):</label>
                        <input type="number" step="0.01" name="hourlyRate" value="<%= editFacility != null ? editFacility.getHourlyRate() : "0" %>" required>
                    </div>
                </div>
                
                <div class="row">
                    <div class="form-group">
                        <label>Opening Time:</label>
                        <input type="time" name="openingTime" value="<%= editFacility != null ? editFacility.getOpeningTime() : "08:00" %>" required>
                    </div>
                    <div class="form-group">
                        <label>Closing Time:</label>
                        <input type="time" name="closingTime" value="<%= editFacility != null ? editFacility.getClosingTime() : "22:00" %>" required>
                    </div>
                </div>
                
                <% if (isEdit != null && isEdit) { %>
                    <div class="form-group">
                        <label>Status:</label>
                        <select name="isAvailable">
                            <option value="true" <%= editFacility != null && editFacility.isAvailable() ? "selected" : "" %>>Available</option>
                            <option value="false" <%= editFacility != null && !editFacility.isAvailable() ? "selected" : "" %>>Maintenance</option>
                        </select>
                    </div>
                <% } %>
                
                <button type="submit"><%= isEdit != null && isEdit ? "Update Facility" : "Create Facility" %></button>
                <% if (isEdit != null && isEdit) { %>
                    <a href="${pageContext.request.contextPath}/manager/facilities" class="btn-cancel" style="display: inline-block; padding: 10px 20px; background: #6c757d; color: white; text-decoration: none; border-radius: 5px;">Cancel</a>
                <% } %>
            </form>
        </div>
        
        <!-- Facilities List -->
        <h2>Existing Facilities</h2>
        <table>
            <thead>
                <tr><th>ID</th><th>Name</th><th>Type</th><th>Location</th><th>Rate</th><th>Status</th><th>Actions</th></tr>
            </thead>
            <tbody>
                <% if (facilities != null) {
                    for (Facility f : facilities) { %>
                    <tr>
                        <td><%= f.getFacilityId() %></td>
                        <td><%= f.getFacilityName() %></td>
                        <td><%= f.getFacilityType() %></td>
                        <td><%= f.getLocation() %></td>
                        <td>RM <%= f.getHourlyRate() %></td>
                        <td><%= f.isAvailable() ? "Available" : "Maintenance" %></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/manager/facilities?action=edit&id=<%= f.getFacilityId() %>" class="btn-edit">Edit</a>
                            <a href="${pageContext.request.contextPath}/manager/facilities?action=delete&id=<%= f.getFacilityId() %>" class="btn-delete" onclick="return confirm('Delete this facility?')">Delete</a>
                        </td>
                    </tr>
                <% } } %>
            </tbody>
        </table>
    </div>
</body>
</html>