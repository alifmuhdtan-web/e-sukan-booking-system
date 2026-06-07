<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Libang Libu - E-Sukan Booking System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modern.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">
</head>
<body>
    <div class="container" style="min-height: 100vh; display: flex; align-items: center; justify-content: center;">
        <div class="card" style="max-width: 420px; width: 100%; animation: fadeInUp 0.4s ease;">
            
            <!-- Logo -->
            <div style="text-align: center; margin-bottom: var(--spacing-xl);">
                <img src="${pageContext.request.contextPath}/assets/logo.png" alt="Libang Libu" style="width: 80px; height: 80px; object-fit: contain; margin-bottom: var(--spacing-md);">
                <h1 class="h2" style="margin-bottom: var(--spacing-xs);">Welcome Back</h1>
                <p class="text-secondary" style="font-size: 0.875rem;">Sign in to your account</p>
            </div>
            
            <% if (request.getParameter("message") != null) { %>
                <div class="toast toast-success" style="margin-bottom: var(--spacing-lg); animation: none;">
                    <span class="toast-message"><%= request.getParameter("message") %></span>
                </div>
            <% } %>
            
            <% if (request.getAttribute("error") != null) { %>
                <div class="toast toast-error" style="margin-bottom: var(--spacing-lg); animation: none;">
                    <span class="toast-message"><%= request.getAttribute("error") %></span>
                </div>
            <% } %>
            
            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="form-group">
                    <label class="form-label"><i class="fas fa-user"></i> Username</label>
                    <input type="text" name="username" class="form-input" 
                           value="<%= request.getAttribute("username") != null ? request.getAttribute("username") : "" %>" 
                           placeholder="Enter your username" required>
                </div>
                
                <div class="form-group">
                    <label class="form-label"><i class="fas fa-lock"></i> Password</label>
                    <input type="password" name="password" class="form-input" 
                           placeholder="Enter your password" required>
                </div>
                
                <button type="submit" class="btn btn-primary" style="width: 100%;"><i class="fas fa-sign-in-alt"></i> Sign In</button>
            </form>
            
            <div style="text-align: center; margin-top: var(--spacing-lg);">
                <p class="text-secondary" style="font-size: 0.875rem;">
                    Don't have an account? 
                    <a href="${pageContext.request.contextPath}/register"><i class="fas fa-user-plus"></i> Create account</a>
                </p>
            </div>
            
            <div style="margin-top: var(--spacing-xl); padding-top: var(--spacing-md); border-top: 1px solid #E2E8F0;">
                <p class="text-secondary" style="font-size: 0.75rem; text-align: center;">Demo Accounts:</p>
                <div style="display: flex; flex-wrap: wrap; gap: var(--spacing-sm); justify-content: center; margin-top: var(--spacing-sm); font-size: 0.75rem;">
                    <span class="badge badge-primary"><i class="fas fa-user-shield"></i> admin / password123</span>
                    <span class="badge badge-primary"><i class="fas fa-user-tie"></i> faizfarhan / password123</span>
                    <span class="badge badge-primary"><i class="fas fa-user-graduate"></i> faizyusmi / password123</span>
                </div>
            </div>
            
            <!-- Footer - Copyright 2026 -->
            <div class="footer" style="margin-top: var(--spacing-xl);">
                <div class="footer-logo">
                    <img src="${pageContext.request.contextPath}/assets/logo.png" alt="Libang Libu" class="footer-logo-img">
                    <span>Libang Libu - E-Sukan System</span>
                </div>
                <p>© 2026 Libang Libu - All Rights Reserved.</p>
            </div>
        </div>
    </div>
    
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>