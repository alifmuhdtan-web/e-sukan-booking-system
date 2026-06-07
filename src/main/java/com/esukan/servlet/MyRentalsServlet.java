package com.esukan.servlet;

import com.esukan.dao.EquipmentDAO;
import com.esukan.model.EquipmentRental;
import com.esukan.model.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "MyRentalsServlet", urlPatterns = {"/my-rentals"})
public class MyRentalsServlet extends HttpServlet {
    private EquipmentDAO equipmentDAO;
    
    @Override
    public void init() {
        equipmentDAO = new EquipmentDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        
        try {
            List<EquipmentRental> rentals = equipmentDAO.findRentalsByUser(user.getUserId());
            request.setAttribute("rentals", rentals);
            
            String success = request.getParameter("success");
            if (success != null) request.setAttribute("success", success);
            String error = request.getParameter("error");
            if (error != null) request.setAttribute("error", error);
            
            request.getRequestDispatcher("/WEB-INF/jsp/equipment/my-rentals.jsp")
                   .forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}