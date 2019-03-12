/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import javax.naming.NamingException;
import tiendat.dto.RecommendResultDTO;
import tiendat.generatedObject.Attribute;
import tiendat.generatedObject.Guitar;
import tiendat.ultility.DBUtils;

/**
 *
 * @author DATTTSE62330
 */
public class GuitarDAO {

    public RecommendResultDTO getTopGuitar() throws NamingException, SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        AttributeDAO attrDao = new AttributeDAO();
        RecommendResultDTO recommendResult = new RecommendResultDTO();
        try {
            con = DBUtils.createConnection();
            String sql = "select TOP 20 g.Id,g.Name,g.Category,g.ImageUrl,g.Price from Guitar g order by g.Price desc";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                String category = rs.getString("Category");
                BigDecimal price = rs.getBigDecimal("Price");
                String imageUrl = rs.getString("ImageUrl");
                List<Attribute> attrList = attrDao.getAttributeByGuitarId(id);
                Guitar.Attributes attributes = new Guitar.Attributes(attrList);
                Guitar guitarDto = new Guitar(id, name, category, price, imageUrl, attributes);
                recommendResult.getGuitar().add(guitarDto);
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return recommendResult;
    }

    // WSM : weighted sum model
    private double getScoreFromWSM(Guitar guitar, String genreName, double minPrice, double maxPrice, String bodyStyle, boolean isVietnam) {
        double score = 0;
        // SET UP criteria 
        double genrePercent = 0.45;
        double pricePercent = 0.1;
        double stylePercent = 0.15;
        double woodPercent = 0.15;
        double originPercent = 0.1;
        double brandPercent = 0.05;

        String attrStr = guitar.getAttributes().toString().toLowerCase();
        if (guitar.getCategory().toLowerCase().contains(genreName)) {
            score += genrePercent * 10;
        }
        if (guitar.getPrice().doubleValue() >= minPrice && guitar.getPrice().doubleValue() <= maxPrice) {
            score += pricePercent * 10;
        }
        if ((attrStr.contains("đầy") && bodyStyle.equals("full"))
                || (attrStr.contains("khuyết") && bodyStyle.equals("cutaway"))) {
            score += stylePercent * 10;
        }
        if ((attrStr.contains("việt") && isVietnam)
                || (!attrStr.contains("việt") && !isVietnam)) {
            score += originPercent * 10;
        }
                // Wood style 
        // GỖ CAO CẤP
        if (attrStr.contains("cẩm lai") || attrStr.contains("hồng sắc") || attrStr.contains("mun")
                || attrStr.contains("rosewood") || attrStr.contains("óc chó") || attrStr.contains("walnut")
                || attrStr.contains("thích") || attrStr.contains("maple") || attrStr.contains("cẩm")) {
            score += woodPercent * 9;
        } // GÕ TRUNG CẤP
        else if (attrStr.contains("điệp") || attrStr.contains("còng") || attrStr.contains("thông")
                || attrStr.contains("cườm") || attrStr.contains("dái ngựa") || attrStr.contains("mahogany")
                || attrStr.contains("keo") || attrStr.contains("koa") || attrStr.contains("tuyết tùng")
                || attrStr.contains("cồng") || attrStr.contains("còng") || attrStr.contains("thao lao")
                || attrStr.contains("sitka") || attrStr.contains("cedar") || attrStr.contains("gụ")
                || attrStr.contains("spruce ")) {
            score += woodPercent * 7;
        } // GỖ PHỔ THÔNG
        else if (attrStr.contains("hồng đào") || attrStr.contains("laminate") || attrStr.contains("nato")) {
            score += woodPercent * 5;
        }

        // GỖ NGUYÊN MIẾNG                
        if (attrStr.contains("solid") || attrStr.contains("nguyên")) {
            score += woodPercent * 1;
        }
        if (attrStr.contains("ép") || attrStr.contains("ván")) {
            score -= woodPercent * 2;
        }

        // THƯƠNG HIỆU
        String nameLowercase = guitar.getName().toLowerCase();
        if (nameLowercase.contains("taylor") || nameLowercase.contains("yamaha")
                || nameLowercase.contains("yamaha") || nameLowercase.contains("fender")
                || nameLowercase.contains("takamine") || nameLowercase.contains("martin")
                || nameLowercase.contains("tanglewood") || nameLowercase.contains("elixir")) {
            score += brandPercent * 8;
        } else {
            score += brandPercent * 4;
        }
                
        return score;
    }

