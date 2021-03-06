/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.pdfviewer;

import com.etest.pdfgenerator.ItemAnalysisOfSubjectReportPDF;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 * @author jetdario
 */
public class ItemAnalysisOfSubjectReportViewer extends Window {

    private int tqCoverageId;
    
    public ItemAnalysisOfSubjectReportViewer(int tqCoverageId) {
        this.tqCoverageId = tqCoverageId;
        
        setWidth("900px");
        setHeight("600px");
        center();
                
        StreamResource resource = new StreamResource(new ItemAnalysisOfSubjectReportPDF(getTQCoverageId()), "ItemAnalysis.pdf");
        resource.setMIMEType("application/pdf");       

        VerticalLayout v = new VerticalLayout();
        v.setSizeFull();
        Embedded e = new Embedded();
        e.setSource(resource);
        e.setSizeFull();
        e.setType(Embedded.TYPE_BROWSER);
        v.addComponent(e);
        
        setContent(v);
    }
    
    int getTQCoverageId(){
        return tqCoverageId;
    }
}
