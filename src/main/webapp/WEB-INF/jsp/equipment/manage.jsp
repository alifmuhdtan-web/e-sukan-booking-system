<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.esukan.model.Equipment" %>
<%@ page import="com.esukan.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    List<Equipment> equipmentList = (List<Equipment>) request.getAttribute("equipmentList");
    String success = request.getParameter("success");
    String error = request.getParameter("error");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Libang Libu - Manage Equipment</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modern.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
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
                <a href="${pageContext.request.contextPath}/manager/facilities" class="nav-link"><i class="fas fa-building"></i> Facilities</a>
                <a href="${pageContext.request.contextPath}/manager/equipment" class="nav-link active"><i class="fas fa-dumbbell"></i> Equipment</a>
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
            <h1><i class="fas fa-dumbbell"></i> Equipment Management</h1>
            
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
            
            <!-- Add New Equipment Form -->
            <div style="background: white; border-radius: 16px; padding: 24px; box-shadow: 0 1px 3px rgba(0,0,0,0.1); margin-bottom: 32px;">
                <h2 style="margin-bottom: 20px;"><i class="fas fa-plus-circle"></i> Add New Equipment</h2>
                <form action="${pageContext.request.contextPath}/manager/equipment" method="post">
                    <div class="form-group">
                        <label class="form-label"><i class="fas fa-font"></i> Equipment Name <span style="color: red;">*</span></label>
                        <input type="text" name="name" class="form-input" required>
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label"><i class="fas fa-tag"></i> Type <span style="color: red;">*</span></label>
                            <select name="type" class="form-select" required>
                                <option value="">Select Type</option>
                                <option value="RACKET">Racket</option>
                                <option value="BALL">Ball</option>
                                <option value="NET">Net</option>
                                <option value="GOOGLE">Goggle</option>
                                <option value="LIFE_JACKET">Life Jacket</option>
                                <option value="WEIGHTS">Weights</option>
                                <option value="MAT">Mat</option>
                                <option value="OTHER">Other</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="form-label"><i class="fas fa-cubes"></i> Quantity <span style="color: red;">*</span></label>
                            <input type="number" name="quantity" class="form-input" min="1" required>
                        </div>
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label"><i class="fas fa-dollar-sign"></i> Daily Rate (RM) <span style="color: red;">*</span></label>
                            <input type="number" step="0.01" name="dailyRate" class="form-input" required>
                        </div>
                        <div class="form-group">
                            <label class="form-label"><i class="fas fa-money-bill"></i> Deposit (RM) <span style="color: red;">*</span></label>
                            <input type="number" step="0.01" name="deposit" class="form-input" required>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label"><i class="fas fa-clipboard-list"></i> Condition Status <span style="color: red;">*</span></label>
                        <select name="conditionStatus" class="form-select" required>
                            <option value="EXCELLENT">Excellent</option>
                            <option value="GOOD">Good</option>
                            <option value="FAIR">Fair</option>
                            <option value="POOR">Poor</option>
                            <option value="DAMAGED">Damaged</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label"><i class="fas fa-align-left"></i> Description (Optional)</label>
                        <textarea name="description" class="form-textarea" rows="2"></textarea>
                    </div>
                    
                    <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Add Equipment</button>
                </form>
            </div>
            
            <!-- Equipment List -->
            <h2><i class="fas fa-list"></i> Existing Equipment</h2>
            <div style="overflow-x: auto; background: white; border-radius: 16px; box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
                <table style="width: 100%; border-collapse: collapse;">
                    <thead>
                        <tr style="background: #F1F5F9;">
                            <th style="padding: 12px; text-align: left;">ID</th>
                            <th style="padding: 12px; text-align: left;">Name</th>
                            <th style="padding: 12px; text-align: left;">Type</th>
                            <th style="padding: 12px; text-align: left;">Total</th>
                            <th style="padding: 12px; text-align: left;">Available</th>
                            <th style="padding: 12px; text-align: left;">Daily Rate</th>
                            <th style="padding: 12px; text-align: left;">Deposit</th>
                            <th style="padding: 12px; text-align: left;">Condition</th>
                            <th style="padding: 12px; text-align: left;">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (equipmentList != null && !equipmentList.isEmpty()) {
                            for (Equipment eq : equipmentList) { %>
                                <tr style="border-bottom: 1px solid #E2E8F0;">
                                    <td style="padding: 12px;"><%= eq.getEquipmentId() %></td>
                                    <td style="padding: 12px;"><%= eq.getEquipmentName() %></td>
                                    <td style="padding: 12px;"><%= eq.getEquipmentType() %></td>
                                    <td style="padding: 12px;"><%= eq.getQuantityTotal() %></td>
                                    <td style="padding: 12px;"><%= eq.getQuantityAvailable() %></td>
                                    <td style="padding: 12px;">RM <%= eq.getDailyRate() %></td>
                                    <td style="padding: 12px;">RM <%= eq.getDepositAmount() %></td>
                                    <td style="padding: 12px;"><span style="background: 
                                        <%= eq.getConditionStatus().toString().equals("EXCELLENT") ? "#D1FAE5" : 
                                           eq.getConditionStatus().toString().equals("GOOD") ? "#DBEAFE" : "#FEF3C7" %>; 
                                        padding: 4px 12px; border-radius: 20px; font-size: 12px;"><%= eq.getConditionStatus() %></span></td>
                                    <td style="padding: 12px;">
                                        <a href="${pageContext.request.contextPath}/manager/equipment?action=edit&id=<%= eq.getEquipmentId() %>" class="btn btn-outline btn-sm"><i class="fas fa-edit"></i> Edit</a>
                                        <a href="${pageContext.request.contextPath}/manager/equipment?action=delete&id=<%= eq.getEquipmentId() %>" class="btn btn-danger btn-sm" onclick="return confirm('Delete this equipment?')" style="margin-left: 8px;"><i class="fas fa-trash"></i> Delete</a>
                                    </td>
                                </tr>
                        <% } } else { %>
                            <tr>
                                <td colspan="9" style="padding: 40px; text-align: center; color: #64748B;">No equipment found. Add your first equipment above.</td>
                            </tr>
                        <% } %>
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