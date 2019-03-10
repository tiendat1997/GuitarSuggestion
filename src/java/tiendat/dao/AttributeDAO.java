/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import tiendat.generatedObject.Attribute;
import tiendat.ultility.DBUtils;

/**
 *
 * @author DATTTSE62330
 */
public class AttributeDAO implements Serializable{    
    
    public List<Attribute> getAttributeByGuitarId(int guitarId) throws SQLException, NamingException, ClassNotFoundException{
        Connection con = null;
        PreparedStatement stm = null; 
        ResultSet rs = null; 
        List<Attribute> attrResult = new ArrayList<>(); 
        try {
            con = DBUtils.createConnection();
            String sql = "Select * from Attribute a where a.GuitarId = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, guitarId);
            rs = stm.executeQuery(); 
            while (rs.next()) {
                String name = rs.getString("Name");
                String content = rs.getString("Content");                
                attrResult.add(new Attribute(name, content));
            }            
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }           
        }
        return attrResult;
    }
    public boolean addAttribute(Attribute attribute, int guitarId) throws NamingException, SQLException, ClassNotFoundException{
        Connection con = null;
        CallableStatement stm = null;
        try {
            con = DBUtils.createConnection();
            String sql = "{call AddAttribute(?,?,?)}";
            stm = con.prepareCall(sql);
            stm.setString(1, attribute.getName().trim());
            stm.setString(2, attribute.getContent().trim());
            stm.setInt(3, guitarId);
            boolean result = stm.execute();
            if (result) {
                System.out.println("AttributeDAO ADDED successfully ----------------------------");
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
