package servlet;

import dao.BookingDAO;
import model.Booking;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

@WebServlet("/CreateBookingServlet")
public class CreateBookingServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));
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
        booking.setTotalCost(totalCost);
        booking.setStatus("PENDING");

        BookingDAO dao = new BookingDAO();

        boolean result = dao.createBooking(booking);

        if (result) {
            response.sendRedirect("myBookings.jsp");
        } else {
            response.sendRedirect("createBooking.jsp?error=1");
        }
    }
}
