/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.dao;

import com.etest.connection.DBConnection;
import com.etest.connection.ErrorDBNotification;
import com.etest.model.CellItem;
import com.etest.model.ItemKeys;
import com.etest.utilities.CommonUtilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jetdario
 */
public class CellItemDAO {
    
    public static List<CellItem> getAllCellItem(){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<CellItem> cellItemList = new ArrayList<CellItem>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM cell_items "
                    + "WHERE CellItemStatus = 0 ");
            while(rs.next()){
                CellItem ci = new CellItem();
                ci.setCellItemId(CommonUtilities.convertStringToInt(rs.getString("CellItemID")));
                ci.setCellCaseId(CommonUtilities.convertStringToInt(rs.getString("CellCaseID")));
                ci.setBloomsClassId(CommonUtilities.convertStringToInt(rs.getString("BloomsClassID")));
                ci.setItem(rs.getString("Item"));
                ci.setOptionA(rs.getString("OptionA"));
                ci.setOptionB(rs.getString("OptionB"));
                ci.setOptionC(rs.getString("OptionC"));
                ci.setOptionD(rs.getString("OptionD"));
                ci.setCellItemStatus(CommonUtilities.convertStringToInt(rs.getString("CellItemStatus")));
                ci.setDifficultIndex(CommonUtilities.convertStringToInt(rs.getString("DifficultIndex")));
                ci.setDiscriminationIndex(CommonUtilities.convertStringToInt(rs.getString("DiscriminationIndex")));
                ci.setUserId(CommonUtilities.convertStringToInt(rs.getString("AuthoredBy_UserID")));
                ci.setDateCreated(CommonUtilities.parsingDateWithTime(rs.getString("DateCreated")));
                cellItemList.add(ci);
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellItemDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return cellItemList;
    }
    
