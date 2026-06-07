<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.esukan.model.EquipmentRental" %>
<%@ page import="com.esukan.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    List<EquipmentRental> rentals = (List<EquipmentRental>) request.getAttribute("rentals");
    String success = (String) request.getAttribute("success");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Libang Libu - My Rentals</title>
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
                <a href="${pageContext.request.contextPath}/equipment" class="nav-link"><i class="fas fa-dumbbell"></i> Equipment</a>
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
            <h1><i class="fas fa-box"></i> My Equipment Rentals</h1>
            <p style="color: #64748B; margin-bottom: 24px;">View your rental history and active rentals</p>
            
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
            
            <% if (rentals == null || rentals.isEmpty()) { %>
                <div style="text-align: center; padding: 60px; background: white; border-radius: 16px;">
                    <i class="fas fa-box-open" style="font-size: 48px; color: #94A3B8; margin-bottom: 16px; display: block;"></i>
                    <h3>No rentals yet</h3>
                    <p style="color: #64748B;">Rent equipment to get started</p>
                    <a href="${pageContext.request.contextPath}/equipment" class="btn btn-primary" style="margin-top: 16px;"><i class="fas fa-dumbbell"></i> Browse Equipment</a>
                </div>
            <% } else { %>
                <div style="overflow-x: auto; background: white; border-radius: 16px; box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
                    <table style="width: 100%; border-collapse: collapse;">
                        <thead>
                            <tr style="background: #F1F5F9;">
                                <th style="padding: 12px; text-align: left;">ID</th>
                                <th style="padding: 12px; text-align: left;">Equipment</th>
                                <th style="padding: 12px; text-align: left;">Quantity</th>
                                <th style="padding: 12px; text-align: left;">Rental Date</th>
                                <th style="padding: 12px; text-align: left;">Return By</th>
                                <th style="padding: 12px; text-align: left;">Total</th>
                                <th style="padding: 12px; text-align: left;">Deposit</th>
                                <th style="padding: 12px; text-align: left;">Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (EquipmentRental rental : rentals) { %>
                                <tr style="border-bottom: 1px solid #E2E8F0;">
                                    <td style="padding: 12px;"><%= rental.getRentalId() %></td>
                                    <td style="padding: 12px;"><strong><%= rental.getEquipmentName() %></strong></td>
                                    <td style="padding: 12px;"><%= rental.getQuantityRented() %></td>
                                    <td style="padding: 12px;"><%= rental.getRentalDate() %></td>
                                    <td style="padding: 12px;"><%= rental.getExpectedReturnDate() %></td>
                                    <td style="padding: 12px;">RM <%= rental.getTotalAmount() %></td>
                                    <td style="padding: 12px;">RM <%= rental.getDepositPaid() %></td>
                                    <td style="padding: 12px;"><span style="background: <%= rental.getRentalStatus().toString().equals("ACTIVE") ? "#D1FAE5" : "#DBEAFE" %>; color: <%= rental.getRentalStatus().toString().equals("ACTIVE") ? "#065F46" : "#1E40AF" %>; padding: 4px 12px; border-radius: 20px; font-size: 12px;"><%= rental.getRentalStatus() %></span></td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            <% } %>
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