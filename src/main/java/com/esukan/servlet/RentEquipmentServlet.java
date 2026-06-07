package com.esukan.servlet;

import com.esukan.dao.EquipmentDAO;
import com.esukan.model.Equipment;
import com.esukan.model.EquipmentRental;
import com.esukan.model.User;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "RentEquipmentServlet", urlPatterns = {"/equipment/rent"})
public class RentEquipmentServlet extends HttpServlet {
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
        
        String equipmentIdParam = request.getParameter("id");
        if (equipmentIdParam != null && !equipmentIdParam.isEmpty()) {
            try {
                int equipmentId = Integer.parseInt(equipmentIdParam);
                Optional<Equipment> equipment = equipmentDAO.getEquipmentById(equipmentId);
                if (equipment.isPresent()) {
                    request.setAttribute("equipment", equipment.get());
                } else {
                    response.sendRedirect(request.getContextPath() + "/equipment?error=Equipment not found");
                    return;
                }
            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
        
        request.getRequestDispatcher("/WEB-INF/jsp/equipment/rent.jsp")
               .forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            int equipmentId = Integer.parseInt(request.getParameter("equipmentId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            Date expectedReturnDate = Date.valueOf(request.getParameter("expectedReturnDate"));
            String conditionOnRental = request.getParameter("conditionOnRental");
            
            if (quantity <= 0) {
                response.sendRedirect(request.getContextPath() + "/equipment/rent?id=" + equipmentId + "&error=Quantity must be at least 1");
                return;
            }
            
            if (expectedReturnDate.toLocalDate().isBefore(LocalDate.now())) {
                response.sendRedirect(request.getContextPath() + "/equipment/rent?id=" + equipmentId + "&error=Return date must be in the future");
                return;
            }
            
            Optional<Equipment> equipmentOpt = equipmentDAO.getEquipmentById(equipmentId);
            if (!equipmentOpt.isPresent()) {
                response.sendRedirect(request.getContextPath() + "/equipment?error=Equipment not found");
                return;
            }
            
            Equipment equipment = equipmentOpt.get();
            
            if (equipment.getQuantityAvailable() < quantity) {
                response.sendRedirect(request.getContextPath() + "/equipment/rent?id=" + equipmentId + "&error=Not enough quantity available");
                return;
            }
            
            long days = ChronoUnit.DAYS.between(LocalDate.now(), expectedReturnDate.toLocalDate());
            if (days < 1) days = 1;
            BigDecimal totalAmount = equipment.getDailyRate().multiply(BigDecimal.valueOf(days)).multiply(BigDecimal.valueOf(quantity));
            BigDecimal depositPaid = equipment.getDepositAmount().multiply(BigDecimal.valueOf(quantity));
            
            EquipmentRental rental = new EquipmentRental();
            rental.setUserId(user.getUserId());
            rental.setEquipmentId(equipmentId);
            rental.setQuantityRented(quantity);
            rental.setRentalDate(new Timestamp(System.currentTimeMillis()));
            rental.setExpectedReturnDate(expectedReturnDate);
            rental.setTotalAmount(totalAmount);
            rental.setDepositPaid(depositPaid);
            rental.setRentalStatus(EquipmentRental.RentalStatus.ACTIVE);
            rental.setConditionOnRental(conditionOnRental);
            
            equipmentDAO.createRental(rental);
            
            response.sendRedirect(request.getContextPath() + "/my-rentals?success=Equipment rented successfully");
            
        } catch (Exception e) {
            String equipmentId = request.getParameter("equipmentId");
            response.sendRedirect(request.getContextPath() + "/equipment/rent?id=" + equipmentId + "&error=" + e.getMessage());
        }
    }
}