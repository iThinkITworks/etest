/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.dao;

import com.etest.connection.DBConnection;
import com.etest.connection.ErrorDBNotification;
import com.etest.model.CellCase;
import com.etest.model.CellItem;
import com.etest.utilities.CommonUtilities;
import com.etest.view.notification.constants.EtestNotificationConstants;
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
public class CellCaseDAO {
    
    public static List<CellCase> getAllCellCase(){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<CellCase> cellCaseList = new ArrayList<>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM enrolled_cell_case_view "
                    + "WHERE CellCaseStatus = 0 ");
            while(rs.next()){
                CellCase c = new CellCase();
                c.setSubject(rs.getString("CurrSubject"));
                c.setTopic(CommonUtilities.escapeSingleQuote(rs.getString("Topic")));
                c.setCaseTopic(CommonUtilities.escapeSingleQuote(rs.getString("caseTopic")));
                c.setUsername_(rs.getString("Author"));
                c.setApprovalStatus(CommonUtilities.convertStringToInt(rs.getString("ApprovalStatus")));
                cellCaseList.add(c);
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return cellCaseList;
    }
    
    public static List<CellCase> getCellCaseByTopic(int syllabusId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<CellCase> cellCaseList = new ArrayList<>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM enrolled_cell_case_view "
                    + "WHERE SyllabusID = "+syllabusId+" "
                    + "AND CellCaseStatus = 0 ");
            while(rs.next()){
                CellCase c = new CellCase();
                c.setCellCaseId(CommonUtilities.convertStringToInt(rs.getString("CellCaseID")));
                c.setSubject(rs.getString("CurrSubject"));
                c.setTopic(CommonUtilities.escapeSingleQuote(rs.getString("Topic")));
                c.setCaseTopic(CommonUtilities.escapeSingleQuote(rs.getString("caseTopic")));
                c.setUsername_(rs.getString("Author"));
                c.setDateCreated(CommonUtilities.parsingDateWithTime(rs.getString("DateCreated")));
                c.setApprovalStatus(CommonUtilities.convertStringToInt(rs.getString("ApprovalStatus")));
                cellCaseList.add(c);
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return cellCaseList;
    }
    
    public static CellCase getCellCaseById(int cellCaseId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        CellCase c = new CellCase();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM enrolled_cell_case_view "
                    + "WHERE CellCaseID = "+cellCaseId+" "
                    + "AND CellCaseStatus = 0 ");
            while(rs.next()){                
                c.setSubject(rs.getString("CurrSubject"));
                c.setTopic(CommonUtilities.escapeSingleQuote(rs.getString("Topic")));
                c.setCaseTopic(CommonUtilities.escapeSingleQuote(rs.getString("caseTopic")));
                c.setUsername_(rs.getString("Author"));
                c.setCurriculumId(CommonUtilities.convertStringToInt(rs.getString("CurriculumID")));
                c.setSyllabusId(CommonUtilities.convertStringToInt(rs.getString("SyllabusID")));
                c.setApprovalStatus(CommonUtilities.convertStringToInt(rs.getString("ApprovalStatus")));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return c;
    }
    
    public static List<CellCase> getCellCaseByAuthor(int userId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<CellCase> cellCaseList = new ArrayList<>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM enrolled_cell_case_view "
                    + "WHERE UserID = "+userId+" "
                    + "AND CellCaseStatus = 0 ");
            while(rs.next()){
                CellCase c = new CellCase();
                c.setSubject(rs.getString("CurrSubject"));
                c.setTopic(CommonUtilities.escapeSingleQuote(rs.getString("Topic")));
                c.setCaseTopic(CommonUtilities.escapeSingleQuote(rs.getString("caseTopic")));
                c.setUsername_(rs.getString("Username_"));
                cellCaseList.add(c);
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return cellCaseList;
    }
    
