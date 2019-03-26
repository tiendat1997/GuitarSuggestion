/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.api;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import tiendat.dao.GuitarDAO;
import tiendat.dao.SearchGuitarDAO;
import tiendat.dto.RecommendResultDTO;
import tiendat.dto.SearchGuitarDTO;

@Path("guitar")
public class GuitarResource {

    @Context
    private UriInfo context;

    @Context
    private Request request;

    @Context
    private Response response;

    public GuitarResource() {
    }

    @Path("/recommend")
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public RecommendResultDTO recommend(@FormParam("music-genre") String genre,
            @FormParam("body-style") String bodyStyle,
            @FormParam("price-level") String priceLevel,
            @FormParam("origin") String origin) {
        
        try {
            GuitarDAO guitarDao = new GuitarDAO();
            SearchGuitarDAO searchDao = new SearchGuitarDAO();
            SearchGuitarDTO searchDto = new SearchGuitarDTO(genre, bodyStyle, priceLevel, origin);
            searchDao.addSearchGuitar(searchDto);
            RecommendResultDTO recommendDto = guitarDao.recommendGuitar(genre, bodyStyle, priceLevel, origin);
            return recommendDto;
        } catch (NamingException ex) {
            Logger.getLogger(GuitarResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GuitarResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GuitarResource.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }        
        return null;
    }
}
