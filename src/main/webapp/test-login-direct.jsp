<%@ page import="com.esukan.dao.UserDAO" %>
<%@ page import="com.esukan.model.User" %>
<%@ page import="com.esukan.util.PasswordUtil" %>
<%@ page import="java.util.Optional" %>
<!DOCTYPE html>
<html>
<head>
    <title>Direct Login Test</title>
    <style>
        body { font-family: monospace; padding: 20px; }
        .pass { color: green; font-weight: bold; }
        .fail { color: red; font-weight: bold; }
        pre { background: #f4f4f4; padding: 10px; }
    </style>
</head>
<body>
<h1>Direct Database Login Test</h1>

<%
    String testUsername = "admin";
    String testPassword = "password123";
    
    out.println("<h2>Testing with: " + testUsername + " / " + testPassword + "</h2>");
    
    UserDAO userDAO = new UserDAO();
    Optional<User> userOpt = userDAO.findByUsername(testUsername);
    
    if (userOpt.isPresent()) {
        User user = userOpt.get();
        out.println("<p class='pass'>? User found in database</p>");
        out.println("<p>Username: " + user.getUsername() + "</p>");
        out.println("<p>Stored hash: <code>" + user.getPasswordHash() + "</code></p>");
        
        String computedHash = PasswordUtil.hashPassword(testPassword);
        out.println("<p>Computed hash of 'password123': <code>" + computedHash + "</code></p>");
        
        boolean matches = computedHash.equals(user.getPasswordHash());
        
        if (matches) {
            out.println("<p class='pass'>? PASSWORDS MATCH! Login would succeed.</p>");
            // Set session manually
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("fullName", user.getFullName());
            session.setAttribute("userRole", user.getUserRole().toString());
            out.println("<p><a href='student/dashboard'>Click here to go to Dashboard</a></p>");
        } else {
            out.println("<p class='fail'>? PASSWORDS DO NOT MATCH!</p>");
        }
    } else {
        out.println("<p class='fail'>? User 'admin' not found in database!</p>");
    }
%>

<hr>
<p><a href="login">Go to Login Page</a></p>
</body>
</html>