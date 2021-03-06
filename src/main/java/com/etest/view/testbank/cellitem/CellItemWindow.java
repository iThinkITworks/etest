/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.view.testbank.cellitem;

import com.etest.common.CommonButton;
import com.etest.common.CommonComboBox;
import com.etest.common.CommonTextField;
import com.etest.model.CellCase;
import com.etest.model.CellItem;
import com.etest.pdfviewer.MultipleChoiceHelpViewer;
import com.etest.service.CellItemService;
import com.etest.service.ItemKeyService;
import com.etest.serviceprovider.CellItemServiceImpl;
import com.etest.serviceprovider.ItemKeyServiceImpl;
import com.etest.utilities.CommonUtilities;
import com.vaadin.data.Property;
import com.vaadin.event.FieldEvents;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jetdario
 */
public class CellItemWindow extends Window {

    CellItemService cis = new CellItemServiceImpl();
    ItemKeyService k = new ItemKeyServiceImpl();
    
    ComboBox bloomsTaxonomy = CommonComboBox.getBloomsTaxonomy("Select a Blooms Class..");
    TextArea stem;
    TextField keyA;
    TextField optionA;
    TextField keyB;
    TextField optionB;
    TextField keyC;
    TextField optionC;
    TextField keyD;
    TextField optionD;
    
    boolean isOptionAKeyExist;
    boolean isOptionBKeyExist;
    boolean isOptionCKeyExist;
    boolean isOptionDKeyExist;
    
    private int cellCaseId;
    private int cellItemId;
    
    private boolean isStemChanged = false;
    private boolean isBloomsChanged = false;
    private boolean isEdit = false;
        
    public CellItemWindow(int cellCaseId, int cellItemId) {
        this.cellCaseId = cellCaseId;
        this.cellItemId = cellItemId;
        
        setCaption("CELL ITEM");
        setWidth("800px");
        setModal(true);
        center();
        
        if(cellItemId != 0){
            isEdit = true;
        }
        
        setContent(buildForms());
        getContent().setHeightUndefined();
    }
    
