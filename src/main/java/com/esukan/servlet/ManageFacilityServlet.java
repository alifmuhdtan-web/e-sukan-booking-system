package com.esukan.servlet;

import com.esukan.dao.FacilityDAO;
import com.esukan.model.Facility;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ManageFacilityServlet", urlPatterns = {"/manager/facility"})
public class ManageFacilityServlet extends HttpServlet {
    private final FacilityDAO facilityDAO = new FacilityDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            facilityDAO.deleteFacility(id);
            response.sendRedirect(request.getContextPath() + "/manager/facility");
            return;
        }
        
        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Facility facility = facilityDAO.getFacilityById(id);
            request.setAttribute("facilityToEdit", facility);
        }

        request.setAttribute("facilities", facilityDAO.getAllFacilities());
        request.getRequestDispatcher("/WEB-INF/jsp/facility/manage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idParam = request.getParameter("id");
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("pricePerHour"));
        String status = request.getParameter("status");

        Facility facility = new Facility();
        facility.setName(name);
        facility.setType(type);
        facility.setDescription(description);
        facility.setPricePerHour(price);
        facility.setStatus(status);

        if (idParam == null || idParam.trim().isEmpty()) {
            facilityDAO.insertFacility(facility);
        } else {
            facility.setId(Integer.parseInt(idParam));
            facilityDAO.updateFacility(facility);
        }

        response.sendRedirect(request.getContextPath() + "/manager/facility");
    }
}