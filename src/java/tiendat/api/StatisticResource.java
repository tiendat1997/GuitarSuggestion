/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.api;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import tiendat.dao.GuitarDAO;
import tiendat.dao.SearchGuitarDAO;
import tiendat.dto.StatisticListDTO;
import tiendat.dto.StatisticTypeDTO;

@Path("statistic")
public class StatisticResource {
    @Context
    private UriInfo context; 
    
    @Path("/trend")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public StatisticListDTO getTrendInformation(){
        SearchGuitarDAO searchGuitarDao = new SearchGuitarDAO();
        GuitarDAO guitarDao = new GuitarDAO();  
        StatisticListDTO list = new StatisticListDTO();
        try {            
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
        } catch (NamingException ex) {
            Logger.getLogger(StatisticResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StatisticResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StatisticResource.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
        }        
        return list; 
    }
}
