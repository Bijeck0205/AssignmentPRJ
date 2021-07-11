/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sump.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sump.registration.RegistrationDAO;
import sump.registration.RegistrationDTO;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "SearchLastNameServlet", urlPatterns = {"/SearchLastNameServlet"})
public class SearchLastNameServlet extends HttpServlet {

    private final String SEARCH_PAGE = "searchPage";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String searchValue = request.getParameter("txtSearchValue");
        String url = SEARCH_PAGE;

        try {
            if (!searchValue.trim().isEmpty()) {
                //callDAO
                RegistrationDAO dao = new RegistrationDAO();
                dao.searchLastname(searchValue);

                List<RegistrationDTO> result = dao.getAccounts();
                //giao tiep du lieu qua scope
                request.setAttribute("SEARCH_RESULT", result);

            }//end searchValue has inputted value
        } catch (NamingException ex) {
            log("SearchLastNameServlet _ Naming " + ex.getMessage());
        } catch (SQLException ex) {
            log("SearchLastNameServlet _ SQL " + ex.getMessage());
        } finally {
            ServletContext context = request.getServletContext();
            Map<String, String> roadmap = (Map<String, String>) context.getAttribute("ROADMAP");
            if (roadmap != null) {
                url = roadmap.get(url);
            }
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
