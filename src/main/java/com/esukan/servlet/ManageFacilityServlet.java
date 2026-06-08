package com.esukan.servlet;

import com.esukan.dao.FacilityDAO;
import com.esukan.model.Facility;
import com.esukan.model.User;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ManageFacilityServlet", urlPatterns = {"/manager/facilities", "/manager/facility/create", "/manager/facility/update", "/manager/facility/delete"})
public class ManageFacilityServlet extends HttpServlet {
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
        
        User user = (User) session.getAttribute("user");
        if (user.getUserRole() != User.UserRole.MANAGER) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Manager access only");
            return;
        }
        
        String action = request.getParameter("action");
        
        try {
            if ("edit".equals(action)) {
                int facilityId = Integer.parseInt(request.getParameter("id"));
                Facility facility = facilityDAO.findById(facilityId).orElse(null);
                request.setAttribute("facility", facility);
                request.setAttribute("isEdit", true);
                request.getRequestDispatcher("/WEB-INF/jsp/facility/manage.jsp")
                       .forward(request, response);
                return;
                
            } else if ("delete".equals(action)) {
                int facilityId = Integer.parseInt(request.getParameter("id"));
                facilityDAO.delete(facilityId);
                response.sendRedirect(request.getContextPath() + "/manager/facilities?success=Facility deleted");
                return;
            }
        } catch (SQLException e) {
            response.sendRedirect(request.getContextPath() + "/manager/facilities?error=Database error");
            return;
        }
        
        // List all facilities
        try {
            List<Facility> facilities = facilityDAO.findAll();
            request.setAttribute("facilities", facilities);
            request.setAttribute("facilityTypes", Facility.FacilityType.values());
            request.getRequestDispatcher("/WEB-INF/jsp/facility/manage.jsp")
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
        
        String action = request.getParameter("action");
        
        try {
            if ("create".equals(action)) {
                // Create new facility
                Facility facility = new Facility();
                facility.setFacilityName(request.getParameter("facilityName"));
                facility.setFacilityType(Facility.FacilityType.valueOf(request.getParameter("facilityType")));
                facility.setDescription(request.getParameter("description"));
                facility.setLocation(request.getParameter("location"));
                facility.setCapacity(Integer.parseInt(request.getParameter("capacity")));
                facility.setHourlyRate(new BigDecimal(request.getParameter("hourlyRate")));
                facility.setImageUrl(request.getParameter("imageUrl"));
                
                // Handle time fields with null check
                String openingTimeStr = request.getParameter("openingTime");
                String closingTimeStr = request.getParameter("closingTime");
                
                if (openingTimeStr != null && !openingTimeStr.isEmpty()) {
                    facility.setOpeningTime(Time.valueOf(openingTimeStr + ":00"));
                } else {
                    facility.setOpeningTime(Time.valueOf("08:00:00"));
                }
                
                if (closingTimeStr != null && !closingTimeStr.isEmpty()) {
                    facility.setClosingTime(Time.valueOf(closingTimeStr + ":00"));
                } else {
                    facility.setClosingTime(Time.valueOf("22:00:00"));
                }
                
                facility.setAvailable(true);
                
                facilityDAO.create(facility);
                response.sendRedirect(request.getContextPath() + "/manager/facilities?success=Facility created");
                
            } else if ("update".equals(action)) {
                // Update existing facility
                int facilityId = Integer.parseInt(request.getParameter("facilityId"));
                Facility facility = facilityDAO.findById(facilityId).orElse(null);
                
                if (facility != null) {
                    facility.setFacilityName(request.getParameter("facilityName"));
                    facility.setFacilityType(Facility.FacilityType.valueOf(request.getParameter("facilityType")));
                    facility.setDescription(request.getParameter("description"));
                    facility.setLocation(request.getParameter("location"));
                    facility.setCapacity(Integer.parseInt(request.getParameter("capacity")));
                    facility.setHourlyRate(new BigDecimal(request.getParameter("hourlyRate")));
                    facility.setImageUrl(request.getParameter("imageUrl"));
                    
                    // Handle time fields with null check
                    String openingTimeStr = request.getParameter("openingTime");
                    String closingTimeStr = request.getParameter("closingTime");
                    
                    if (openingTimeStr != null && !openingTimeStr.isEmpty()) {
                        facility.setOpeningTime(Time.valueOf(openingTimeStr + ":00"));
                    }
                    
                    if (closingTimeStr != null && !closingTimeStr.isEmpty()) {
                        facility.setClosingTime(Time.valueOf(closingTimeStr + ":00"));
                    }
                    
                    // Handle status
                    String statusParam = request.getParameter("isAvailable");
                    facility.setAvailable("true".equals(statusParam));
                    
                    facilityDAO.update(facility);
                    response.sendRedirect(request.getContextPath() + "/manager/facilities?success=Facility updated");
                } else {
                    response.sendRedirect(request.getContextPath() + "/manager/facilities?error=Facility not found");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/manager/facilities");
            }
        } catch (IllegalArgumentException e) {
            response.sendRedirect(request.getContextPath() + "/manager/facilities?error=Invalid input: " + e.getMessage());
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/manager/facilities?error=" + e.getMessage());
        }
    }
}