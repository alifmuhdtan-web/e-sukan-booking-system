package com.esukan.servlet;

import com.esukan.util.PasswordUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "TestPasswordServlet", urlPatterns = {"/test-password"})
public class TestPasswordServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>Password Hash Test</title>");
        out.println("<style>");
        out.println("body { font-family: monospace; padding: 20px; }");
        out.println(".pass { color: green; font-weight: bold; }");
        out.println(".fail { color: red; font-weight: bold; }");
        out.println("pre { background: #f4f4f4; padding: 10px; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Password Hash Test</h1>");
        
        String password = "password123";
        String hash = PasswordUtil.hashPassword(password);
        String expectedHash = "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918";
        
        out.println("<h2>Test Results:</h2>");
        out.println("<p>Password: <strong>" + password + "</strong></p>");
        out.println("<p>Generated Hash: <strong>" + hash + "</strong></p>");
        out.println("<p>Expected Hash: <strong>" + expectedHash + "</strong></p>");
        
        if (hash.equals(expectedHash)) {
            out.println("<p class='pass'>✓ HASH MATCHES! PasswordUtil is working correctly.</p>");
            out.println("<p>Login should work. <a href='login'>Go to Login Page</a></p>");
        } else {
            out.println("<p class='fail'>✗ HASH DOES NOT MATCH! PasswordUtil has an issue.</p>");
            out.println("<pre>Expected: " + expectedHash + "</pre>");
            out.println("<pre>Got:      " + hash + "</pre>");
        }
        
        out.println("</body>");
        out.println("</html>");
    }
}