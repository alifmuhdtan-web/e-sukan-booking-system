package com.esukan.servlet;

import com.esukan.dao.EquipmentDAO;
import com.esukan.model.Equipment;
import com.esukan.model.User;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "EquipmentServlet", urlPatterns = {"/manager/equipment", "/equipment"})
public class EquipmentServlet extends HttpServlet {

    private EquipmentDAO equipmentDAO;

    @Override
    public void init() {
        equipmentDAO = new EquipmentDAO();
    }

    private boolean requireManager(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        User user = (User) session.getAttribute("user");
        if (user.getUserRole() != User.UserRole.MANAGER) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Manager access only");
            return false;
        }
        return true;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!requireManager(request, response)) return;

        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                equipmentDAO.delete(id);
                response.sendRedirect(request.getContextPath() + "/manager/equipment?success=Equipment deleted successfully");
                return;
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/manager/equipment?error=Invalid equipment ID");
                return;
            }
        }

        List<Equipment> equipmentList = equipmentDAO.getAllEquipment();
        request.setAttribute("equipmentList", equipmentList);
        request.getRequestDispatcher("/WEB-INF/jsp/equipment/manage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!requireManager(request, response)) return;

        Equipment eq = new Equipment();
        eq.setName(request.getParameter("name"));
        eq.setType(request.getParameter("type"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        eq.setQuantity(quantity);
        eq.setQuantityAvailable(quantity);
        eq.setDailyRate(new BigDecimal(request.getParameter("dailyRate") == null || request.getParameter("dailyRate").isEmpty() ? "0" : request.getParameter("dailyRate")));
        eq.setDeposit(new BigDecimal(request.getParameter("deposit") == null || request.getParameter("deposit").isEmpty() ? "0" : request.getParameter("deposit")));
        eq.setConditionStatus(request.getParameter("conditionStatus") == null ? "GOOD" : request.getParameter("conditionStatus"));
        eq.setActive(!"Maintenance".equals(request.getParameter("status")));
        equipmentDAO.create(eq);
        response.sendRedirect(request.getContextPath() + "/manager/equipment?success=Equipment added successfully");
    }
}
