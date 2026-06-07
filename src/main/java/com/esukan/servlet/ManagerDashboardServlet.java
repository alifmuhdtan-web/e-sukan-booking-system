package com.esukan.servlet;

import com.esukan.model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ManagerDashboardServlet", urlPatterns = {"/manager/dashboard"})
public class ManagerDashboardServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        if (user.getUserRole() != User.UserRole.MANAGER) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Manager access only");
            return;
        }
        
        response.setContentType("text/html");
        response.getWriter().println("<!DOCTYPE html>");
        response.getWriter().println("<html>");
        response.getWriter().println("<head><title>Manager Dashboard - E-Sukan</title>");
        response.getWriter().println("<style>");
        response.getWriter().println("body { font-family: Arial, sans-serif; background: #f5f5f5; margin: 0; padding: 0; }");
        response.getWriter().println(".navbar { background: #667eea; padding: 15px 30px; color: white; display: flex; justify-content: space-between; }");
        response.getWriter().println(".navbar a { color: white; text-decoration: none; margin-left: 20px; }");
        response.getWriter().println(".container { max-width: 800px; margin: 50px auto; background: white; padding: 30px; border-radius: 10px; }");
        response.getWriter().println("h1 { color: #333; }");
        response.getWriter().println(".btn { display: inline-block; background: #667eea; color: white; padding: 12px 24px; text-decoration: none; border-radius: 5px; margin: 10px; font-size: 16px; }");
        response.getWriter().println(".btn:hover { background: #5a67d8; }");
        response.getWriter().println(".success { color: green; }");
        response.getWriter().println("</style>");
        response.getWriter().println("</head>");
        response.getWriter().println("<body>");
        response.getWriter().println("<div class='navbar'>");
        response.getWriter().println("<div><strong>E-Sukan</strong> - Manager Portal</div>");
        response.getWriter().println("<div><a href='" + request.getContextPath() + "/logout'>Logout</a></div>");
        response.getWriter().println("</div>");
        response.getWriter().println("<div class='container'>");
        response.getWriter().println("<h1>Welcome, " + user.getFullName() + "!</h1>");
        response.getWriter().println("<p class='success'>You have successfully logged in.</p>");
        response.getWriter().println("<hr>");
        response.getWriter().println("<h2>Management Tools:</h2>");
        response.getWriter().println("<div>");
        response.getWriter().println("<a href='" + request.getContextPath() + "/manager/facilities' class='btn'>Manage Facilities</a>");
        response.getWriter().println("<a href='" + request.getContextPath() + "/manager/equipment' class='btn'>Manage Equipment</a>");
        response.getWriter().println("<a href='" + request.getContextPath() + "/manager/analytics' class='btn'>View Analytics</a>");
        response.getWriter().println("</div>");
        response.getWriter().println("</div>");
        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }
}