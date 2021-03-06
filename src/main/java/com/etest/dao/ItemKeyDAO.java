/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.dao;

import com.etest.connection.DBConnection;
import com.etest.connection.ErrorDBNotification;
import com.etest.model.ItemKeys;
import com.etest.model.KeyLogUse;
import com.etest.utilities.CommonUtilities;
import com.vaadin.server.VaadinSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jetdario
 */
public class ItemKeyDAO {
    
    public static boolean addItemKey(int cellItemId, 
            String itemKey, 
            String answer){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("INSERT INTO item_keys SET "
                    + "CellItemID = ?, "
                    + "ItemKey = ?, "
                    + "Answer = ? ");
            pstmt.setInt(1, cellItemId);
            pstmt.setString(2, itemKey);
            pstmt.setString(3, answer);
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement("INSERT INTO system_logs SET "
                    + "UserID = ?, "
                    + "EntryDateTime = now(), "
                    + "Activity = ? ");            
            pstmt.setInt(1, CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()));
            pstmt.setString(2, "New Item Key was added with CellItemID #"+cellItemId+" Key value: "+itemKey);
            pstmt.executeUpdate();
            
            conn.commit();
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static List<String> getAllItemKey(int cellItemId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<String> keyList = new ArrayList<>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT ItemKey FROM item_keys "
                    + "WHERE CellItemID = "+cellItemId+" ");
            while(rs.next()){
                keyList.add(CommonUtilities.escapeSingleQuote(rs.getString("ItemKey")));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return keyList;
    }
    
