/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DATTTSE62330
 */
public class DispatchServlet extends HttpServlet {

    private final String HOME_PAGE = "home.jsp";
    private final String RECOMMEND_SERVLET = "RecommendServlet";
    private final String TOP_GUITAR_SERVLET = "TopGuitarServlet";
    private final String STATISTIC_SERVLET = "StatisticServlet";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
        String url = HOME_PAGE;        
        try {            
            String btAction = request.getParameter("btAction");
            if (btAction == null) {
                request.setAttribute("NAV_ACTIVE", 1);
            } else if (btAction.equals("recommend")){
                url = RECOMMEND_SERVLET;
            } else if (btAction.equals("topguitar")){
                request.setAttribute("NAV_ACTIVE", 2);
                url = TOP_GUITAR_SERVLET;
            } else if (btAction.equals("statistic")){
                request.setAttribute("NAV_ACTIVE", 3);
                url = STATISTIC_SERVLET;
            }
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
//            out.close();
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
