/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import tiendat.ultility.DBUtils;

/**
 *
 * @author DATTTSE62330
 */
public class AccountDAO implements Serializable {

    public boolean checkLogin(String username, String password, int roleId) throws SQLException, NamingException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null; 
        boolean result = false; 
        try {
            con = DBUtils.createConnection(); 
            String sql = "select top 1 * from Account where Username=? and Password=? and RoleId=?";
                    
            stm = con.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            stm.setInt(3, roleId);
            rs = stm.executeQuery(); 
            if (rs.next()) {
                result = true;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }
}
