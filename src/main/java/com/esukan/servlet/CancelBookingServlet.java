package com.esukan.servlet;

import com.esukan.dao.BookingDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CancelBookingServlet", urlPatterns = {"/CancelBookingServlet"})
public class CancelBookingServlet extends HttpServlet {
    
    private BookingDAO bookingDAO;
    
    @Override
    public void init() {
        bookingDAO = new BookingDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String bookingIdParam = request.getParameter("id");
        if (bookingIdParam == null || bookingIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/MyBookingsServlet?error=Invalid booking ID");
            return;
        }
        
        try {
            int bookingId = Integer.parseInt(bookingIdParam);
            boolean result = bookingDAO.cancelBooking(bookingId);
            
            if (result) {
                response.sendRedirect(request.getContextPath() + "/MyBookingsServlet?success=Booking cancelled successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/MyBookingsServlet?error=Failed to cancel booking");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/MyBookingsServlet?error=Invalid booking ID");
        }
    }
}