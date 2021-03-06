/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.dao;

import com.etest.connection.DBConnection;
import com.etest.connection.ErrorDBNotification;
import com.etest.model.Syllabus;
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
public class SyllabusDAO {
    
    public static List<Syllabus> getAllSyllabus(){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<Syllabus> syllabusList = new ArrayList<Syllabus>();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM enrolled_syllabus_view "
                    + "WHERE CurriculumStatus = 0");
            while(rs.next()){
                Syllabus s = new Syllabus();
                s.setSyllabusId(CommonUtilities.convertStringToInt(rs.getString("SyllabusID")));
                s.setSubject(rs.getString("CurrSubject"));
                s.setDescriptiveTitle(rs.getString("DescriptiveTitle"));
                s.setTopicNo(CommonUtilities.convertStringToInt(rs.getString("TopicNo")));
                s.setTopic(rs.getString("Topic"));
                s.setEstimatedTime(CommonUtilities.convertStringToFloat(rs.getString("EstimatedTime")));
                s.setCurriculumId(CommonUtilities.convertStringToInt(rs.getString("CurriculumID")));
                syllabusList.add(s);
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return syllabusList;
    }
    
    public static Syllabus getSyllabusById(int syllabusId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        Syllabus s = new Syllabus();
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM enrolled_syllabus_view "
                    + "WHERE SyllabusID = "+syllabusId+" ");
            while(rs.next()){
                s.setSubject(rs.getString("CurrSubject"));
                s.setDescriptiveTitle(rs.getString("DescriptiveTitle"));
                s.setTopicNo(CommonUtilities.convertStringToInt(rs.getString("TopicNo")));
                s.setTopic(rs.getString("Topic"));
                s.setEstimatedTime(CommonUtilities.convertStringToFloat(rs.getString("EstimatedTime")));
                s.setCurriculumId(CommonUtilities.convertStringToInt(rs.getString("CurriculumID")));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return s;
    }
    
    public static int getSyllabusIdByTopic(String topic){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int syllabusId = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT SyllabusID FROM enrolled_syllabus_view "
                    + "WHERE Topic = '"+topic+"' ");
            while(rs.next()){
                syllabusId = CommonUtilities.convertStringToInt(rs.getString("SyllabusID"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return syllabusId;
    }
    
    public static List<Syllabus> getSyllabusByCurriculum(int curriculumId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<Syllabus> syllabusList = new ArrayList<Syllabus>();
        
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM enrolled_syllabus_view "
                    + "WHERE CurriculumID = "+curriculumId+" ");
            while(rs.next()){
                Syllabus s = new Syllabus();
                s.setSubject(rs.getString("CurrSubject"));
                s.setDescriptiveTitle(rs.getString("DescriptiveTitle"));
                s.setTopicNo(CommonUtilities.convertStringToInt(rs.getString("TopicNo")));
                s.setTopic(rs.getString("Topic"));
                s.setEstimatedTime(CommonUtilities.convertStringToFloat(rs.getString("EstimatedTime")));
                s.setSyllabusId(CommonUtilities.convertStringToInt(rs.getString("SyllabusID")));
                syllabusList.add(s);
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return syllabusList;
    }
    
    public static boolean insertNewSyllabus(Syllabus s){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            pstmt = conn.prepareStatement("INSERT INTO syllabus SET "
                    + "CurriculumID = ?, "
                    + "TopicNo = ?, "
                    + "Topic = ?, "
                    + "EstimatedTime = ? ");            
            pstmt.setInt(1, s.getCurriculumId());
            pstmt.setInt(2, s.getTopicNo());
            pstmt.setString(3, s.getTopic());
            pstmt.setFloat(4, s.getEstimatedTime());
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement("INSERT INTO system_logs SET "
                    + "UserID = ?, "
                    + "EntryDateTime = now(), "
                    + "Activity = ? ");            
            pstmt.setInt(1, CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()));
            pstmt.setString(2, "New Syllabus was added with CurriculumID #"+s.getCurriculumId()+", Topic: "+s.getTopic());
            pstmt.executeUpdate();
            
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean updateSyllabus(Syllabus s){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("UPDATE syllabus SET "
                    + "CurriculumID = ?, "
                    + "TopicNo = ?, "
                    + "Topic = ?, "
                    + "EstimatedTime = ? "
                    + "WHERE SyllabusID = ? ");            
            pstmt.setInt(1, s.getCurriculumId());
            pstmt.setInt(2, s.getTopicNo());
            pstmt.setString(3, s.getTopic());
            pstmt.setFloat(4, s.getEstimatedTime());
            pstmt.setInt(5, s.getSyllabusId());
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement("INSERT INTO system_logs SET "
                    + "UserID = ?, "
                    + "EntryDateTime = now(), "
                    + "Activity = ? ");            
            pstmt.setInt(1, CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()));
            pstmt.setString(2, "Update Syllabus Topic with SyllabusID #"+s.getTopic());
            pstmt.executeUpdate();
            
            conn.commit();
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static boolean removeSyllabus(int syllabusId){
        Connection conn = DBConnection.connectToDB();
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("DELETE FROM syllabus "
                    + "WHERE SyllabusID = ? ");            
            pstmt.setInt(1, syllabusId);
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement("INSERT INTO system_logs SET "
                    + "UserID = ?, "
                    + "EntryDateTime = now(), "
                    + "Activity = ? ");            
            pstmt.setInt(1, CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()));
            pstmt.setString(2, "Removed Syllabus with SyllabusID #"+syllabusId);
            pstmt.executeUpdate();
            
            conn.commit();
            result = true;
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static double getEstimatedTime(int syllabusId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        double estimatedTime = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM enrolled_syllabus_view "
                    + "WHERE SyllabusID = "+syllabusId+" ");
            while(rs.next()){
                estimatedTime = CommonUtilities.convertStringToFloat(rs.getString("EstimatedTime"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(SyllabusDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return estimatedTime;
    }
    
    public static int getCurriculumIdBySyllabusId(int syllabusId){
        Connection conn = DBConnection.connectToDB();
        Statement stmt = null;
        ResultSet rs = null;
        int curriculumId = 0;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT CurriculumID FROM syllabus "
                    + "WHERE SyllabusID = "+syllabusId+" ");
            while(rs.next()){                
                curriculumId = CommonUtilities.convertStringToInt(rs.getString("CurriculumID"));
            }
        } catch (SQLException ex) {
            ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
            Logger.getLogger(CurriculumDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ErrorDBNotification.showLoggedErrorOnWindow(ex.toString());
                Logger.getLogger(CurriculumDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return curriculumId;
    }
}
