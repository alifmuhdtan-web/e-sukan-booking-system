<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>E-Sukan Booking System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 50px; background: #f5f5f5; }
        .container { max-width: 800px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #667eea; }
        .success { color: green; font-weight: bold; }
        .error { color: red; }
        ul { margin-top: 20px; }
        li { margin: 10px 0; }
        a { color: #667eea; text-decoration: none; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="container">
        <h1>🏟️ E-Sukan Campus Facility & Equipment Booking System</h1>
        <p class="success">✅ Application is successfully deployed!</p>
        <p>Server Time: <%= new java.util.Date().toString() %></p>
        
        <hr>
        
        <h2>📋 Navigation</h2>
        <ul>
            <li><a href="login">🔐 Login</a></li>
            <li><a href="register">📝 Register</a></li>
            <li><a href="test-db">🗄️ Test Database Connection</a></li>
        </ul>
        
        <hr>
        
        <h2>📊 System Status</h2>
        <ul>
            <li>GlassFish Server: <span class="success">Running</span></li>
            <li>Application: <span class="success">Ready</span></li>
            <li>Database: <span id="dbStatus">Testing...</span></li>
        </ul>
    </div>
    
    <script>
        // Optional: Test database status via AJAX
        fetch('test-db')
            .then(response => response.text())
            .then(html => {
                if (html.includes('successful')) {
                    document.getElementById('dbStatus').innerHTML = '<span class="success">Connected</span>';
                } else {
                    document.getElementById('dbStatus').innerHTML = '<span class="error">Not Connected</span>';
                }
            })
            .catch(() => {
                document.getElementById('dbStatus').innerHTML = '<span class="error">Not Connected</span>';
            });
    </script>
</body>
</html>