    public static boolean insertNewCellCase(CellCase c){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("INSERT INTO cell_cases SET "
                    + "SyllabusID = ?, "
                    + "CaseTopic = ?, "
                    + "AuthoredBy_UserID = ?, "
                    + "DateCreated = now() ");
            pstmt.setInt(1, c.getSyllabusId());
            pstmt.setString(2, c.getCaseTopic());
            pstmt.setInt(3, c.getUserId());
            pstmt.executeUpdate();
            
            int cellCaseId = 0;            
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT last_insert_id() AS id FROM cell_cases ");
            while(rs.next()){
                cellCaseId = CommonUtilities.convertStringToInt(rs.getString("id"));
            }
                        
            pstmt = conn.prepareStatement("INSERT INTO case_log SET "
                    + "cellCaseID = ?, "
                    + "UserID = ?, "
                    + "Remarks = ?, "
                    + "DateRemarked = now(), "
                    + "ActionDone = ? ");
            pstmt.setInt(1, cellCaseId);
            pstmt.setInt(2, c.getUserId());
            pstmt.setString(3, "insert new case");
            pstmt.setString(4, "insert");
            pstmt.executeUpdate();
            
            conn.commit();
            
            conn.setAutoCommit(true);
            pstmt = conn.prepareStatement("INSERT INTO notifications SET "
                    + "UserID = ?, "
                    + "SenderID = ?, "
                    + "Notice = ?, "
                    + "Remarks = ?, "
                    + "NoteDate = now() ");            
            pstmt.setInt(1, TeamTeachDAO.getTeamLeaderIdByCurriculumId(SyllabusDAO.getCurriculumIdBySyllabusId(getSyllabusIdByCellCaseId(cellCaseId))));
            pstmt.setInt(2, c.getUserId());
            pstmt.setString(3, "CellCaseID #"+cellCaseId);
            pstmt.setString(4, EtestNotificationConstants.NEW_CASE_NOTIFICATION);
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement("INSERT INTO system_logs SET "
                    + "UserID = ?, "
                    + "EntryDateTime = now(), "
                    + "Activity = ? ");            
            pstmt.setInt(1, c.getUserId());
            pstmt.setString(2, "Created new cell case with CellCaseID #"+cellCaseId);
            pstmt.executeUpdate();
                        
            result = true;
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex1.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean modifyCellCase(CellCase c){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("UPDATE cell_cases SET "
                    + "SyllabusID = ?, "
                    + "CaseTopic = ?, "
                    + "ApprovalStatus = ? "
                    + "WHERE CellCaseID = ? ");
            pstmt.setInt(1, c.getSyllabusId());
            pstmt.setString(2, c.getCaseTopic());
            pstmt.setInt(3, 0);
            pstmt.setInt(4, c.getCellCaseId());
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement("INSERT INTO case_log SET "
                    + "cellCaseID = ?, "
                    + "UserID = ?, "
                    + "Remarks = ?, "
                    + "DateRemarked = now(), "
                    + "ActionDone = ? ");
            pstmt.setInt(1, c.getCellCaseId());
            pstmt.setInt(2, c.getUserId());
            pstmt.setString(3, c.getRemarks());
            pstmt.setString(4, c.getActionDone());
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement("INSERT INTO system_logs SET "
                    + "UserID = ?, "
                    + "EntryDateTime = now(), "
                    + "Activity = ? ");            
            pstmt.setInt(1, c.getUserId());
            pstmt.setString(2, "Modify cell case with CellCaseID #"+c.getCellCaseId());
            pstmt.executeUpdate();
            
            conn.commit();
            result = true;
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex1.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean approveCellCase(int cellCaseId){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("UPDATE cell_cases SET "
                    + "ApprovalStatus = ? "
                    + "WHERE CellCaseID = ? ");
            pstmt.setInt(1, 1);
            pstmt.setInt(2, cellCaseId);
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement("INSERT INTO case_log SET "
                    + "cellCaseID = ?, "
                    + "UserID = ?, "
                    + "Remarks = ?, "
                    + "DateRemarked = now(), "
                    + "ActionDone = ? ");
            pstmt.setInt(1, cellCaseId);
            pstmt.setInt(2, CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()));
            pstmt.setString(3, "approved case");
            pstmt.setString(4, "approved");
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement("INSERT INTO system_logs SET "
                    + "UserID = ?, "
                    + "EntryDateTime = now(), "
                    + "Activity = ? ");            
            pstmt.setInt(1, CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()));
            pstmt.setString(2, "Approved cell case with CellCaseID #"+cellCaseId);
            pstmt.executeUpdate();
            
            conn.commit();
            result = true;
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex1.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean removeCellCase(int cellCaseId){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("UPDATE cell_cases SET "
                    + "CellCaseStatus = ? "
                    + "WHERE CellCaseID = ? ");
            pstmt.setInt(1, 1);
            pstmt.setInt(2, cellCaseId);
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement("INSERT INTO case_log SET "
                    + "cellCaseID = ?, "
                    + "UserID = ?, "
                    + "Remarks = ?, "
                    + "DateRemarked = now(), "
                    + "ActionDone = ? ");
            pstmt.setInt(1, cellCaseId);
            pstmt.setInt(2, CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()));
            pstmt.setString(3, "delete case");
            pstmt.setString(4, "delete");
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement("UPDATE cell_items SET "
                    + "CellItemStatus = ? "
                    + "WHERE CellCaseID = ? ");
            pstmt.setInt(1, 1);
            pstmt.setInt(2, cellCaseId);
            pstmt.executeUpdate();
            
            for(CellItem cellItem : CellItemDAO.getCellItemByCase(cellCaseId)){
                pstmt = conn.prepareStatement("INSERT INTO item_log SET "
                        + "CellItemID = ?, "
                        + "UserID = ?, "
                        + "Remarks = ?, "
                        + "DateRemarked = now(), "
                        + "ActionDone = ? ");
                pstmt.setInt(1, cellItem.getCellItemId());
                pstmt.setInt(2, CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()));
                pstmt.setString(3, "delete cell item by case");
                pstmt.setString(4, "delete");
                pstmt.executeUpdate();
            }            
            
