package com.esukan.servlet;

import com.esukan.dao.EquipmentDAO;
import com.esukan.model.Equipment;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "EquipmentListServlet", urlPatterns = {"/equipment"})
public class EquipmentListServlet extends HttpServlet {
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
        
        try {
            List<Equipment> equipmentList = equipmentDAO.findAllAvailable();
            request.setAttribute("equipmentList", equipmentList);
            request.getRequestDispatcher("/WEB-INF/jsp/equipment/list.jsp")
                   .forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}