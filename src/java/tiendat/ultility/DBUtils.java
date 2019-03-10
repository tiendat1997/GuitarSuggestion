/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.ultility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author DATTTSE62330
 */
public class DBUtils {
     public static Connection makeConnection() throws NamingException, SQLException{
        
        Context context = new InitialContext();
        Context tomcatCtx = (Context) context.lookup("java:comp/env");
        DataSource ds = (DataSource) tomcatCtx.lookup("GuitarSuggestion");
        Connection con = ds.getConnection();
        
        return con;
    }
     public static Connection createConnection() throws NamingException, SQLException, ClassNotFoundException{
        
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=GuitarSuggestion";
        Connection con = DriverManager.getConnection(url, "sa", "tiendat");
        return con; 
    }
}
