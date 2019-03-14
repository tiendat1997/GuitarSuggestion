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

    public int checkLogin(String username, String password) throws SQLException, NamingException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null; 
        int roleId = -1; 
        try {
            con = DBUtils.createConnection(); 
            String sql = "select top 1 * from Account where Username=? and Password=?";
                    
            stm = con.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            rs = stm.executeQuery();
            if (rs.next()) {
                roleId = rs.getInt("RoleId");
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return roleId;
    }
}
