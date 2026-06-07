package com.esukan.servlet;

import com.esukan.dao.BookingDAO;
import com.esukan.model.Booking;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "MyBookingsServlet", urlPatterns = {"/MyBookingsServlet"})
public class MyBookingsServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        int userId = (int) session.getAttribute("userId");
        
        BookingDAO dao = new BookingDAO();
        
        try {
            List<Booking> bookingList = dao.getBookingsByUser(userId);
            request.setAttribute("bookingList", bookingList);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
        }
        
        String success = request.getParameter("success");
        if (success != null) {
            request.setAttribute("success", success);
        }
        
        String error = request.getParameter("error");
        if (error != null) {
            request.setAttribute("error", error);
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/booking/list.jsp");
        dispatcher.forward(request, response);
    }
}