    FormLayout buildForms(){
        FormLayout form = new FormLayout();
        form.setWidth("100%");
        form.setMargin(true);
        form.setSpacing(true);
                
        HorizontalLayout hlayout = new HorizontalLayout();
        hlayout.setWidth("100%");
        
        Button helpBtn = new Button("HELP");
        helpBtn.setWidthUndefined();
        helpBtn.setIcon(FontAwesome.TASKS);
        helpBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        helpBtn.addStyleName(ValoTheme.BUTTON_SMALL);
        helpBtn.addClickListener((Button.ClickEvent event) -> {
            Window sub = new MultipleChoiceHelpViewer();
            if(sub.getParent() == null){
                UI.getCurrent().addWindow(sub);
            }
        });
        hlayout.addComponent(helpBtn);
        hlayout.setComponentAlignment(helpBtn, Alignment.MIDDLE_RIGHT);
        form.addComponent(hlayout);
        
        bloomsTaxonomy.setCaption("Blooms Class: ");
        bloomsTaxonomy.setWidth("30%");
        form.addComponent(bloomsTaxonomy);        
        
        stem = new TextArea("Stem: ");
        stem.setWidth("100%");
        stem.setRows(5);
        stem.setWordwrap(true);
        form.addComponent(stem);        
        
        HorizontalLayout h1 = new HorizontalLayout();
        h1.setCaption("Option A:");
        h1.setWidth("100%");
        h1.setSpacing(true);
        
        optionA = new CommonTextField("add option A", null);
        optionA.setWidth("500px");
        h1.addComponent(optionA);
        
        Button optionABtn = new CommonButton("OPTION A");
        optionABtn.setWidth("120px");
        optionABtn.addClickListener(updateOptionAndKeyListerner);         
        h1.addComponent(optionABtn);
        h1.setComponentAlignment(optionABtn, Alignment.MIDDLE_RIGHT);
        optionABtn.setVisible(isEdit());
        form.addComponent(h1); 
        
        HorizontalLayout h2 = new HorizontalLayout();
        h2.setCaption("Key A:");
        h2.setWidth("100%");
        h2.setSpacing(true);
        
        keyA = new CommonTextField("Enter a Key for Option A", null);
        keyA.setWidth("500px");
        h2.addComponent(keyA);
        
        Button keyABtn = new CommonButton("KEY A");
        keyABtn.setWidth("120px");
        keyABtn.addClickListener(updateOptionAndKeyListerner);
        h2.addComponent(keyABtn);
        h2.setComponentAlignment(keyABtn, Alignment.MIDDLE_RIGHT);
        keyABtn.setVisible(isEdit());
        form.addComponent(h2);               
        
        HorizontalLayout h3 = new HorizontalLayout();
        h3.setCaption("Option B:");
        h3.setWidth("100%");
        h3.setSpacing(true);
        
        optionB = new CommonTextField("add option B", null);
        optionB.setWidth("500px");
        h3.addComponent(optionB);
        
        Button optionBBtn = new CommonButton("OPTION B");
        optionBBtn.setWidth("120px");
        optionBBtn.addClickListener(updateOptionAndKeyListerner);
        h3.addComponent(optionBBtn);
        h3.setComponentAlignment(optionBBtn, Alignment.MIDDLE_RIGHT);
        optionBBtn.setVisible(isEdit());
        form.addComponent(h3);
        
        HorizontalLayout h4 = new HorizontalLayout();
        h4.setCaption("Key B:");
        h4.setWidth("100%");
        h4.setSpacing(true);
        
        keyB = new CommonTextField("Enter a Key for Option B", null);
        keyB.setWidth("500px");
        h4.addComponent(keyB);
        
        Button keyBBtn = new CommonButton("KEY B");
        keyBBtn.setWidth("120px");
        keyBBtn.addClickListener(updateOptionAndKeyListerner);
        h4.addComponent(keyBBtn);
        h4.setComponentAlignment(keyBBtn, Alignment.MIDDLE_RIGHT);
        keyBBtn.setVisible(isEdit());
        form.addComponent(h4);        
        
        HorizontalLayout h5 = new HorizontalLayout();
        h5.setCaption("Option C:");
        h5.setWidth("100%");
        h5.setSpacing(true);
        
        optionC = new CommonTextField("add option C", null);
        optionC.setWidth("500px");
        h5.addComponent(optionC);
        
        Button optionCBtn = new CommonButton("OPTION C");
        optionCBtn.setWidth("120px");
        optionCBtn.addClickListener(updateOptionAndKeyListerner);
        h5.addComponent(optionCBtn);
        h5.setComponentAlignment(optionCBtn, Alignment.TOP_RIGHT);
        optionCBtn.setVisible(isEdit());
        form.addComponent(h5);
        
        HorizontalLayout h6 = new HorizontalLayout();
        h6.setCaption("Key C:");
        h6.setWidth("100%");
        h6.setSpacing(true);
        
        keyC = new CommonTextField("Enter a Key for Option C", null);
        keyC.setWidth("500px");
        h6.addComponent(keyC);
        
        Button keyCBtn = new CommonButton("KEY C");
        keyCBtn.setWidth("120px");
        keyCBtn.addClickListener(updateOptionAndKeyListerner);
        h6.addComponent(keyCBtn);
        h6.setComponentAlignment(keyCBtn, Alignment.MIDDLE_RIGHT);
        keyCBtn.setVisible(isEdit());
        form.addComponent(h6);        
        
        HorizontalLayout h7 = new HorizontalLayout();
        h7.setCaption("Option D:");
        h7.setWidth("100%");
        h7.setSpacing(true);
        
        optionD = new CommonTextField("add option D", null);
        optionD.setWidth("500px");
        h7.addComponent(optionD);
        
        Button optionDBtn = new CommonButton("OPTION D");
        optionDBtn.setWidth("120px");
        optionDBtn.addClickListener(updateOptionAndKeyListerner);
        h7.addComponent(optionDBtn);
        h7.setComponentAlignment(optionDBtn, Alignment.MIDDLE_RIGHT);
        optionDBtn.setVisible(isEdit());
        form.addComponent(h7);  
                
        HorizontalLayout h8 = new HorizontalLayout();
        h8.setCaption("Key D:");
        h8.setWidth("100%");
        h8.setSpacing(true);
        
        keyD = new CommonTextField("Enter a Key for Option D", null);
        keyD.setWidth("500px");
        h8.addComponent(keyD);
        
        Button keyDBtn = new CommonButton("KEY D");
        keyDBtn.setWidth("120px");
        keyDBtn.addClickListener(updateOptionAndKeyListerner);
        h8.addComponent(keyDBtn);
        h8.setComponentAlignment(keyDBtn, Alignment.MIDDLE_RIGHT);
        keyDBtn.setVisible(isEdit());
        form.addComponent(h8); 
                
        HorizontalLayout h = new HorizontalLayout();
        h.setWidth("100%");
        
        Button save = new Button("SAVE");
        save.setWidth("200px");
        save.setIcon(FontAwesome.SAVE);
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        save.addStyleName(ValoTheme.BUTTON_SMALL);
        save.addClickListener(buttonClickListener);        
                
        Button remove = new Button("ARCHIVE/REMOVE ITEM?");
        remove.setWidth("200px");
        remove.setIcon(FontAwesome.ARCHIVE);
        remove.addStyleName(ValoTheme.BUTTON_PRIMARY);
        remove.addStyleName(ValoTheme.BUTTON_SMALL);
        remove.addClickListener(buttonClickListener);
        
        Button approve = new Button("APPROVE ITEM?");
        approve.setWidth("200px");
        approve.setIcon(FontAwesome.THUMBS_O_UP);
        approve.addStyleName(ValoTheme.BUTTON_PRIMARY);
        approve.addStyleName(ValoTheme.BUTTON_SMALL);
        approve.addClickListener(buttonClickListener);
        
        Button edit = new Button("UPDATE");
        edit.setWidth("200px");
        edit.setIcon(FontAwesome.SAVE);
        edit.addStyleName(ValoTheme.BUTTON_PRIMARY);
        edit.addStyleName(ValoTheme.BUTTON_SMALL);
        edit.addClickListener(buttonClickListener);
                
        if(getCellItemId() != 0){
            CellItem ci = cis.getCellItemById(getCellItemId());
            bloomsTaxonomy.setValue(ci.getBloomsClassId());
            bloomsTaxonomy.addValueChangeListener((Property.ValueChangeEvent event) -> {
                isBloomsChanged = true;
            });
            
            stem.setValue(ci.getItem());
            stem.addTextChangeListener((FieldEvents.TextChangeEvent event) -> {
                if(!stem.getValue().trim().equals(event.getText().trim())){
                    isStemChanged = true;
                }
            });
            
            /**
             * OPTION A
             */
            optionA.setValue(ci.getOptionA());
            isOptionAKeyExist = k.isKeyExist(getCellItemId(), ci.getOptionA());
            if(isOptionAKeyExist){
                keyA.setValue(k.getItemKey(getCellItemId(), ci.getOptionA()));                
            } 
            keyA.setData(k.getItemKeyId(getCellItemId(), ci.getOptionA()));
            
            /**
             * OPTION B
             */
            optionB.setValue(ci.getOptionB()); 
            isOptionBKeyExist = k.isKeyExist(getCellItemId(), ci.getOptionB());
            if(isOptionBKeyExist){
                keyB.setValue(k.getItemKey(getCellItemId(), ci.getOptionB()));                
            }
            keyB.setData(k.getItemKeyId(getCellItemId(), ci.getOptionB()));
            
            /**
             * OPTION C
             */
            optionC.setValue(ci.getOptionC());   
            isOptionCKeyExist = k.isKeyExist(getCellItemId(), ci.getOptionC());
            if(isOptionCKeyExist){    
                keyC.setValue(k.getItemKey(getCellItemId(), ci.getOptionC()));                
            }
            keyC.setData(k.getItemKeyId(getCellItemId(), ci.getOptionC()));
            
            /**
             * OPTION D
             */
            optionD.setValue(ci.getOptionD());
            isOptionDKeyExist = k.isKeyExist(getCellItemId(), ci.getOptionD());
            if(isOptionDKeyExist){
                keyD.setValue(k.getItemKey(getCellItemId(), ci.getOptionD()));                
            }
            keyD.setData(k.getItemKeyId(getCellItemId(), ci.getOptionD()));
                               
            h.addComponent(remove);
            h.setComponentAlignment(remove, Alignment.MIDDLE_RIGHT);
            
            h.addComponent(approve);
            h.setComponentAlignment(approve, Alignment.MIDDLE_RIGHT);
            
            h.addComponent(edit);
            h.setComponentAlignment(edit, Alignment.MIDDLE_RIGHT);            
            form.addComponent(h);
        } else {
            h.addComponent(save);
            h.setComponentAlignment(save, Alignment.MIDDLE_RIGHT);
            form.addComponent(h);
        }
        
        return form;
    }
    
