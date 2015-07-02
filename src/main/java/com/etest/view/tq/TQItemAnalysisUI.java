/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.tq;

import com.etest.model.ItemAnalysis;
import com.etest.model.TQCoverage;
import com.etest.service.CurriculumService;
import com.etest.service.TQCoverageService;
import com.etest.serviceprovider.CurriculumServiceImpl;
import com.etest.serviceprovider.TQCoverageServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.etest.view.itemanalysis.FileUploadWindow;
import com.etest.view.itemanalysis.ItemAnalysisDataGridProperties;
import com.etest.view.itemanalysis.ItemAnalysisDataTable;
import com.etest.view.itemanalysis.ItemAnalysisInterpretation;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import pl.exsio.plupload.PluploadError;
import pl.exsio.plupload.PluploadFile;
import pl.exsio.plupload.manager.PluploadManager;

/**
 *
 * @author jetdario
 */
public class TQItemAnalysisUI extends VerticalLayout {

    TQCoverageService tq = new TQCoverageServiceImpl();
    CurriculumService cs = new CurriculumServiceImpl();
    
    private int tqCoverageId = 0;
    List<String> upperGroupStudentNo = new ArrayList<>();
    List<String> lowerGroupStudentNo = new ArrayList<>();
    List<Integer> itemIds;
    Map<String, List<Character>> studentNoAndAnswer;
    private double groupTotalForProportion = 0;
    File excelFile;
    
    Table table = new ItemAnalysisDataTable();
    PluploadManager manager;
    
    public TQItemAnalysisUI() {
        setWidth("100%");        
        
        manager = new PluploadManager();
        manager.getUploader().setMaxFileSize("5mb");
        manager.getUploader().addFileUploadedListener((PluploadFile file) -> {
            Notification.show("I've just uploaded file: "
                    + file.getName());
            excelFile = new File(file.getUploadedFile().toString());
        });
        
        manager.getUploader().addErrorListener((PluploadError error) -> {
            Notification.show("There was an error: "
                 + error.getMessage() + " (" + error.getType() + ")",
                 Notification.Type.ERROR_MESSAGE);
        }); 
         
        populateDataTable();
        addComponent(table);
    }
        
    void populateDataTable(){
        table.removeAllItems();
        int i = 0;
        for(TQCoverage t : tq.getAllTQCoverage()){            
            Button analyze = new Button("analyze");
            analyze.setSizeFull();
            analyze.setData(t.getTqCoverageId());
            analyze.setIcon(FontAwesome.BULLSEYE);
            analyze.addStyleName(ValoTheme.BUTTON_LINK);
            analyze.addStyleName(ValoTheme.BUTTON_TINY);
            analyze.addStyleName(ValoTheme.BUTTON_QUIET);
            analyze.addStyleName("button-container");
            analyze.addClickListener((Button.ClickEvent event) -> {                
                tqCoverageId = (int) event.getButton().getData();
                Window sub = new FileUploadWindow(tqCoverageId);
                if(sub.getParent() == null){
                    UI.getCurrent().addWindow(sub);
                }
            });
                                    
            table.addItem(new Object[]{
                t.getExamTitle(), 
                cs.getCurriculumById(t.getCurriculumId()).getSubject(), 
                t.getDateCreated(), 
                t.getTotalHoursCoverage(), 
                t.getTotalItems(), 
                analyze
            }, i);
            i++;
        }
        table.setPageLength(table.size());                
    }
    
    int getTqCoverageId(){
        return tqCoverageId;
    }
    
    List<String> getUpperGroupStudentNo(){
        return upperGroupStudentNo;
    }
    
    List<String> getLowerGroupStudentNo(){
        return lowerGroupStudentNo;
    }
    
    Map<String, List<Character>> getStudentNoAndAnswer(){
        return studentNoAndAnswer;
    }
    
    double getGroupTotalForProportion(){
        return groupTotalForProportion;
    }
}