    public RecommendResultDTO recommendGuitar(String genre, String bodyStyle, String priceLevel, String origin) throws NamingException, SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        CallableStatement callStm = null;
        ResultSet rs = null;
        AttributeDAO attrDao = new AttributeDAO();

        RecommendResultDTO recommendResult = new RecommendResultDTO();
        try {
            con = DBUtils.createConnection();

            // GET BY GENRE  
            String genreName = "";
            switch (genre) {
                case "acoustic":
                    genreName = "acoustic";
                    break;
                case "classic":
                    genreName = "classic";
                    break;
                case "electric":
                    genreName = "electric";
                    break;
                case "vongco":
                    genreName = "vọng cổ";
                    break;
                default:
                    break;
            }

            // FILTER BY PRICE  minPrice maxPrice
            double minPrice = 0;
            double maxPrice = 1000000000;
            switch (priceLevel) {
                case "low":
                    minPrice = 0;
                    maxPrice = 1500000;
                    break;
                case "middle":
                    minPrice = 1500001;
                    maxPrice = 5000000;
                    break;
                case "high":
                    minPrice = 5000001;
                    break;
                default:
                    break;
            }

            // FILTER Attributes 
            String attrBody = "";
            switch (bodyStyle) {
                case "full":
                    attrBody = "%đầy%";
                    break;
                case "cutaway":
                    attrBody = "%khuyết%";
                    break;
                default:
                    break;
            }
            boolean isVietnam = true;
            switch (origin) {
                case "vietnam":
                    isVietnam = true;
                    break;
                case "foreign":
                    isVietnam = false;
                    break;
            }

            String sql = "{call RecommendGuitar(?,?,?,?,?)}";

            stm = con.prepareStatement(sql);
            stm.setNString(1, "%" + genreName + "%");
            stm.setDouble(2, minPrice);
            stm.setDouble(3, maxPrice);
            stm.setNString(4, attrBody);
            stm.setBoolean(5, isVietnam); // origin

            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                String category = rs.getString("Category");
                BigDecimal price = rs.getBigDecimal("Price");
                String imageUrl = rs.getString("ImageUrl");
                List<Attribute> attrList = attrDao.getAttributeByGuitarId(id);
                Guitar.Attributes attributes = new Guitar.Attributes(attrList);
                Guitar guitarDto = new Guitar(id, name, category, price, imageUrl, attributes);
                // Processing Score Weigted Score matrix             
                double weightedScore = this.getScoreFromWSM(guitarDto, genreName, minPrice, maxPrice, bodyStyle, isVietnam);
                guitarDto.setWeightedScore((float) weightedScore);
                recommendResult.getGuitar().add(guitarDto);
            }
                      
            Collections.sort(recommendResult.getGuitar());

        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return recommendResult;
    }

    public int getGuitarByName(String name) throws SQLException, NamingException, ClassNotFoundException {
        Connection con = null;
        CallableStatement stm = null;
        int guitarId = 0;
        try {
            con = DBUtils.createConnection();
            String sql = "{call GetGuitarByName(?,?)}";
            stm = con.prepareCall(sql);
            stm.setString(1, name);
            stm.registerOutParameter(2, java.sql.Types.INTEGER);
            boolean result = stm.execute();
            if (result) {
                guitarId = stm.getInt(2);
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return guitarId;
    }

    public boolean addGuitar(Guitar guitar) throws SQLException, NamingException, ClassNotFoundException {
        Connection con = null;
        CallableStatement stm = null;
        try {
            con = DBUtils.createConnection();
            if (con != null) {
                String sql = "{call AddGuitar(?,?,?,?)}";
                stm = con.prepareCall(sql);
                stm.setString(1, guitar.getName());
                stm.setString(2, guitar.getCategory().trim());
                stm.setDouble(3, guitar.getPrice().doubleValue());
                stm.setString(4, guitar.getImageUrl());

                boolean result = stm.execute();
                if (result) {
                    System.out.println("GuitarDAO ADDED successfully ----------------------------");
                    return true;
                }
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