    public static int getItemKeyIdFromKeyLogUse(int cellItemId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int itemKeyId = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT i.ItemKeyID AS ItemKeyID FROM key_log_use k "
                    + "INNER JOIN item_keys i ON k.ItemKeyID = i.ItemKeyID "
                    + "WHERE i.CellItemID = "+cellItemId+" AND UsedItemKey = 0 "
                    + "ORDER BY DateUsed ASC LIMIT 1 ");
            while(rs.next()){
                itemKeyId = CommonUtilities.convertStringToInt(rs.getString("ItemKeyID"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return itemKeyId;
    }
    
    public static List<ItemKeys> getItemKeysByCellItemId(int cellItemId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<ItemKeys> keyList = new ArrayList<>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM item_keys "
                    + "WHERE CellItemID = "+cellItemId+" ");
            while(rs.next()){
                ItemKeys k = new ItemKeys();
                k.setItemKeyId(CommonUtilities.convertStringToInt(rs.getString("ItemKeyID")));
                k.setItemKey(CommonUtilities.escapeSingleQuote(rs.getString("ItemKey")));
                k.setAnswer(CommonUtilities.escapeSingleQuote(rs.getString("Answer")));
                keyList.add(k);
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return keyList;
    }
    
    public static List<Integer> getItemKeyIdsByCellItemId(int cellItemId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<Integer> itemKeyIdList = new ArrayList<>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT ItemKeyID FROM item_keys "
                    + "WHERE CellItemID = "+cellItemId+" ");
            while(rs.next()){
                itemKeyIdList.add(CommonUtilities.convertStringToInt(rs.getString("ItemKeyID")));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return itemKeyIdList;
    }
    
    public static String getItemKey(int cellItemId, 
            String answer){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        String key = null;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT ItemKey FROM item_keys "
                    + "WHERE CellItemID = "+cellItemId+" "
                    + "AND Answer = '"+answer.replace("'", "\\'")+"' "
                    + "ORDER BY ItemKeyID DESC LIMIT 1");
            while(rs.next()){
                key = CommonUtilities.escapeSingleQuote(rs.getString("ItemKey"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return key;
    }
    
    public static boolean isKeyExist(int cellItemId, 
            String answer){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        boolean result = false;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS itemKey FROM item_keys "
                    + "WHERE CellItemID = "+cellItemId+" "
                    + "AND Answer = '"+answer.replace("'", "\\'")+"' ");
            while(rs.next()){
                if(rs.getString("itemKey").equals("1")){
                    result = true;
                }
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static int getItemKeyId(int cellItemId, 
            String answer){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int itemKeyId = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT ItemKeyID FROM item_keys "
                    + "WHERE CellItemID = "+cellItemId+" "
                    + "AND Answer = '"+answer.replace("'", "\\'")+"' ");
            while(rs.next()){
                itemKeyId = CommonUtilities.convertStringToInt(rs.getString("ItemKeyID"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return itemKeyId;
    }
    
    public static boolean modifyItemOption(int cellItemId, 
            String optionColumn, 
            String optionValue, 
            boolean isOptionKeyExist, 
            int itemKeyId){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("UPDATE cell_items SET "
                    + ""+optionColumn+" = '"+optionValue.replace("'", "\\'")+"' "
                    + "WHERE cellItemId = "+cellItemId+" ");
            pstmt.executeUpdate();
            
            if(isOptionKeyExist){
                pstmt = conn.prepareStatement("UPDATE item_keys SET "
                        + "Answer = '"+optionValue.replace("'", "\\'")+"' "
                        + "WHERE ItemKeyID = "+itemKeyId+" ");
                pstmt.executeUpdate();
            }
            
            pstmt = conn.prepareStatement("INSERT INTO system_logs SET "
                    + "UserID = ?, "
                    + "EntryDateTime = now(), "
                    + "Activity = ? ");            
            pstmt.setInt(1, CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()));
            pstmt.setString(2, "Modified item option with CellItemID #"+cellItemId+", "+optionColumn+": "+optionValue);
            pstmt.executeUpdate();
            
            conn.commit();
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean modifyItemKey(int itemKeyId, 
            int cellItemId, 
            String keyValue, 
            String answer, 
            boolean isOptionKeyExist, 
            String actionDone, 
            String remarks){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
                
        try {
            conn.setAutoCommit(false);
            if(isOptionKeyExist){
                pstmt = conn.prepareStatement("UPDATE item_keys SET "
                        + "ItemKey = '"+keyValue.replace("'", "\\'")+"' "
                        + "WHERE ItemKeyID = "+itemKeyId+" ");
                pstmt.executeUpdate();
            } else {
                pstmt = conn.prepareStatement("INSERT item_keys SET "
                        + "CellItemID = ?, "
                        + "ItemKey = ?, "
                        + "Answer = ? ");
                pstmt.setInt(1, cellItemId);
                pstmt.setString(2, keyValue);
                pstmt.setString(3, answer);
                pstmt.executeUpdate();
                
                remarks = "insert new item key";
                actionDone = "insert";
            }
            
            pstmt = conn.prepareStatement("INSERT INTO key_log SET "
                    + "ItemKeyID = ?, "
                    + "UserID = ?, "
                    + "Remarks = ?, "
                    + "DateRemarked = now(), "
                    + "ActionDone = ? ");
            pstmt.setInt(1, itemKeyId);
            pstmt.setInt(2, CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()));
            pstmt.setString(3, remarks);
            pstmt.setString(4, actionDone);
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement("INSERT INTO system_logs SET "
                    + "UserID = ?, "
                    + "EntryDateTime = now(), "
                    + "Activity = ? ");            
            pstmt.setInt(1, CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()));
            pstmt.setString(2, "Modified Item Key with CellItemID #"+cellItemId+", ItemKey: "+keyValue+", answer: "+answer);
            pstmt.executeUpdate();
            
            conn.commit();
            result = true;
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex1.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean removeItemKey(int itemKeyId){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("DELETE FROM item_keys "
                    + "WHERE ItemKeyID = "+itemKeyId+" ");
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement("INSERT INTO key_log SET "
                    + "ItemKeyID = ?, "
                    + "UserID = ?, "
                    + "Remarks = ?, "
                    + "DateRemarked = now(), "
                    + "ActionDone = ? ");
            pstmt.setInt(1, itemKeyId);
            pstmt.setInt(2, CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()));
            pstmt.setString(3, "delete item key");
            pstmt.setString(4, "delete");
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement("INSERT INTO system_logs SET "
                    + "UserID = ?, "
                    + "EntryDateTime = now(), "
                    + "Activity = ? ");            
            pstmt.setInt(1, CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()));
            pstmt.setString(2, "Removed Item Key with ItemKeyID #"+itemKeyId);
            pstmt.executeUpdate();
            
            conn.commit();
            result = true;
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean isAnswerCorrect(int cellItemId, 
            String key, 
            String answer){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        boolean result = false;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS result FROM item_keys "
                    + "WHERE CellItemID = "+cellItemId+" "
                    + "AND ItemKey = '"+key.replace("'", "\\'")+"' "
                    + "AND Answer = '"+answer.replace("'", "\\'")+"' "
                    + "ORDER BY ItemKeyID DESC LIMIT 1");
            while(rs.next()){
                if(rs.getString("result").equals("1")){
                    result = true;   
                }
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static String getAnswerByItemKeyId(int itemKeyId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        String answer = null;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT Answer FROM item_keys "
                    + "WHERE ItemKeyId = "+itemKeyId+" ");
            while(rs.next()){
                answer = CommonUtilities.escapeSingleQuote(rs.getString("Answer"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return answer;
    }

    public static boolean isItemKeyInKeyLogUse(int itemKeyId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        boolean result = false;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS itemKey FROM key_log_use "
                    + "WHERE ItemKeyID = "+itemKeyId+" AND UsedItemKey = 0 ");
            while(rs.next()){
                if(!rs.getString("itemKey").equals("0")){
                    result = true;
                }
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }

    public static String getItemKeyById(int itemKeyId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        String key = null;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT ItemKey FROM item_keys "
                    + "WHERE ItemKeyID = "+itemKeyId+" ");
            while(rs.next()){
                key = CommonUtilities.escapeSingleQuote(rs.getString("ItemKey"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return key;
    }
    
    public static KeyLogUse markUsedItemKey(int cellItemId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        int keyLogUseId = 0;
        boolean result = false;
        KeyLogUse k = new KeyLogUse();
        
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT k.KeyLogUseID AS KeyLogUseID FROM key_log_use k "
                    + "INNER JOIN item_keys i ON k.ItemKeyID = i.ItemKeyID "
                    + "WHERE i.CellItemID = "+cellItemId+" AND k.UsedItemKey = 0 "
                    + "ORDER BY DateUsed ASC LIMIT 1");
            while(rs.next()){
                keyLogUseId = CommonUtilities.convertStringToInt(rs.getString("KeyLogUseID"));                
            }
            
            pstmt = conn.prepareStatement("UPDATE key_log_use "
                                + "SET UsedItemKey = 1 "
                                + "WHERE KeyLogUseID = "+keyLogUseId+" ");
            pstmt.executeUpdate();
            
            k.setKeyLogUseId(keyLogUseId);
            k.setUsedItemKey(1);
            
            conn.commit();
            result = true;
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex1.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return k;
    }
    
    public static boolean revertMarkedUsedItemKey(int keyLogUseID){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("UPDATE key_log_use "
                    + "SET UsedItemKey = 0"
                    + "WHERE KeyLogUseID = "+keyLogUseID+" ");
            pstmt.executeUpdate();
            
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(ItemKeyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
}
