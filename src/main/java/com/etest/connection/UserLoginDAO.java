/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.connection;

import com.etest.global.LoginSession;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jetdario
 */
public class UserLoginDAO {
        
    VaadinSession session;
    
    public static boolean loginResult(String username, String password){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        boolean result = false;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM users "
                    + "WHERE Username_ = '"+username+"' "
                    + "AND Password_ = '"+password+"' ");
            while(rs.next()){
                VaadinSession.getCurrent().setAttribute("username", rs.getString("Username_"));
                result = true;
            }
        } catch (SQLException ex) {
            ErrorDBNotification.errorConnectoToDB();
            Logger.getLogger(UserLoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
}
