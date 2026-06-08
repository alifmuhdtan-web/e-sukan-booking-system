<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.esukan.model.Facility" %>
<%@ page import="com.esukan.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    List<Facility> facilities = (List<Facility>) request.getAttribute("facilities");
    Facility editFacility = (Facility) request.getAttribute("facility");
    Boolean isEdit = (Boolean) request.getAttribute("isEdit");
    String success = request.getParameter("success");
    String error = request.getParameter("error");
    
    // Format time for display (HH:MM)
    String openingTimeValue = "08:00";
    String closingTimeValue = "22:00";
    if (editFacility != null && editFacility.getOpeningTime() != null) {
        String timeStr = editFacility.getOpeningTime().toString();
        openingTimeValue = timeStr.length() >= 5 ? timeStr.substring(0, 5) : "08:00";
    }
    if (editFacility != null && editFacility.getClosingTime() != null) {
        String timeStr = editFacility.getClosingTime().toString();
        closingTimeValue = timeStr.length() >= 5 ? timeStr.substring(0, 5) : "22:00";
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Libang Libu - Manage Facilities</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modern.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">
    <style>
        .form-row {
            display: flex;
            gap: 20px;
            margin-bottom: 0;
        }
        .form-row .form-group {
            flex: 1;
            margin-bottom: 20px;
        }
        @media (max-width: 768px) {
            .form-row {
                flex-direction: column;
                gap: 0;
            }
        }
    </style>
</head>
<body>
    <nav class="navbar">
        <div class="nav-container">
            <div class="nav-brand">
                <img src="${pageContext.request.contextPath}/assets/logo.png" alt="Libang Libu" class="nav-logo">
                <div class="nav-brand-text">
                    <span class="nav-brand-name">Libang Libu</span>
                    <span class="nav-brand-sub">E-Sukan System</span>
                </div>
            </div>
            <div class="nav-menu">
                <a href="${pageContext.request.contextPath}/manager/dashboard" class="nav-link"><i class="fas fa-tachometer-alt"></i> Dashboard</a>
                <a href="${pageContext.request.contextPath}/manager/facilities" class="nav-link active"><i class="fas fa-building"></i> Facilities</a>
                <a href="${pageContext.request.contextPath}/manager/equipment" class="nav-link"><i class="fas fa-dumbbell"></i> Equipment</a>
                <div class="user-dropdown">
                    <button class="user-btn">
                        <i class="fas fa-user-circle"></i> <span><%= user.getUsername() %></span> <i class="fas fa-chevron-down"></i>
                    </button>
                    <div class="dropdown-menu">
                        <a href="${pageContext.request.contextPath}/profile"><i class="fas fa-user"></i> Profile</a>
                        <hr>
                        <a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a>
                    </div>
                </div>
            </div>
        </div>
    </nav>
    
    <main class="container">
        <div class="fade-in-up">
            <h1><i class="fas fa-building"></i> Facilities Management</h1>
            
            <% if (success != null) { %>
                <div style="background: #D1FAE5; color: #065F46; padding: 12px 16px; border-radius: 8px; margin-bottom: 20px; border-left: 4px solid #10B981;">
                    <i class="fas fa-check-circle"></i> <%= success %>
                </div>
            <% } %>
            <% if (error != null) { %>
                <div style="background: #FEE2E2; color: #991B1B; padding: 12px 16px; border-radius: 8px; margin-bottom: 20px; border-left: 4px solid #EF4444;">
                    <i class="fas fa-exclamation-circle"></i> <%= error %>
                </div>
            <% } %>
            
            <!-- Add/Edit Form -->
            <div style="background: white; border-radius: 16px; padding: 24px; box-shadow: 0 1px 3px rgba(0,0,0,0.1); margin-bottom: 32px;">
                <h2 style="margin-bottom: 20px;"><%= isEdit != null && isEdit ? "Edit Facility" : "Add New Facility" %></h2>
                <form action="${pageContext.request.contextPath}/manager/facilities" method="post">
                    <input type="hidden" name="action" value="<%= isEdit != null && isEdit ? "update" : "create" %>">
                    <% if (isEdit != null && isEdit && editFacility != null) { %>
                        <input type="hidden" name="facilityId" value="<%= editFacility.getFacilityId() %>">
                    <% } %>
                    
                    <div class="form-group">
                        <label class="form-label"><i class="fas fa-font"></i> Facility Name <span style="color: red;">*</span></label>
                        <input type="text" name="facilityName" class="form-input" value="<%= editFacility != null ? editFacility.getFacilityName() : "" %>" required>
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label"><i class="fas fa-tag"></i> Type <span style="color: red;">*</span></label>
                            <select name="facilityType" class="form-select" required>
                                <option value="">Select Type</option>
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
                            <label class="form-label"><i class="fas fa-map-marker-alt"></i> Location <span style="color: red;">*</span></label>
                            <input type="text" name="location" class="form-input" value="<%= editFacility != null ? editFacility.getLocation() : "" %>" required>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label"><i class="fas fa-align-left"></i> Description</label>
                        <textarea name="description" class="form-textarea" rows="2"><%= editFacility != null ? editFacility.getDescription() : "" %></textarea>
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label"><i class="fas fa-users"></i> Capacity <span style="color: red;">*</span></label>
                            <input type="number" name="capacity" class="form-input" value="<%= editFacility != null ? editFacility.getCapacity() : "10" %>" required>
                        </div>
                        <div class="form-group">
                            <label class="form-label"><i class="fas fa-dollar-sign"></i> Hourly Rate (RM) <span style="color: red;">*</span></label>
                            <input type="number" step="0.01" name="hourlyRate" class="form-input" value="<%= editFacility != null ? editFacility.getHourlyRate() : "0" %>" required>
                        </div>
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label"><i class="fas fa-clock"></i> Opening Time <span style="color: red;">*</span></label>
                            <input type="time" name="openingTime" class="form-input" 
                                   value="<%= openingTimeValue %>" required>
                        </div>
                        <div class="form-group">
                            <label class="form-label"><i class="fas fa-clock"></i> Closing Time <span style="color: red;">*</span></label>
                            <input type="time" name="closingTime" class="form-input" 
                                   value="<%= closingTimeValue %>" required>
                        </div>
                    </div>
                    
                    <% if (isEdit != null && isEdit) { %>
                        <div class="form-group">
                            <label class="form-label"><i class="fas fa-toggle-on"></i> Status</label>
                            <select name="isAvailable" class="form-select">
                                <option value="true" <%= editFacility != null && editFacility.isAvailable() ? "selected" : "" %>>Available</option>
                                <option value="false" <%= editFacility != null && !editFacility.isAvailable() ? "selected" : "" %>>Maintenance</option>
                            </select>
                        </div>
                    <% } %>
                    
                    <div style="display: flex; gap: 16px; margin-top: 24px;">
                        <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> <%= isEdit != null && isEdit ? "Update Facility" : "Create Facility" %></button>
                        <% if (isEdit != null && isEdit) { %>
                            <a href="${pageContext.request.contextPath}/manager/facilities" class="btn btn-outline"><i class="fas fa-times"></i> Cancel</a>
                        <% } %>
                    </div>
                </form>
            </div>
            
            <!-- Facilities List -->
            <h2><i class="fas fa-list"></i> Existing Facilities</h2>
            <div style="overflow-x: auto; background: white; border-radius: 16px; box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
                <table style="width: 100%; border-collapse: collapse;">
                    <thead>
                        <tr style="background: #F1F5F9;">
                            <th style="padding: 12px; text-align: left;">ID</th>
                            <th style="padding: 12px; text-align: left;">Name</th>
                            <th style="padding: 12px; text-align: left;">Type</th>
                            <th style="padding: 12px; text-align: left;">Location</th>
                            <th style="padding: 12px; text-align: left;">Rate</th>
                            <th style="padding: 12px; text-align: left;">Status</th>
                            <th style="padding: 12px; text-align: left;">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (facilities != null) {
                            for (Facility f : facilities) { %>
                                <tr style="border-bottom: 1px solid #E2E8F0;">
                                    <td style="padding: 12px;"><%= f.getFacilityId() %></td>
                                    <td style="padding: 12px;"><%= f.getFacilityName() %></td>
                                    <td style="padding: 12px;"><%= f.getFacilityType() %></td>
                                    <td style="padding: 12px;"><%= f.getLocation() %></td>
                                    <td style="padding: 12px;">RM <%= f.getHourlyRate() %></td>
                                    <td style="padding: 12px;">
                                        <span style="background: <%= f.isAvailable() ? "#D1FAE5" : "#FEF3C7" %>; color: <%= f.isAvailable() ? "#065F46" : "#92400E" %>; padding: 4px 12px; border-radius: 20px; font-size: 12px;">
                                            <%= f.isAvailable() ? "Available" : "Maintenance" %>
                                        </span>
                                    </td>
                                    <td style="padding: 12px;">
                                        <a href="${pageContext.request.contextPath}/manager/facilities?action=edit&id=<%= f.getFacilityId() %>" class="btn btn-outline btn-sm"><i class="fas fa-edit"></i> Edit</a>
                                        <a href="${pageContext.request.contextPath}/manager/facilities?action=delete&id=<%= f.getFacilityId() %>" class="btn btn-danger btn-sm" onclick="return confirm('Delete this facility?')" style="margin-left: 8px;"><i class="fas fa-trash"></i> Delete</a>
                                    </td>
                                </tr>
                        <% } } %>
                    </tbody>
                </table>
            </div>
        </div>
        
        <div class="footer">
            <div class="footer-logo">
                <img src="${pageContext.request.contextPath}/assets/logo.png" alt="Libang Libu" class="footer-logo-img">
                <span>Libang Libu - E-Sukan System</span>
            </div>
            <p>© 2026 Libang Libu - All Rights Reserved.</p>
        </div>
    </main>
    
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>