    int getCellCaseId(){
        return cellCaseId;
    }
    
    int getCellItemId(){
        return cellItemId;
    }
        
    Button.ClickListener buttonClickListener = (Button.ClickEvent event) -> {
        if(bloomsTaxonomy.getValue() == null){
            Notification.show("Bloom Class Required!", Notification.Type.WARNING_MESSAGE);
            return;
        }
        
        if(stem.getValue() == null || stem.getValue().trim().isEmpty()){
            Notification.show("Stem is Required!", Notification.Type.WARNING_MESSAGE);
            return;
        }
        
        if(optionA.getValue() == null || optionA.getValue().isEmpty()){ requiredAllOptions(); return; }
        if(optionB.getValue() == null || optionB.getValue().isEmpty()){ requiredAllOptions(); return; }
        if(optionC.getValue() == null || optionC.getValue().isEmpty()){ requiredAllOptions(); return; }
        if(optionD.getValue() == null || optionD.getValue().isEmpty()){ requiredAllOptions(); return; }
                
        if(keyA.getValue().trim().isEmpty() && keyB.getValue().trim().isEmpty() && 
                keyC.getValue().trim().isEmpty() && keyD.getValue().trim().isEmpty()){
            Notification.show("Provide at least one Key", Notification.Type.ERROR_MESSAGE);
            return;
        }
        
        Map<String, String> keys = new HashMap<>();
        if(keyA.getValue().trim().isEmpty()){            
        } else {
            keys.put(CommonUtilities.escapeSingleQuote(keyA.getValue().trim()), 
                    CommonUtilities.escapeSingleQuote(optionA.getValue().trim()));
        }
                
        if(keyB.getValue().trim().isEmpty()){
        } else {
            keys.put(CommonUtilities.escapeSingleQuote(keyB.getValue().trim()), 
                    CommonUtilities.escapeSingleQuote(optionB.getValue().trim()));
        }
        
        if(keyC.getValue().trim().isEmpty()){
        } else {
            keys.put(CommonUtilities.escapeSingleQuote(keyC.getValue().trim()), 
                    CommonUtilities.escapeSingleQuote(optionC.getValue().trim()));
        }
        
        if(keyD.getValue().trim().isEmpty()){
        } else {
            keys.put(CommonUtilities.escapeSingleQuote(keyD.getValue().trim()), 
                    CommonUtilities.escapeSingleQuote(optionD.getValue().trim()));
        }
                
        CellItem ci = new CellItem();
        ci.setCellCaseId(getCellCaseId());
        ci.setBloomsClassId((int) bloomsTaxonomy.getValue());
        ci.setItem(CommonUtilities.escapeSingleQuote(stem.getValue().trim()));
        ci.setOptionA(CommonUtilities.escapeSingleQuote(optionA.getValue().trim()));
        ci.setOptionB(CommonUtilities.escapeSingleQuote(optionB.getValue().trim()));
        ci.setOptionC(CommonUtilities.escapeSingleQuote(optionC.getValue().trim()));
        ci.setOptionD(CommonUtilities.escapeSingleQuote(optionD.getValue().trim()));
        ci.setUserId(CommonUtilities.convertStringToInt(VaadinSession.getCurrent().getAttribute("userId").toString()));
        ci.setItemKeys(keys);        
        
        switch (event.getButton().getCaption()) {
            case "SAVE":
                {
                    boolean result = cis.insertNewCellItem(ci);
                    if(result){
                        close();
                    }       break;
                }
            case "ARCHIVE/REMOVE ITEM?":
                {
                    boolean result = cis.removeCellItem(getCellItemId());
                    if(result){
                        close();
            }       break;
                }
            case "APPROVE ITEM?":
            {
                boolean result = cis.approveCellItem(getCellItemId());
                if(result){
                    close();
            }       break;
                }
            default:
                ci.setCellItemId(getCellItemId());
                if(isStemChanged || isBloomsChanged){
                    Window sub = modifyCellItemWindow(ci);
                    if(sub.getParent() == null){
                        UI.getCurrent().addWindow(sub);
                    }
                } else {
                    Notification.show("No changes was made in Item or Bloom's class.. ", Notification.Type.TRAY_NOTIFICATION);
                    close();
                }   
                break;
        }
    };
            
