package com.esukan.servlet;

import com.esukan.dao.FacilityDAO;
import com.esukan.model.Facility;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "FacilityDetailServlet", urlPatterns = {"/facility/detail"})
public class FacilityDetailServlet extends HttpServlet {
    private FacilityDAO facilityDAO;
    
    @Override
    public void init() {
        facilityDAO = new FacilityDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String facilityIdParam = request.getParameter("id");
        if (facilityIdParam == null || facilityIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/facilities");
            return;
        }
        
        try {
            int facilityId = Integer.parseInt(facilityIdParam);
            Optional<Facility> facilityOpt = facilityDAO.findById(facilityId);
            
            if (facilityOpt.isPresent()) {
                request.setAttribute("facility", facilityOpt.get());
                request.getRequestDispatcher("/WEB-INF/jsp/facility/detail.jsp")
                       .forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/facilities?error=Facility not found");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/facilities?error=Invalid facility ID");
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
}