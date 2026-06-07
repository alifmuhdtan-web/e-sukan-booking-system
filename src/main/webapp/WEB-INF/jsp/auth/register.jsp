<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Libang Libu - Register E-Sukan</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modern.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">
</head>
<body>
    <div class="container" style="min-height: 100vh; display: flex; align-items: center; justify-content: center; padding: 40px 20px;">
        <div class="card" style="max-width: 500px; width: 100%; animation: fadeInUp 0.4s ease;">
            
            <!-- Logo - Fixed Size (80x80) -->
            <div style="text-align: center; margin-bottom: var(--spacing-xl);">
                <img src="${pageContext.request.contextPath}/assets/logo.png" alt="Libang Libu" style="width: 80px; height: 80px; object-fit: contain; margin-bottom: var(--spacing-md);">
                <h1 class="h2" style="margin-bottom: var(--spacing-xs);">Create Account</h1>
                <p class="text-secondary" style="font-size: 0.875rem;">Join Libang Libu E-Sukan</p>
            </div>
            
            <% if (request.getAttribute("errors") != null) { %>
                <div class="toast toast-error" style="margin-bottom: var(--spacing-lg); animation: none;">
                    <span class="toast-message"><%= request.getAttribute("errors") %></span>
                </div>
            <% } %>
            
            <form action="${pageContext.request.contextPath}/register" method="post">
                <div class="form-group">
                    <label class="form-label"><i class="fas fa-user"></i> Username <span class="required">*</span></label>
                    <input type="text" name="username" class="form-input" 
                           value="<%= request.getAttribute("username") != null ? request.getAttribute("username") : "" %>" 
                           placeholder="Enter username" required>
                    <div class="form-hint">At least 3 characters</div>
                </div>
                
                <div class="form-group">
                    <label class="form-label"><i class="fas fa-envelope"></i> Email <span class="required">*</span></label>
                    <input type="email" name="email" class="form-input" 
                           value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>" 
                           placeholder="your@email.com" required>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label class="form-label"><i class="fas fa-lock"></i> Password <span class="required">*</span></label>
                        <input type="password" name="password" class="form-input" placeholder="Enter password" required>
                        <div class="form-hint">Minimum 6 characters</div>
                    </div>
                    <div class="form-group">
                        <label class="form-label"><i class="fas fa-check-circle"></i> Confirm Password <span class="required">*</span></label>
                        <input type="password" name="confirmPassword" class="form-input" placeholder="Confirm password" required>
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="form-label"><i class="fas fa-signature"></i> Full Name <span class="required">*</span></label>
                    <input type="text" name="fullName" class="form-input" 
                           value="<%= request.getAttribute("fullName") != null ? request.getAttribute("fullName") : "" %>" 
                           placeholder="Your full name" required>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label class="form-label"><i class="fas fa-phone"></i> Phone Number <span class="required">*</span></label>
                        <input type="tel" name="phoneNumber" class="form-input" 
                               value="<%= request.getAttribute("phoneNumber") != null ? request.getAttribute("phoneNumber") : "" %>" 
                               placeholder="0123456789" required>
                        <div class="form-hint">10-11 digits</div>
                    </div>
                    <div class="form-group">
                        <label class="form-label"><i class="fas fa-id-card"></i> Matric Number</label>
                        <input type="text" name="matricNumber" class="form-input" 
                               value="<%= request.getAttribute("matricNumber") != null ? request.getAttribute("matricNumber") : "" %>" 
                               placeholder="e.g., A123456">
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="form-label"><i class="fas fa-tag"></i> Account Type</label>
                    <select name="userRole" class="form-select">
                        <option value="STUDENT">Student</option>
                        <option value="MANAGER">Facility Manager</option>
                    </select>
                </div>
                
                <button type="submit" class="btn btn-primary" style="width: 100%;"><i class="fas fa-user-plus"></i> Create Account</button>
            </form>
            
            <div style="text-align: center; margin-top: var(--spacing-lg);">
                <p class="text-secondary" style="font-size: 0.875rem;">
                    Already have an account? 
                    <a href="${pageContext.request.contextPath}/login"><i class="fas fa-sign-in-alt"></i> Sign in</a>
                </p>
            </div>
            
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