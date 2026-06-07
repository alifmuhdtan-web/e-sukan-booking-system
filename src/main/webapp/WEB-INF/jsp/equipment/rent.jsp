<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.esukan.model.Equipment" %>
<%@ page import="com.esukan.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    Equipment equipment = (Equipment) request.getAttribute("equipment");
    String error = request.getParameter("error");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Libang Libu - Rent Equipment</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modern.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/logo.png">
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
                <a href="${pageContext.request.contextPath}/student/dashboard" class="nav-link">Dashboard</a>
                <a href="${pageContext.request.contextPath}/equipment" class="nav-link">Equipment</a>
                <div class="user-dropdown">
                    <button class="user-btn">👤 <span><%= user.getUsername() %></span> ▼</button>
                    <div class="dropdown-menu">
                        <a href="${pageContext.request.contextPath}/profile">Profile</a>
                        <a href="${pageContext.request.contextPath}/my-rentals">My Rentals</a>
                        <hr>
                        <a href="${pageContext.request.contextPath}/logout">Logout</a>
                    </div>
                </div>
            </div>
        </div>
    </nav>
    
    <main class="container">
        <div class="fade-in-up">
            <div class="card" style="max-width: 600px; margin: 0 auto;">
                <h1>Rent Equipment</h1>
                
                <% if (error != null) { %>
                    <div class="toast toast-error" style="margin-bottom: var(--spacing-lg); animation: none;">
                        <span class="toast-icon">✗</span>
                        <span class="toast-message"><%= error %></span>
                    </div>
                <% } %>
                
                <% if (equipment != null) { %>
                    <div class="card" style="background: var(--surface-hover); margin-bottom: var(--spacing-lg);">
                        <h3><%= equipment.getEquipmentName() %></h3>
                        <p>Daily Rate: <strong>RM <%= equipment.getDailyRate() %></strong></p>
                        <p>Deposit: RM <%= equipment.getDepositAmount() %></p>
                        <p>Available: <%= equipment.getQuantityAvailable() %> units</p>
                        <p>Condition: <span class="badge badge-info"><%= equipment.getConditionStatus() %></span></p>
                    </div>
                    
                    <form action="${pageContext.request.contextPath}/equipment/rent" method="post">
                        <input type="hidden" name="equipmentId" value="<%= equipment.getEquipmentId() %>">
                        
                        <div class="form-group">
                            <label class="form-label">Quantity:</label>
                            <input type="number" name="quantity" class="form-input" min="1" max="<%= equipment.getQuantityAvailable() %>" value="1" required>
                        </div>
                        
                        <div class="form-group">
                            <label class="form-label">Expected Return Date:</label>
                            <input type="date" name="expectedReturnDate" class="form-input" required>
                        </div>
                        
                        <div class="form-group">
                            <label class="form-label">Condition on Rental:</label>
                            <select name="conditionOnRental" class="form-select">
                                <option value="Excellent">Excellent</option>
                                <option value="Good">Good</option>
                                <option value="Fair">Fair</option>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label class="form-label">Notes (Optional):</label>
                            <textarea name="notes" class="form-textarea" rows="3"></textarea>
                        </div>
                        
                        <div style="display: flex; gap: var(--spacing-md); margin-top: var(--spacing-lg);">
                            <button type="submit" class="btn btn-primary">Confirm Rental</button>
                            <a href="${pageContext.request.contextPath}/equipment" class="btn btn-outline">Cancel</a>
                        </div>
                    </form>
                <% } else { %>
                    <div class="empty-state">
                        <div class="empty-icon">🏸</div>
                        <h3>Equipment not found</h3>
                        <a href="${pageContext.request.contextPath}/equipment" class="btn btn-primary">Browse Equipment</a>
                    </div>
                <% } %>
            </div>
        </div>
        
        <div class="footer">
            <div class="footer-logo">
                <img src="${pageContext.request.contextPath}/assets/logo.png" alt="Libang Libu" class="footer-logo-img">
                <span>Libang Libu - E-Sukan System</span>
            </div>
            <p>© 2024 Libang Libu - All Rights Reserved.</p>
        </div>
    </main>
    
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>