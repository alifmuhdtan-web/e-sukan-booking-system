package com.esukan.servlet;

import com.esukan.dao.EquipmentDAO;
import com.esukan.model.Equipment;
import com.esukan.model.User;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ManageEquipmentServlet", urlPatterns = {"/manager/equipment"})
public class ManageEquipmentServlet extends HttpServlet {
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
        if (user.getUserRole() != User.UserRole.MANAGER) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Manager access only");
            return;
        }
        
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                equipmentDAO.deleteEquipment(id);
                response.sendRedirect(request.getContextPath() + "/manager/equipment?success=Equipment deleted");
                return;
            } catch (Exception e) {
                response.sendRedirect(request.getContextPath() + "/manager/equipment?error=Delete failed");
                return;
            }
        }
        
        try {
            List<Equipment> equipmentList = equipmentDAO.findAll();
            request.setAttribute("equipmentList", equipmentList);
            request.getRequestDispatcher("/WEB-INF/jsp/equipment/manage.jsp")
                   .forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        if (user.getUserRole() != User.UserRole.MANAGER) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Manager access only");
            return;
        }
        
        try {
            Equipment eq = new Equipment();
            eq.setEquipmentName(request.getParameter("name"));
            eq.setEquipmentType(Equipment.EquipmentType.valueOf(request.getParameter("type").toUpperCase()));
            eq.setDescription(request.getParameter("description"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            eq.setQuantityTotal(quantity);
            eq.setQuantityAvailable(quantity);
            eq.setDailyRate(new BigDecimal(request.getParameter("dailyRate")));
            eq.setDepositAmount(new BigDecimal(request.getParameter("deposit")));
            eq.setConditionStatus(Equipment.ConditionStatus.valueOf(request.getParameter("conditionStatus").toUpperCase()));
            eq.setActive(true);
            
            equipmentDAO.createEquipment(eq);
            response.sendRedirect(request.getContextPath() + "/manager/equipment?success=Equipment added");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/manager/equipment?error=" + e.getMessage());
        }
    }
}