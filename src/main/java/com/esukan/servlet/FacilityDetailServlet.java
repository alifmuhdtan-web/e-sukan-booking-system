package com.esukan.servlet;

import com.esukan.dao.FacilityDAO;
import com.esukan.model.Facility;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/facility/detail")
public class FacilityDetailServlet extends HttpServlet {
    private final FacilityDAO facilityDAO = new FacilityDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idParam = request.getParameter("id");
        
        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            Facility facility = facilityDAO.getFacilityById(id);
            request.setAttribute("facility", facility);
        }
        
        request.getRequestDispatcher("/WEB-INF/jsp/facility/detail.jsp").forward(request, response);
    }
}