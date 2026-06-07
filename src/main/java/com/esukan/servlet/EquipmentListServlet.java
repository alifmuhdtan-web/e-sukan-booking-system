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

@WebServlet("/equipment")
public class EquipmentListServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        try {
            EquipmentDAO dao = new EquipmentDAO();
            List<Equipment> equipmentList = dao.findAllAvailable();
            req.setAttribute("equipmentList", equipmentList);
            req.getRequestDispatcher("/WEB-INF/jsp/equipment/list.jsp")
               .forward(req, resp);
        } catch (SQLException e) {
            resp.getWriter().println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}