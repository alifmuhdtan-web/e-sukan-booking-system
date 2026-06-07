package com.esukan.servlet;

import com.esukan.dao.BookingDAO;
import com.esukan.model.Booking;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CreateBookingServlet", urlPatterns = {"/CreateBookingServlet"})
public class CreateBookingServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String facilityId = request.getParameter("facilityId");
        request.setAttribute("facilityId", facilityId);
        request.getRequestDispatcher("/WEB-INF/jsp/booking/create.jsp")
               .forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            int userId = (int) session.getAttribute("userId");
            int facilityId = Integer.parseInt(request.getParameter("facilityId"));
            Date bookingDate = Date.valueOf(request.getParameter("bookingDate"));
            Time startTime = Time.valueOf(request.getParameter("startTime") + ":00");
            Time endTime = Time.valueOf(request.getParameter("endTime") + ":00");
            double totalCost = Double.parseDouble(request.getParameter("totalCost"));

            Booking booking = new Booking();
            booking.setUserId(userId);
            booking.setFacilityId(facilityId);
            booking.setBookingDate(bookingDate);
            booking.setStartTime(startTime);
            booking.setEndTime(endTime);
            booking.setTotalCost(BigDecimal.valueOf(totalCost));
            booking.setBookingStatus(Booking.BookingStatus.CONFIRMED);
            booking.setPaymentStatus(Booking.PaymentStatus.PENDING);

            BookingDAO dao = new BookingDAO();
            boolean result = dao.createBooking(booking);

            if (result) {
                response.sendRedirect(request.getContextPath() + "/MyBookingsServlet?success=Booking created successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/CreateBookingServlet?facilityId=" + facilityId + "&error=Failed to create booking");
            }
            
        } catch (NumberFormatException e) {
            String facilityId = request.getParameter("facilityId");
            response.sendRedirect(request.getContextPath() + "/CreateBookingServlet?facilityId=" + facilityId + "&error=Invalid input format");
        } catch (IllegalArgumentException e) {
            String facilityId = request.getParameter("facilityId");
            response.sendRedirect(request.getContextPath() + "/CreateBookingServlet?facilityId=" + facilityId + "&error=Invalid date or time format");
        } catch (SQLException e) {
            e.printStackTrace();
            String facilityId = request.getParameter("facilityId");
            response.sendRedirect(request.getContextPath() + "/CreateBookingServlet?facilityId=" + facilityId + "&error=Database error: " + e.getMessage());
        }
    }
}