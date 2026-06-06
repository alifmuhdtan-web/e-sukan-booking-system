package com.esukan.servlet;

import com.esukan.dao.FacilityDAO;
import com.esukan.model.Facility;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "FacilityListServlet", urlPatterns = {"/facility/list"})
public class FacilityListServlet extends HttpServlet {
    private final FacilityDAO facilityDAO = new FacilityDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<Facility> facilityList = facilityDAO.getAllFacilities();
        
        request.setAttribute("facilities", facilityList);
        
        request.getRequestDispatcher("/WEB-INF/jsp/facility/list.jsp").forward(request, response);
    }
}