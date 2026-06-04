package com.esukan.servlet;

import com.esukan.dao.EquipmentDAO;
import com.esukan.model.Equipment;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/manager/equipment")
public class EquipmentServlet extends HttpServlet {
    private EquipmentDAO equipmentDAO = new EquipmentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            equipmentDAO.deleteEquipment(id);
        }

        
        List<Equipment> list = equipmentDAO.getAllEquipment();
        request.setAttribute("equipmentList", list);
        
        
        request.getRequestDispatcher("/WEB-INF/jsp/equipment/manage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String status = request.getParameter("status");

        Equipment eq = new Equipment(0, name, type, quantity, status);
        equipmentDAO.addEquipment(eq);

        
        response.sendRedirect(request.getContextPath() + "/manager/equipment");
    }
}
