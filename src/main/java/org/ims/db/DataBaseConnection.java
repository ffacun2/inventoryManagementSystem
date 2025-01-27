package org.ims.db;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static final String user = "root";
    private static final String password = "";
    private static final String url = "jdbc:mysql://localhost:3306/inventorysystem";

    public static Connection getConnection(){
        Connection con = null;
        try{
//            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        }
//        catch(ClassNotFoundException e){
//            JOptionPane.showMessageDialog(null,"Driver not found");
//        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Connection failed: ");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error: "+e.getMessage());
        } finally {
            return con;
        }
    }
    

}
