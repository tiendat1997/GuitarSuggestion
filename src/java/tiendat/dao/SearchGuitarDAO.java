/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import tiendat.dto.SearchGuitarDTO;
import tiendat.dto.StatisticDTO;
import tiendat.dto.StatisticListDTO;
import tiendat.dto.StatisticTypeDTO;
import tiendat.ultility.DBUtils;

/**
 *
 * @author DATTTSE62330
 */
public class SearchGuitarDAO implements Serializable {

    public StatisticTypeDTO getTrendStatistics(String typeName) throws NamingException, SQLException, ClassNotFoundException {
        Connection con = null;
        CallableStatement stm = null;
        ResultSet rs = null;
        StatisticTypeDTO type = null;
        try {
            con = DBUtils.createConnection();
            
            String sql = "{call AnalyticSearchTrendBy" + typeName + "}";
            stm = con.prepareCall(sql);
            rs = stm.executeQuery();
            type = new StatisticTypeDTO("Trend" + typeName);
            List<StatisticDTO> items = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getNString("name");
                int count = rs.getInt("count");
                StatisticDTO item = new StatisticDTO(name, count);
                items.add(item);
            }
            type.setItems(items);
            return type;
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }    
    }
    
    public boolean addSearchGuitar(SearchGuitarDTO dto) throws NamingException, SQLException, ClassNotFoundException {
        Connection con = null;
        CallableStatement stm = null;
        try {
            con = DBUtils.createConnection();
            String sql = "{call AddSearchResult(?,?,?,?,?)}";
            stm = con.prepareCall(sql);
            String genre = dto.getMusicGenre();
            String body = dto.getBodyStyle();
            String price = dto.getPriceLevel();
            String origin = dto.getOrigin();
            int userId = dto.getUserId();

            stm.setString(1, genre);
            stm.setString(2, body);
            stm.setString(3, price);
            stm.setString(4, origin);
            stm.setInt(5, userId);

            boolean result = stm.execute();
            if (result) {
                System.out.println("Add searchResult successfully!");
                return true;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
}