    public static List<CellItem> getCellItemByCase(int cellCaseId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        Statement innerStmt = null;
        ResultSet rs = null;
        ResultSet innerRs = null;
        List<CellItem> cellItemList = new ArrayList<CellItem>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM cell_items "
                    + "WHERE CellItemStatus = 0 "
                    + "AND CellCaseID = '"+cellCaseId+"' ");
            while(rs.next()){
                CellItem ci = new CellItem();
                ci.setCellItemId(CommonUtilities.convertStringToInt(rs.getString("CellItemID")));
                ci.setBloomsClassId(CommonUtilities.convertStringToInt(rs.getString("BloomsClassID")));
                ci.setItem(rs.getString("Item"));
                ci.setOptionA(rs.getString("OptionA"));
                ci.setOptionB(rs.getString("OptionB"));
                ci.setOptionC(rs.getString("OptionC"));
                ci.setOptionD(rs.getString("OptionD"));
                ci.setApproveItemStatus(CommonUtilities.convertStringToInt(rs.getString("ApproveStatus")));
                ci.setDifficultIndex(CommonUtilities.convertStringToInt(rs.getString("DifficultIndex")));
                ci.setDiscriminationIndex(CommonUtilities.convertStringToInt(rs.getString("DiscriminationIndex")));
                ci.setUserId(CommonUtilities.convertStringToInt(rs.getString("AuthoredBy_UserID")));
                ci.setDateCreated(CommonUtilities.parsingDateWithTime(rs.getString("DateCreated")));
                ci.setCellItemStatus(CommonUtilities.convertStringToInt(rs.getString("CellItemStatus")));                
                cellItemList.add(ci);
            }            
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellItemDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return cellItemList;
    }
    
    public static CellItem getCellItemById(int cellItemId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        Statement innerStmt = null;
        ResultSet rs = null;
        ResultSet innerRs = null;
        CellItem ci = new CellItem();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM cell_items "
                    + "WHERE CellItemStatus = 0 "
                    + "AND CellItemID = "+cellItemId+" ");
            while(rs.next()){                
                ci.setCellCaseId(CommonUtilities.convertStringToInt(rs.getString("CellCaseID")));
                ci.setBloomsClassId(CommonUtilities.convertStringToInt(rs.getString("BloomsClassID")));
                ci.setItem(rs.getString("Item"));
                ci.setOptionA(rs.getString("OptionA"));
                ci.setOptionB(rs.getString("OptionB"));
                ci.setOptionC(rs.getString("OptionC"));
                ci.setOptionD(rs.getString("OptionD"));
                ci.setApproveItemStatus(CommonUtilities.convertStringToInt(rs.getString("ApproveStatus")));
                ci.setDifficultIndex(CommonUtilities.convertStringToInt(rs.getString("DifficultIndex")));
                ci.setDiscriminationIndex(CommonUtilities.convertStringToInt(rs.getString("DiscriminationIndex")));
                ci.setUserId(CommonUtilities.convertStringToInt(rs.getString("AuthoredBy_UserID")));
                ci.setDateCreated(CommonUtilities.parsingDateWithTime(rs.getString("DateCreated")));                
                ci.setCellItemStatus(CommonUtilities.convertStringToInt(rs.getString("CellItemStatus")));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellItemDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return ci;
    }
    
    public static boolean insertNewCellItem(CellItem ci){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("INSERT INTO cell_items SET "
                    + "CellCaseID = ?, "
                    + "BloomsClassID = ?, "
                    + "Item = ?, "
                    + "OptionA = ?, "
                    + "OptionB = ?, "
                    + "OptionC = ?, "
                    + "OptionD = ?, "
                    + "AuthoredBy_UserID = ?, "
                    + "DateCreated = now() ");
            pstmt.setInt(1, ci.getCellCaseId());
            pstmt.setInt(2, ci.getBloomsClassId());
            pstmt.setString(3, ci.getItem());
            pstmt.setString(4, ci.getOptionA());
            pstmt.setString(5, ci.getOptionB());
            pstmt.setString(6, ci.getOptionC());
            pstmt.setString(7, ci.getOptionD());
            pstmt.setInt(8, ci.getUserId());
            pstmt.executeUpdate();
            
            int cellItemId = 0;            
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT last_insert_id() AS id FROM cell_items ");
            while(rs.next()){
                cellItemId = CommonUtilities.convertStringToInt(rs.getString("id"));
            }
            
            for (Map.Entry<String, String> entrySet : ci.getItemKeys().entrySet()) {
                Object key = entrySet.getKey();
                Object value = entrySet.getValue();
                
                pstmt = conn.prepareStatement("INSERT INTO item_keys "
                        + "SET CellItemID = ?, "
                        + "ItemKey = ?, "
                        + "Answer = ? ");
                pstmt.setInt(1, cellItemId);
                pstmt.setString(2, key.toString());
                pstmt.setString(3, value.toString());
                pstmt.executeUpdate();
            }
            
            conn.commit();
            result = true;
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex1.toString());
                Logger.getLogger(CellItemDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellItemDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean modifyCellItem(CellItem ci){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("UPDATE cell_items SET "
                    + "BloomsClassID = ?, "
                    + "Item = ? " 
                    + "WHERE CellItemID = ? ");
            pstmt.setInt(1, ci.getBloomsClassId());
            pstmt.setString(2, ci.getItem());
            pstmt.setInt(3, ci.getCellItemId());
            pstmt.executeUpdate();
                        
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellItemDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean approveCellItem(int cellItemId){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("UPDATE cell_items SET "
                    + "ApproveStatus = ? "
                    + "WHERE CellItemID = ? ");
            pstmt.setInt(1, 1);
            pstmt.setInt(2, cellItemId);
            pstmt.executeUpdate();
            
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellItemDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean removeCellItem(int cellItemId){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("UPDATE cell_items SET "
                    + "CellItemStatus = ? "
                    + "WHERE CellItemID = ? ");
            pstmt.setInt(1, 1);
            pstmt.setInt(2, cellItemId);
            pstmt.executeUpdate();
            
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellItemDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
        
}
