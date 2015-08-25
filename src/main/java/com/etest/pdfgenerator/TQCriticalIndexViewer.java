/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.pdfgenerator;

import com.vaadin.server.StreamResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 * @author jetdario
 */
public class TQCriticalIndexViewer extends Window {

    private int tqCoverageId;
    
    public TQCriticalIndexViewer(int tqCoverageId) {
        this.tqCoverageId = tqCoverageId;
        setWidth("900px");
        setHeight("600px");
        center();
                
        StreamResource resource = new StreamResource(new TQCriticalIndexValues(getTQCoverageId()), "TQCriticalIndexValues.pdf");
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
