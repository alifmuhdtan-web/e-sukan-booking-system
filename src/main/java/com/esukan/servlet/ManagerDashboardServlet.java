package com.esukan.servlet;

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
        
        String fullName = (String) session.getAttribute("fullName");
        
        response.setContentType("text/html");
        response.getWriter().println("<html><head><title>Manager Dashboard</title>");
        response.getWriter().println("<style>");
        response.getWriter().println("body { font-family: Arial; margin: 0; padding: 0; background: #f5f5f5; }");
        response.getWriter().println(".navbar { background: #667eea; padding: 15px 30px; color: white; }");
        response.getWriter().println(".container { max-width: 800px; margin: 30px auto; background: white; padding: 30px; border-radius: 10px; }");
        response.getWriter().println("h1 { color: #333; }");
        response.getWriter().println("</style></head><body>");
        response.getWriter().println("<div class='navbar'><strong>🏟️ E-Sukan</strong> - Manager Portal</div>");
        response.getWriter().println("<div class='container'>");
        response.getWriter().println("<h1>Welcome, " + fullName + "!</h1>");
        response.getWriter().println("<p>Manager Dashboard</p>");
        response.getWriter().println("<hr>");
        response.getWriter().println("<ul>");
        response.getWriter().println("<li><a href='" + request.getContextPath() + "/logout'>Logout</a></li>");
        response.getWriter().println("</ul>");
        response.getWriter().println("</div></body></html>");
    }
}