    Button.ClickListener updateOptionAndKeyListerner = (Button.ClickEvent event) -> {
        switch(event.getButton().getCaption()){
            case "OPTION A": {
                if(optionA.getValue() == null || 
                        optionA.getValue().trim().isEmpty()){
                    Notification.show("Option A is Empty!", Notification.Type.WARNING_MESSAGE);
                } else {
                    boolean result = k.modifyItemOption(getCellItemId(), 
                            "OptionA", 
                            optionA.getValue().trim(), 
                            isOptionAKeyExist, 
                            (int) keyA.getData());
                    if(result){
                        Notification.show("Option A was modified", Notification.Type.TRAY_NOTIFICATION);
                    }
                }
                break;
            } 
            
            case "KEY A": {
                if(keyA.getValue() == null || 
                        keyA.getValue().trim().isEmpty()){
                    if(isOptionAKeyExist){
                        boolean result = k.removeItemKey((int) keyA.getData());
                        if(result){
                            Notification.show("Key A has been removed!", Notification.Type.TRAY_NOTIFICATION);
                        }
                    }
                } else {
                    Window sub = modifyKeyWindow((int) keyA.getData(), 
                            getCellItemId(), 
                            keyA.getValue().trim(), 
                            optionA.getValue().trim(), 
                            isOptionAKeyExist);
                    if(sub.getParent() == null){
                        UI.getCurrent().addWindow(sub);
                    }
                }
                break;
            }
            
            case "OPTION B": {
                if(optionB.getValue() == null || 
                        optionB.getValue().trim().isEmpty()){
                    Notification.show("Option B is Empty!", Notification.Type.WARNING_MESSAGE);
                } else {
                    boolean result = k.modifyItemOption(getCellItemId(), 
                            "OptionB", 
                            optionB.getValue().trim(), 
                            isOptionBKeyExist, 
                            (int) keyB.getData());
                    if(result){
                        Notification.show("Option B was modified", Notification.Type.TRAY_NOTIFICATION);
                    }
                }
                break;
            } 
            
            case "KEY B": {
                if(keyB.getValue() == null || 
                        keyB.getValue().trim().isEmpty()){
                    if(isOptionBKeyExist){
                        boolean result = k.removeItemKey((int) keyB.getData());
                        if(result){
                            Notification.show("Key B has been removed!", Notification.Type.TRAY_NOTIFICATION);
                        }
                    }
                } else {
                    Window sub = modifyKeyWindow((int) keyB.getData(), 
                            getCellItemId(), 
                            keyB.getValue().trim(), 
                            optionB.getValue().trim(), 
                            isOptionBKeyExist);
                    if(sub.getParent() == null){
                        UI.getCurrent().addWindow(sub);
                    }
                }
                break;
            }
            
            case "OPTION C": {
                if(optionC.getValue() == null || 
                        optionC.getValue().trim().isEmpty()){
                    Notification.show("Option C is Empty!", Notification.Type.WARNING_MESSAGE);
                } else {
                    boolean result = k.modifyItemOption(getCellItemId(), 
                            "OptionC", 
                            optionC.getValue().trim(), 
                            isOptionCKeyExist, 
                            (int) keyC.getData());
                    if(result){
                        Notification.show("Option C was modified", Notification.Type.TRAY_NOTIFICATION);
                    }
                }
                break;
            } 
            
            case "KEY C": {
                if(keyC.getValue() == null || 
                        keyC.getValue().trim().isEmpty()){
                    if(isOptionCKeyExist){
                        boolean result = k.removeItemKey((int) keyC.getData());
                        if(result){
                            Notification.show("Key C has been removed!", Notification.Type.TRAY_NOTIFICATION);
                        }
                    }
                } else {
                    Window sub = modifyKeyWindow((int) keyC.getData(), 
                            getCellItemId(), 
                            keyC.getValue().trim(), 
                            optionC.getValue().trim(), 
                            isOptionCKeyExist);
                    if(sub.getParent() == null){
                        UI.getCurrent().addWindow(sub);
                    }
                }
                break;
            }
            
            case "OPTION D": {
                if(optionD.getValue() == null || 
                        optionD.getValue().trim().isEmpty()){
                    Notification.show("Option D is Empty!", Notification.Type.WARNING_MESSAGE);
                } else {
                    boolean result = k.modifyItemOption(getCellItemId(), 
                            "OptionD", 
                            optionD.getValue().trim(), 
                            isOptionDKeyExist, 
                            (int) keyD.getData());
                    if(result){
                        Notification.show("Option D was modified", Notification.Type.TRAY_NOTIFICATION);
                    }
                }
                break;
            } 
            
            case "KEY D": {
                if(keyD.getValue() == null || 
                        keyD.getValue().trim().isEmpty()){
                    if(isOptionDKeyExist){
                        boolean result = k.removeItemKey((int) keyD.getData());
                        if(result){
                            Notification.show("Key D has been removed!", Notification.Type.TRAY_NOTIFICATION);
                        }
                    }
                } else {
                    Window sub = modifyKeyWindow((int) keyD.getData(), 
                            getCellItemId(), 
                            keyD.getValue().trim(), 
                            optionD.getValue().trim(), 
                            isOptionDKeyExist);
                    if(sub.getParent() == null){
                        UI.getCurrent().addWindow(sub);
                    }
                }
                break;
            }
            
            default: {
                Notification.show(event.getButton().getCaption());
                break;
            }
        }
    };
    
