package servlet;

import dao.BookingDAO;
import model.Booking;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/MyBookingsServlet")
public class MyBookingsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));

        BookingDAO dao = new BookingDAO();

        List<Booking> bookingList = dao.getBookingsByUser(userId);

        request.setAttribute("bookingList", bookingList);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("myBookings.jsp");

        dispatcher.forward(request, response);
    }
}
