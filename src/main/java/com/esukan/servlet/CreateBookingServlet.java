package com.esukan.servlet;

import com.esukan.dao.BookingDAO;
import com.esukan.model.Booking;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CreateBookingServlet", urlPatterns = {"/CreateBookingServlet"})
public class CreateBookingServlet extends HttpServlet {
    
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
        
        String facilityId = request.getParameter("facilityId");
        request.setAttribute("facilityId", facilityId);
        
        // Forward to the correct JSP location
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

            // Validate date is not in the past
            if (bookingDate.toLocalDate().isBefore(LocalDate.now())) {
                response.sendRedirect(request.getContextPath() + "/CreateBookingServlet?facilityId=" + facilityId + "&error=Cannot book past date");
                return;
            }
            
            // Validate start time is before end time
            if (!startTime.before(endTime)) {
                response.sendRedirect(request.getContextPath() + "/CreateBookingServlet?facilityId=" + facilityId + "&error=Start time must be before end time");
                return;
            }
            
            // Check availability
            boolean isAvailable = bookingDAO.checkAvailability(facilityId, bookingDate, startTime, endTime);
            if (!isAvailable) {
                response.sendRedirect(request.getContextPath() + "/CreateBookingServlet?facilityId=" + facilityId + "&error=Time slot not available. Please choose another time.");
                return;
            }

            // Create booking object
            Booking booking = new Booking();
            booking.setUserId(userId);
            booking.setFacilityId(facilityId);
            booking.setBookingDate(bookingDate);
            booking.setStartTime(startTime);
            booking.setEndTime(endTime);
            booking.setTotalCost(totalCost);
            booking.setStatus("CONFIRMED");

            // Save to database
            boolean result = bookingDAO.createBooking(booking);

            if (result) {
                response.sendRedirect(request.getContextPath() + "/MyBookingsServlet?success=Booking created successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/CreateBookingServlet?facilityId=" + facilityId + "&error=Failed to create booking. Please try again.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            String facilityId = request.getParameter("facilityId");
            response.sendRedirect(request.getContextPath() + "/CreateBookingServlet?facilityId=" + facilityId + "&error=Invalid input: " + e.getMessage());
        }
    }
}