    void requiredAllOptions(){
        Notification.show("Fill up all  Options!", Notification.Type.WARNING_MESSAGE);
    }    
    
    boolean isEdit(){
        return isEdit;
    }
    
    Window modifyCellItemWindow(CellItem ci){
        VerticalLayout v = new VerticalLayout();
        v.setWidth("100%");
        v.setMargin(true);
        v.setSpacing(true);
        
        Window sub = new Window("MODIFY");
        sub.setWidth("400px");
        sub.setModal(true);
        sub.center();
        
        ComboBox actionDone = new ComboBox("Action: ");
        actionDone.setWidth("70%");
        actionDone.addStyleName(ValoTheme.COMBOBOX_SMALL);
        actionDone.setNullSelectionAllowed(false);
        actionDone.addItem("resolved");
        actionDone.addItem("clarified");
        actionDone.addItem("modified");
        actionDone.setImmediate(true);
        v.addComponent(actionDone);
        
        TextArea remarks = new TextArea("Remarks: ");
        remarks.setWidth("100%");
        remarks.setRows(3);
        v.addComponent(remarks);
        
        Button modify = new Button("UPDATE");
        modify.setWidth("70%");
        modify.setIcon(FontAwesome.EDIT);
        modify.addStyleName(ValoTheme.BUTTON_PRIMARY);
        modify.addStyleName(ValoTheme.BUTTON_SMALL);
        modify.addClickListener((Button.ClickEvent event) -> {    
            if(remarks.getValue() == null || remarks.getValue().trim().isEmpty()){
                Notification.show("Add remarks!", Notification.Type.WARNING_MESSAGE);
                return;
            }
            
            if(actionDone.getValue() == null){
                Notification.show("Add action!", Notification.Type.WARNING_MESSAGE);
                return;
            }
            
            ci.setRemarks(remarks.getValue().trim());
            ci.setActionDone(actionDone.getValue().toString());
            boolean result = cis.modifyCellItem(ci);
            if(result){  
                sub.close();
                close();
            }
        });
        v.addComponent(modify);
        
        sub.setContent(v);
        sub.getContent().setHeightUndefined();
        
        return sub;
    }

