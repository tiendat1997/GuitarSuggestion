/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;
import tiendat.generatedObject.Category;
import tiendat.ultility.DBUtils;

/**
 *
 * @author DATTTSE62330
 */
public class CategoryDAO implements Serializable {

    public boolean addCategory(Category category) throws NamingException, SQLException, ClassNotFoundException {
        Connection con = null;
        CallableStatement stm = null;
        try {
            con = DBUtils.createConnection();
            String sql = "{call AddCategory(?,?)}";
            stm = con.prepareCall(sql);
            stm.setString(1, category.getCategoryName().trim());
            stm.setString(2, category.getLink().trim());
            boolean result = stm.execute();
            if (result) {
                System.out.println("CategoryDAO ADDED successfully ----------------------------");
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
