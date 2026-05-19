package com.esukan.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "TestDBServlet", urlPatterns = {"/test-db"})
public class TestDBServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>Database Test</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 40px; }");
        out.println(".success { color: green; }");
        out.println(".error { color: red; }");
        out.println("table { border-collapse: collapse; margin-top: 20px; }");
        out.println("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
        out.println("th { background: #667eea; color: white; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Database Connection Test</h1>");
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            // Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            out.println("<p class='success'>✅ MySQL Driver loaded!</p>");
            
            // Connect to database - FIXED URL
            String url = "jdbc:mysql://localhost:3306/esukan_db?useSSL=false&allowPublicKeyRetrieval=true";
            String user = "esukan_user";
            String password = "1234!";
            
            conn = DriverManager.getConnection(url, user, password);
            out.println("<p class='success'>✅ Connected to database: esukan_db</p>");
            
            // Query users
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT user_id, username, full_name, user_role FROM users");
            
            out.println("<h2>Users in Database:</h2>");
            out.println("<table>");
            out.println("<tr><th>ID</th><th>Username</th><th>Full Name</th><th>Role</th></tr>");
            
            int count = 0;
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("user_id") + "</td>");
                out.println("<td>" + rs.getString("username") + "</td>");
                out.println("<td>" + rs.getString("full_name") + "</td>");
                out.println("<td>" + rs.getString("user_role") + "</td>");
                out.println("</tr>");
                count++;
            }
            out.println("</table>");
            out.println("<p>Total users: " + count + "</p>");
            
            // Test login with admin account
            out.println("<h2>Test Login Credentials:</h2>");
            out.println("<ul>");
            out.println("<li><strong>Admin:</strong> username='admin', password='password123'</li>");
            out.println("<li><strong>Manager:</strong> username='manager_ahmad', password='password123'</li>");
            out.println("<li><strong>Student:</strong> username='student_ali', password='password123'</li>");
            out.println("</ul>");
            
        } catch (ClassNotFoundException e) {
            out.println("<p class='error'>❌ MySQL Driver not found!</p>");
            out.println("<p>Error: " + e.getMessage() + "</p>");
        } catch (SQLException e) {
            out.println("<p class='error'>❌ Database connection failed!</p>");
            out.println("<p>Error: " + e.getMessage() + "</p>");
        } finally {
            try { if (rs != null) rs.close(); } catch(Exception e) {}
            try { if (stmt != null) stmt.close(); } catch(Exception e) {}
            try { if (conn != null) conn.close(); } catch(Exception e) {}
        }
        
        out.println("<p><a href='/e-sukan-booking-system/'>Back to Home</a></p>");
        out.println("</body>");
        out.println("</html>");
    }
}