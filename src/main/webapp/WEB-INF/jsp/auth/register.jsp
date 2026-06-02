<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - E-Sukan Booking System</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 40px 20px;
        }
        .register-container {
            background: white;
            border-radius: 10px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.2);
            max-width: 500px;
            margin: 0 auto;
            padding: 40px;
        }
        h2 { text-align: center; color: #333; margin-bottom: 30px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; color: #555; font-weight: 500; }
        input, select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
        }
        input:focus, select:focus { outline: none; border-color: #667eea; }
        button {
            width: 100%;
            padding: 12px;
            background: #667eea;
            border: none;
            border-radius: 5px;
            color: white;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            margin-top: 10px;
        }
        button:hover { background: #5a67d8; }
        .error-message {
            background: #fed7d7;
            color: #c53030;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .login-link { text-align: center; margin-top: 20px; color: #666; }
        .login-link a { color: #667eea; text-decoration: none; }
        .row { display: flex; gap: 15px; }
        .row .form-group { flex: 1; }
        small { color: #666; }
    </style>
</head>
<body>
    <div class="register-container">
        <h2>Create Account</h2>
        
        <% if (request.getAttribute("errors") != null) { %>
            <div class="error-message">
                <%= request.getAttribute("errors") %>
            </div>
        <% } %>
        
        <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="form-group">
                <label>Username *</label>
                <input type="text" name="username" 
                       value="<%= request.getAttribute("username") != null ? request.getAttribute("username") : "" %>"
                       required>
                <small>At least 3 characters</small>
            </div>
            
            <div class="form-group">
                <label>Email *</label>
                <input type="email" name="email" 
                       value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>"
                       required>
            </div>
            
            <div class="row">
                <div class="form-group">
                    <label>Password *</label>
                    <input type="password" name="password" required>
                    <small>At least 6 characters</small>
                </div>
                <div class="form-group">
                    <label>Confirm Password *</label>
                    <input type="password" name="confirmPassword" required>
                </div>
            </div>
            
            <div class="form-group">
                <label>Full Name *</label>
                <input type="text" name="fullName" 
                       value="<%= request.getAttribute("fullName") != null ? request.getAttribute("fullName") : "" %>"
                       required>
            </div>
            
            <div class="row">
                <div class="form-group">
                    <label>Phone Number *</label>
                    <input type="tel" name="phoneNumber" 
                           value="<%= request.getAttribute("phoneNumber") != null ? request.getAttribute("phoneNumber") : "" %>"
                           placeholder="0123456789" required>
                </div>
                <div class="form-group">
                    <label>Matric Number (Students)</label>
                    <input type="text" name="matricNumber" 
                           value="<%= request.getAttribute("matricNumber") != null ? request.getAttribute("matricNumber") : "" %>">
                </div>
            </div>
            
            <div class="form-group">
                <label>Account Type</label>
                <select name="userRole">
                    <option value="STUDENT">Student</option>
                    <option value="MANAGER">Facility Manager</option>
                </select>
            </div>
            
            <button type="submit">Register</button>
        </form>
        
        <div class="login-link">
            Already have an account? <a href="${pageContext.request.contextPath}/login">Login here</a>
        </div>
    </div>
</body>
</html>