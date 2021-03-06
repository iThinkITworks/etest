/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.systemadministration.SemestralTeam;

import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author jetdario
 */
public class SemestralTeamDataGrid extends Grid {

    public SemestralTeamDataGrid() {
        setSizeFull();
        setSelectionMode(SelectionMode.SINGLE);
        addStyleName(ValoTheme.TABLE_SMALL);
        setImmediate(true);
        
        addColumn("school year", String.class);
        addColumn("semester", String.class);
        addColumn("year level", String.class);
        addColumn("subject", String.class);
        addColumn("team leader", String.class);
        addColumn("members", Button.class);    
        
    }
    
}
