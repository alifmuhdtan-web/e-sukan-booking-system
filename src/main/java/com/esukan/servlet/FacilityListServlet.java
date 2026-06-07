package com.esukan.servlet;

import com.esukan.dao.FacilityDAO;
import com.esukan.model.Facility;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "FacilityListServlet", urlPatterns = {"/facilities", "/facilities/search", "/facilities/type"})
public class FacilityListServlet extends HttpServlet {
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
        
        String path = request.getServletPath();
        String typeParam = request.getParameter("type");
        String keyword = request.getParameter("keyword");
        
        try {
            List<Facility> facilities;
            String title = "All Facilities";
            
            if ("/facilities/search".equals(path) && keyword != null && !keyword.isEmpty()) {
                facilities = facilityDAO.searchByName(keyword);
                title = "Search Results for: " + keyword;
                request.setAttribute("keyword", keyword);
            } else if (typeParam != null && !typeParam.isEmpty()) {
                facilities = facilityDAO.findByType(Facility.FacilityType.valueOf(typeParam));
                title = typeParam + " Facilities";
                request.setAttribute("selectedType", typeParam);
            } else {
                facilities = facilityDAO.findAvailableFacilities();
                title = "Available Facilities";
            }
            
            request.setAttribute("facilities", facilities);
            request.setAttribute("title", title);
            request.setAttribute("facilityTypes", Facility.FacilityType.values());
            
            request.getRequestDispatcher("/WEB-INF/jsp/facility/list.jsp")
                   .forward(request, response);
                   
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        } catch (IllegalArgumentException e) {
            response.sendRedirect(request.getContextPath() + "/facilities");
        }
    }
}