            pstmt = conn.prepareStatement("INSERT INTO system_logs SET "
                    + "UserID = ?, "
                    + "EntryDateTime = now(), "
                    + "Activity = ? ");            
            pstmt.setInt(1, CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()));
            pstmt.setString(2, "Removed cell case with CellCaseID #"+cellCaseId);
            pstmt.executeUpdate();
            
            conn.commit();
            result = true;
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex1.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean isCellCaseApproved(int cellCaseId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        boolean result = false;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS approved FROM "
                    + "enrolled_cell_case_view");
            while(rs.next()){
                if(rs.getString("approved").equals("1")){
                    result = true;
                }
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static CellCase getCellCaseIdByCellItemId(int cellItemId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        CellCase c = new CellCase();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT DISTINCT CellCaseID FROM generate_tq_coverage_view "
                    + "WHERE CellItemID = "+cellItemId+" "
                    + "AND CellItemStatus = 0 ");
            while(rs.next()){
                c.setCellCaseId(CommonUtilities.convertStringToInt(rs.getString("CellCaseID")));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return c;
    }

    public static int getCellCaseAuthorById(int cellCaseId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int authorId = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT CellCaseID FROM enrolled_cell_case_view "
                    + "WHERE CellCaseID = "+cellCaseId+" ");
            while(rs.next()){
                authorId = CommonUtilities.convertStringToInt(rs.getString("CellCaseID"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return authorId;
    }
    
    public static int getSyllabusIdByCellCaseId(int cellCaseId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int syllabusId = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT SyllabusID FROM cell_cases "
                    + "WHERE CellCaseID = "+cellCaseId+" ");
            while(rs.next()){
                syllabusId = CommonUtilities.convertStringToInt(rs.getString("SyllabusID"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CellCaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return syllabusId;
    }
}
