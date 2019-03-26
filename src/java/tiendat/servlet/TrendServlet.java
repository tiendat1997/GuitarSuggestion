/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tiendat.dao.GuitarDAO;
import tiendat.dao.SearchGuitarDAO;
import tiendat.dto.StatisticListDTO;
import tiendat.dto.StatisticTypeDTO;
import tiendat.ultility.XMLUltils;

/**
 *
 * @author DATTTSE62330
 */
public class TrendServlet extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/xml;charset=UTF-8");

        OutputStream os = response.getOutputStream();
        SearchGuitarDAO searchGuitarDao;
        GuitarDAO guitarDao;                
        try {            
            searchGuitarDao = new SearchGuitarDAO();
            guitarDao = new GuitarDAO();
            StatisticListDTO list = new StatisticListDTO();
            List<StatisticTypeDTO> types = new ArrayList<>();                        
            StatisticTypeDTO genre = searchGuitarDao.getTrendStatistics("Genre");            
            StatisticTypeDTO body = searchGuitarDao.getTrendStatistics("BodyStyle");
            StatisticTypeDTO priceLevel = searchGuitarDao.getTrendStatistics("PriceLevel");
            StatisticTypeDTO trendOrigin = searchGuitarDao.getTrendStatistics("Origin");                                    
            types.add(genre);
            types.add(body);
            types.add(priceLevel);
            types.add(trendOrigin);
            
            StatisticTypeDTO category = guitarDao.getGuitarStatistics("Category");
            types.add(category);
            StatisticTypeDTO price = guitarDao.getGuitarStatistics("Price");
            types.add(price);
            StatisticTypeDTO origin = guitarDao.getGuitarStatistics("Origin");
            types.add(origin);
            StatisticTypeDTO brand = guitarDao.getGuitarStatistics("Brand");
            types.add(brand);
            StatisticTypeDTO wood = guitarDao.getGuitarStatistics("Wood");
            types.add(wood);
            list.setStatistic(types);
            XMLUltils.marshallToTransfer(list, os);
        } catch (NamingException ex) {
            Logger.getLogger(TrendServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TrendServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TrendServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
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
