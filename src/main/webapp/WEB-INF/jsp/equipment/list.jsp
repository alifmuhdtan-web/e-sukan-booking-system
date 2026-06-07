<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.esukan.model.Equipment" %>
<%@ page import="com.esukan.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    List<Equipment> equipmentList = (List<Equipment>) request.getAttribute("equipmentList");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Libang Libu - Equipment Rental</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modern.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
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
                <a href="${pageContext.request.contextPath}/student/dashboard" class="nav-link"><i class="fas fa-tachometer-alt"></i> Dashboard</a>
                <a href="${pageContext.request.contextPath}/facilities" class="nav-link"><i class="fas fa-building"></i> Facilities</a>
                <a href="${pageContext.request.contextPath}/equipment" class="nav-link active"><i class="fas fa-dumbbell"></i> Equipment</a>
                <div class="user-dropdown">
                    <button class="user-btn">
                        <i class="fas fa-user-circle"></i> <span><%= user.getUsername() %></span> <i class="fas fa-chevron-down"></i>
                    </button>
                    <div class="dropdown-menu">
                        <a href="${pageContext.request.contextPath}/profile"><i class="fas fa-user"></i> Profile</a>
                        <a href="${pageContext.request.contextPath}/my-rentals"><i class="fas fa-box"></i> My Rentals</a>
                        <hr>
                        <a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a>
                    </div>
                </div>
            </div>
        </div>
    </nav>
    
    <main class="container">
        <div class="fade-in-up">
            <h1><i class="fas fa-dumbbell"></i> Equipment Rental</h1>
            <p style="color: #64748B; margin-bottom: 24px;">Rent sports equipment for your activities</p>
            
            <div class="stats-grid">
                <% if (equipmentList == null || equipmentList.isEmpty()) { %>
                    <div style="text-align: center; padding: 60px; background: white; border-radius: 16px;">
                        <i class="fas fa-box-open" style="font-size: 48px; color: #94A3B8; margin-bottom: 16px; display: block;"></i>
                        <h3>No equipment available</h3>
                        <p style="color: #64748B;">Check back later for equipment rentals</p>
                    </div>
                <% } else { %>
                    <% for (Equipment eq : equipmentList) { %>
                        <div class="stat-card">
                            <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 16px;">
                                <span class="badge badge-primary"><%= eq.getEquipmentType() %></span>
                                <span class="badge badge-success"><%= eq.getQuantityAvailable() %> available</span>
                            </div>
                            <h3><%= eq.getEquipmentName() %></h3>
                            <p style="color: #64748B; margin: 8px 0;">Condition: <span class="badge badge-info"><%= eq.getConditionStatus() %></span></p>
                            <p style="color: #64748B;">Total: <%= eq.getQuantityTotal() %> units</p>
                            <div style="margin-top: 16px; display: flex; justify-content: space-between; align-items: baseline;">
                                <div>
                                    <span class="stat-value" style="font-size: 1.25rem;">RM <%= eq.getDailyRate() %></span>
                                    <span style="color: #64748B;"> / day</span>
                                </div>
                                <div style="color: #64748B;">Deposit: RM <%= eq.getDepositAmount() %></div>
                            </div>
                            <a href="${pageContext.request.contextPath}/equipment/rent?id=<%= eq.getEquipmentId() %>" class="btn btn-primary btn-sm" style="margin-top: 16px; width: 100%;"><i class="fas fa-shopping-cart"></i> Rent Now →</a>
                        </div>
                    <% } %>
                <% } %>
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