    Window modifyKeyWindow(int itemKeyId, 
            int cellItemId, 
            String keyValue, 
            String optionValue, 
            boolean isOptionKeyExist){
        VerticalLayout v = new VerticalLayout();
        v.setWidth("100%");
        v.setMargin(true);
        v.setSpacing(true);
        
        Window sub = new Window("MODIFY");
        sub.setWidth("400px");
        sub.setModal(true);
        sub.center();
        
        ComboBox actionDone = new ComboBox("Action: ");
        actionDone.setWidth("70%");
        actionDone.addStyleName(ValoTheme.COMBOBOX_SMALL);
        actionDone.setNullSelectionAllowed(false);
        actionDone.addItem("resolved");
        actionDone.addItem("clarified");
        actionDone.addItem("modified");
        actionDone.setImmediate(true);
        v.addComponent(actionDone);
        
        TextArea remarks = new TextArea("Remarks: ");
        remarks.setWidth("100%");
        remarks.setRows(3);
        v.addComponent(remarks);
        
        Button modify = new Button("UPDATE");
        modify.setWidth("70%");
        modify.setIcon(FontAwesome.EDIT);
        modify.addStyleName(ValoTheme.BUTTON_PRIMARY);
        modify.addStyleName(ValoTheme.BUTTON_SMALL);
        modify.addClickListener((Button.ClickEvent event) -> {    
            if(remarks.getValue() == null || remarks.getValue().trim().isEmpty()){
                Notification.show("Add remarks!", Notification.Type.WARNING_MESSAGE);
                return;
            }
            
            if(actionDone.getValue() == null){
                Notification.show("Add action!", Notification.Type.WARNING_MESSAGE);
                return;
            }
            
            boolean result = k.modifyItemKey(itemKeyId, 
                    cellItemId,                             
                    keyValue, 
                    optionValue, 
                    isOptionKeyExist, 
                    remarks.getValue().trim(), 
                    actionDone.getValue().toString());
            if(result){
                Notification.show("Key SUCCESSFULLY modified", Notification.Type.TRAY_NOTIFICATION);
                sub.close();
            }
        });
        v.addComponent(modify);
        
        sub.setContent(v);
        sub.getContent().setHeightUndefined();
        
        return sub;
    }
}
