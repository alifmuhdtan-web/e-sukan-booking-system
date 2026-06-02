package com.esukan.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "StudentDashboardServlet", urlPatterns = {"/student/dashboard"})
public class StudentDashboardServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String username = (String) session.getAttribute("username");
        String fullName = (String) session.getAttribute("fullName");
        
        response.setContentType("text/html");
        response.getWriter().println("<html><head><title>Student Dashboard</title>");
        response.getWriter().println("<style>");
        response.getWriter().println("body { font-family: Arial; margin: 0; padding: 0; background: #f5f5f5; }");
        response.getWriter().println(".navbar { background: #667eea; padding: 15px 30px; color: white; }");
        response.getWriter().println(".container { max-width: 800px; margin: 30px auto; background: white; padding: 30px; border-radius: 10px; }");
        response.getWriter().println("h1 { color: #333; }");
        response.getWriter().println("a { display: inline-block; margin: 10px 0; color: #667eea; text-decoration: none; }");
        response.getWriter().println("</style>");
        response.getWriter().println("</head><body>");
        response.getWriter().println("<div class='navbar'><strong>🏟️ E-Sukan</strong> - Student Portal</div>");
        response.getWriter().println("<div class='container'>");
        response.getWriter().println("<h1>Welcome, " + fullName + "!</h1>");
        response.getWriter().println("<p>You have successfully logged in.</p>");
        response.getWriter().println("<hr>");
        response.getWriter().println("<h3>Quick Links:</h3>");
        response.getWriter().println("<ul>");
        response.getWriter().println("<li><a href='" + request.getContextPath() + "/MyBookingsServlet'>My Bookings</a></li>");
        response.getWriter().println("<li><a href='" + request.getContextPath() + "/CreateBookingServlet?facilityId=1'>Book Facility</a></li>");
        response.getWriter().println("<li><a href='" + request.getContextPath() + "/logout'>Logout</a></li>");
        response.getWriter().println("</ul>");
        response.getWriter().println("</div></body></html>");
    }
}