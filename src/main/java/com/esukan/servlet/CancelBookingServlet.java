package servlet;

import dao.BookingDAO;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/CancelBookingServlet")
public class CancelBookingServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        int bookingId = Integer.parseInt(request.getParameter("id"));

        BookingDAO dao = new BookingDAO();

        dao.cancelBooking(bookingId);

        response.sendRedirect("MyBookingsServlet");
    }
}
