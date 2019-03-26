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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.naming.NamingException;
import tiendat.common.GuitarType;
import tiendat.dto.RecommendResultDTO;
import tiendat.dto.StatisticDTO;
import tiendat.dto.StatisticTypeDTO;
import tiendat.generatedObject.Attribute;
import tiendat.generatedObject.Guitar;
import tiendat.ultility.DBUtils;
import tiendat.wsm.Alternative;
import tiendat.wsm.WeightedProductModel;
import tiendat.wsm.WeightedSumModel;

/**
 *
 * @author DATTTSE62330
 */
public class GuitarDAO {

    public StatisticTypeDTO getGuitarStatistics(String typeName) throws NamingException, SQLException, ClassNotFoundException {
        Connection con = null;
        CallableStatement stm = null;
        ResultSet rs = null;
        StatisticTypeDTO type = null;
        try {
            con = DBUtils.createConnection();

            String sql = "{call StatisticGuitarBy" + typeName + "}";
            stm = con.prepareCall(sql);
            rs = stm.executeQuery();
            type = new StatisticTypeDTO(typeName);
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
    public RecommendResultDTO getBestGuitar(int cateId) throws SQLException, NamingException, ClassNotFoundException{ 
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        AttributeDAO attrDao = new AttributeDAO();
        RecommendResultDTO recommendResult = new RecommendResultDTO();
        try {
            con = DBUtils.createConnection();
            String sql = "{call GetTopGuitarByPrice(?,?)}";
            stm = con.prepareCall(sql);
            stm.setInt(1, 50);
            stm.setInt(2, cateId);
            rs = stm.executeQuery();
            List<Alternative> alternativeList = new ArrayList();
            while (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                String category = rs.getString("Category");
                BigDecimal price = rs.getBigDecimal("Price");
                String imageUrl = rs.getString("ImageUrl");
                List<Attribute> attrList = attrDao.getAttributeByGuitarId(id);
                Guitar guitarDto = new Guitar(id, name, category, price, imageUrl, new Guitar.Attributes(attrList));
                Alternative alternative = preNormalization(guitarDto);
                alternativeList.add(alternative);                
            }   
            WeightedProductModel wpm = new WeightedProductModel(alternativeList);
            wpm.calculate();

            for (Alternative alt : alternativeList) {
                Guitar guitar = alt.getGuitar(); // GET GUITAR THAT HAS THE SCORED
                recommendResult.getGuitar().add(guitar);
            }
            Collections.sort(recommendResult.getGuitar());
            // GET FIRST 10 ELEMENT;
            List<Guitar> topGuitars = recommendResult.getGuitar().stream().limit(10).collect(Collectors.toList()); 
            recommendResult.setGuitar(topGuitars);
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

    public RecommendResultDTO getTopGuitar(int cateId) throws NamingException, SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        AttributeDAO attrDao = new AttributeDAO();
        RecommendResultDTO recommendResult = new RecommendResultDTO();
        try {
            con = DBUtils.createConnection();
            String sql = "{call GetTopGuitarByPrice(?,?)}";
            stm = con.prepareCall(sql);
            stm.setInt(1, 10);
            stm.setInt(2, cateId);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                String category = rs.getString("Category");
                BigDecimal price = rs.getBigDecimal("Price");
                String imageUrl = rs.getString("ImageUrl");
                List<Attribute> attrList = attrDao.getAttributeByGuitarId(id);
                Guitar guitarDto = new Guitar(id, name, category, price, imageUrl, new Guitar.Attributes(attrList));
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
    /* DOES NOT INCLUDE SEARCH ATTRIBUTE */
    private Alternative preNormalization(Guitar guitar){
        Alternative alternative;
        double vPrice = 0;
        double vWood = 0;
        double vBrand = 0;
        double vOrigin = 0;
        vPrice = guitar.getPrice().doubleValue();

        String attrStr = guitar.getAttributes().toString().toLowerCase();
        /* WOOD */
        // GỖ CAO CẤP
        if (attrStr.contains("cẩm lai") || attrStr.contains("hồng sắc") || attrStr.contains("mun")
                || attrStr.contains("rosewood") || attrStr.contains("óc chó") || attrStr.contains("walnut")
                || attrStr.contains("thích") || attrStr.contains("maple") || attrStr.contains("cẩm")
                || attrStr.contains("ocbolo") || attrStr.contains("zitricote") || attrStr.contains("ovangkol")
                || attrStr.contains("hawaiian koa") || attrStr.contains("ovangkol")) {
            vWood += 3;
        } // GÕ TRUNG CẤP
        else if (attrStr.contains("điệp") || attrStr.contains("còng") || attrStr.contains("thông")
                || attrStr.contains("cườm") || attrStr.contains("dái ngựa") || attrStr.contains("mahogany")
                || attrStr.contains("keo") || attrStr.contains("koa") || attrStr.contains("tuyết tùng")
                || attrStr.contains("cồng") || attrStr.contains("thao lao") || attrStr.contains("sitka")
                || attrStr.contains("cedar") || attrStr.contains("gụ") || attrStr.contains("spruce")) {
            vWood += 2;
        } // GỖ PHỔ THÔNG
        else if (attrStr.contains("hồng đào") || attrStr.contains("laminate")
                || attrStr.contains("nato") 
                || attrStr.contains("sapele")) {
            vWood += 1;
        } else {
            vWood += 1;
        }
        
        // GỖ THỊT 
        if (attrStr.contains("nguyên") || attrStr.contains("solid") || attrStr.contains("thịt")){
            vWood += 1;
        }
        // GỖ ÉP
        if (attrStr.contains("ván") || attrStr.contains("ép")){
            vWood -= 1;
        }
        /* Branding */
        String nameStr = guitar.getName().toLowerCase();

        if (nameStr.contains("martin")
                || nameStr.contains("taylor")
                || nameStr.contains("fender")
                || nameStr.contains("samick")
                || nameStr.contains("yamaha")
                || nameStr.contains("suzuki")
                || nameStr.contains("takamine")
                || nameStr.contains("Ibanez")
                || nameStr.contains("almansa")) {
            vBrand = 3;
        }
        if (nameStr.contains("cordoba")
                || nameStr.contains("cort")
                || nameStr.contains("tanglewood")
                || nameStr.contains("aria")
                || nameStr.contains("sigma")
                || nameStr.contains("stagg")
                || nameStr.contains("everest")
                || nameStr.contains("saga")) {
            vBrand = 2;
        } else if (nameStr.contains("lazer")
                || nameStr.contains("kapok")
                || nameStr.contains("caravan")
                || nameStr.contains("epiphone")
                || nameStr.contains("poshman")
                || nameStr.contains("ng")
                || nameStr.contains("hohner")
                || nameStr.contains("kepma")
                || nameStr.contains("mantic")
                || nameStr.contains("morrision")
                || nameStr.contains("rosen")) {
            vBrand = 1;
        } else {
            vBrand = 1;
        }

        /* ORIGIN */
        if (attrStr.contains("mỹ")
                || attrStr.contains("usa")
                || attrStr.contains("america")
                || attrStr.contains("nhật")
                || attrStr.contains("japan")
                || attrStr.contains("tây ban nha")
                || attrStr.contains("mexico")
                || attrStr.contains("italy")
                || attrStr.contains("bỉ")) {
            vOrigin = 3;
        } else if (attrStr.contains("việt nam") 
                || attrStr.contains("vietnam")
                || attrStr.contains("trung quốc")
                || attrStr.contains("china")
                || attrStr.contains("taiwan")
                || attrStr.contains("indonesia")) {
            vOrigin = 1;
        } else {
            vOrigin = 2;
        }
        
        alternative = new Alternative(guitar, vPrice, vWood, vBrand, vOrigin, vBrand);        
        return alternative;
    }
    /* INCLUDE SEARCH ATTRIBUTE */
    private Alternative preNormalization(Guitar guitar, String cateName, String bodyStyle, boolean isVietnam) {
        Alternative alternative;
        double vPrice = 0;
        double vWood = 0;
        double vBrand = 0;
        double vOrigin = 0;
        double vFitSearch = 0;

        vPrice = guitar.getPrice().doubleValue();

        String attrStr = guitar.getAttributes().toString().toLowerCase();
        /* WOOD */
        // GỖ CAO CẤP
        if (attrStr.contains("cẩm lai") || attrStr.contains("hồng sắc") || attrStr.contains("mun")
                || attrStr.contains("rosewood") || attrStr.contains("óc chó") || attrStr.contains("walnut")
                || attrStr.contains("thích") || attrStr.contains("maple") || attrStr.contains("cẩm")
                || attrStr.contains("ocbolo") || attrStr.contains("zitricote") || attrStr.contains("ovangkol")
                || attrStr.contains("hawaiian koa") || attrStr.contains("ovangkol")) {
            vWood += 3;
        } // GÕ TRUNG CẤP
        else if (attrStr.contains("điệp") || attrStr.contains("còng") || attrStr.contains("thông")
                || attrStr.contains("cườm") || attrStr.contains("dái ngựa") || attrStr.contains("mahogany")
                || attrStr.contains("keo") || attrStr.contains("koa") || attrStr.contains("tuyết tùng")
                || attrStr.contains("cồng") || attrStr.contains("thao lao") || attrStr.contains("sitka")
                || attrStr.contains("cedar") || attrStr.contains("gụ") || attrStr.contains("spruce")) {
            vWood += 2;
        } // GỖ PHỔ THÔNG
        else if (attrStr.contains("hồng đào") || attrStr.contains("laminate")
                || attrStr.contains("nato") 
                || attrStr.contains("sapele")) {
            vWood += 1;
        } else {
            vWood += 1;
        }
        
        // GỖ THỊT 
        if (attrStr.contains("nguyên") || attrStr.contains("solid") || attrStr.contains("thịt")){
            vWood += 1;
        }
        // GỖ ÉP
        if (attrStr.contains("ván") || attrStr.contains("ép")){
            vWood -= 1;
        }
        /* Branding */
        String nameStr = guitar.getName().toLowerCase();

        if (nameStr.contains("martin")
                || nameStr.contains("taylor")
                || nameStr.contains("fender")
                || nameStr.contains("samick")
                || nameStr.contains("yamaha")
                || nameStr.contains("suzuki")
                || nameStr.contains("takamine")
                || nameStr.contains("Ibanez")
                || nameStr.contains("almansa")) {
            vBrand = 3;
        }
        if (nameStr.contains("cordoba")
                || nameStr.contains("cort")
                || nameStr.contains("tanglewood")
                || nameStr.contains("aria")
                || nameStr.contains("sigma")
                || nameStr.contains("stagg")
                || nameStr.contains("everest")
                || nameStr.contains("saga")) {
            vBrand = 2;
        } else if (nameStr.contains("lazer")
                || nameStr.contains("kapok")
                || nameStr.contains("caravan")
                || nameStr.contains("epiphone")
                || nameStr.contains("poshman")
                || nameStr.contains("ng")
                || nameStr.contains("hohner")
                || nameStr.contains("kepma")
                || nameStr.contains("mantic")
                || nameStr.contains("morrision")
                || nameStr.contains("rosen")) {
            vBrand = 1;
        } else {
            vBrand = 1;
        }

        /* ORIGIN */
        if (attrStr.contains("mỹ")
                || attrStr.contains("usa")
                || attrStr.contains("america")
                || attrStr.contains("nhật")
                || attrStr.contains("japan")
                || attrStr.contains("tây ban nha")
                || attrStr.contains("mexico")
                || attrStr.contains("italy")
                || attrStr.contains("bỉ")) {
            vOrigin = 3;
        } else if (attrStr.contains("việt nam") 
                || attrStr.contains("vietnam")
                || attrStr.contains("trung quốc")
                || attrStr.contains("china")
                || attrStr.contains("taiwan")
                || attrStr.contains("indonesia")) {
            vOrigin = 1;
        } else {
            vOrigin = 2;
        }

        /* Match Origin */
        if (isVietnam && (attrStr.contains("việt nam") || attrStr.contains("vietnam"))) {
            vFitSearch += 1;
        }
        if (!isVietnam && (!attrStr.contains("việt nam") && !attrStr.contains("vietnam"))) {
            vFitSearch += 1;
        }
        /* Match Body Style */
        if (bodyStyle.equals("full") && (attrStr.contains("đầy") || attrStr.contains("dreadnought"))) {
            vFitSearch += 1;
        }
        if (bodyStyle.equals("cutaway") && (attrStr.contains("khuyết") || attrStr.contains("cutaway"))) {
            vFitSearch += 1;
        }

        /* Match CATEGORY NAME */
        if (nameStr.contains(cateName)) {
            vFitSearch += 1;
        }

        alternative = new Alternative(guitar, vPrice, vWood, vBrand, vOrigin, vFitSearch);
        return alternative;
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
            String cateName = "";
            int cateId = 0;
            switch (genre) {
                case "modern":
                    cateId = GuitarType.ACOUSTIC;
                    cateName = GuitarType.ACOUSTIC_STRING;
                    break;
                case "classic":
                    cateId = GuitarType.CLASSIC;
                    cateName = GuitarType.CLASSICAL_STRING;
                    break;
                case "electric":
                    cateId = GuitarType.ELECTRIC;
                    cateName = GuitarType.ELECTRIC_STRING;
                    break;
                case "vongco":
                    cateId = GuitarType.VONGCO;
                    cateName = GuitarType.VONGCO_STRING;
                    break;
                case "ukulele":
                    cateId = GuitarType.UKULELE;
                    cateName = GuitarType.UKULELE_STRING;
                default:
                    break;
            }

            // FILTER BY PRICE  minPrice maxPrice
            double minPrice = 0;
            double maxPrice = 1000000000;
            switch (priceLevel) {
                case "low":
                    minPrice = 0;
                    maxPrice = 3500000;
                    break;
                case "middle":
                    minPrice = 3500000;
                    maxPrice = 10000000;
                    break;
                case "high":
                    minPrice = 10000000;
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
            stm.setInt(1, cateId);
            stm.setDouble(2, minPrice);
            stm.setDouble(3, maxPrice);
            stm.setNString(4, attrBody);
            stm.setBoolean(5, isVietnam); // origin

            rs = stm.executeQuery();

            List<Alternative> alternativeList = new ArrayList();

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
                Alternative alternative = preNormalization(guitarDto, cateName, bodyStyle, isVietnam);
                alternativeList.add(alternative);
            }

            WeightedSumModel wsm = new WeightedSumModel(alternativeList);
            wsm.calculate();

            for (Alternative alt : alternativeList) {
                Guitar guitar = alt.getGuitar(); // GET GUITAR THAT HAS THE SCORED
                recommendResult.getGuitar().add(guitar);
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
                String sql = "{call AddGuitar(?,?,?,?,?)}";
                stm = con.prepareCall(sql);
                stm.setString(1, guitar.getName());
                stm.setString(2, guitar.getCategory().trim()); // DESCRIPTION IN TABLE GUITAR
                stm.setDouble(3, guitar.getPrice().doubleValue());
                stm.setString(4, guitar.getImageUrl());
                // SET CATEGORY ID 
                String categoryName = guitar.getCategory().toLowerCase();
                String guitarName = guitar.getName().trim().toLowerCase();
                int cateId;
                if (guitarName.contains(GuitarType.ACOUSTIC_STRING)) {
                    cateId = GuitarType.ACOUSTIC;
                } else if (guitarName.contains(GuitarType.CLASSICAL_STRING) || guitarName.contains("cổ điển")) {
                    cateId = GuitarType.CLASSIC;
                } else if (guitarName.contains(GuitarType.ELECTRIC_STRING)
                        || guitarName.contains(GuitarType.BASS_STRING)
                        || guitarName.contains("điện")) {
                    cateId = GuitarType.ELECTRIC;
                } else if (guitarName.contains(GuitarType.UKULELE_STRING)) {
                    cateId = GuitarType.UKULELE;
                } else if (guitarName.contains(GuitarType.VONGCO_STRING)) {
                    cateId = GuitarType.VONGCO;
                } else {
                    cateId = GuitarType.ACOUSTIC;
                }
                stm.setInt(5, cateId);

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
