/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.serviceprovider;

import com.etest.dao.TQCoverageDAO;
import com.etest.service.TQCoverageService;
import com.etest.utilities.CommonUtilities;
import com.vaadin.data.Item;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author jetdario
 */
public class TQCoverageServiceImpl implements TQCoverageService {

    @Override
    public double calculateTotalHourSpent(Grid grid) {
        double avg = 0;
        
        Collection c = grid.getContainerDataSource().getItemIds();
        Iterator iterator = c.iterator();
        while(iterator.hasNext()){
            Item item = grid.getContainerDataSource().getItem(iterator.next());
            if(item.getItemProperty("Hrs Spent").getValue() != null){
                avg = avg + CommonUtilities.convertStringToDouble(item.getItemProperty("Hrs Spent").getValue().toString());
            }
        }
                
        return avg;
    }

    @Override
    public void calculateProportion(Grid grid) {
        Collection c = grid.getContainerDataSource().getItemIds();
        Iterator iterator = c.iterator();
        while(iterator.hasNext()){
            Item item = grid.getContainerDataSource().getItem(iterator.next());
            
            if(item.getItemProperty("Hrs Spent").getValue() != null){
                item.getItemProperty("Proportion(%)").setValue(
                        CommonUtilities.roundOffToWholeNumber((CommonUtilities.convertStringToDouble(
                                item.getItemProperty("Hrs Spent").getValue().toString()) / calculateTotalHourSpent(grid))*100)                    
                );
            }
        }  
    }

    @Override
    public void calculateMaxItems(Grid grid, TextField totalItems) {
        Collection c = grid.getContainerDataSource().getItemIds();
        Iterator iterator = c.iterator();
        while(iterator.hasNext()){
            Item item = grid.getContainerDataSource().getItem(iterator.next());
            
            if(item.getItemProperty("Proportion(%)").getValue() != null){
                item.getItemProperty("Max Items").setValue(
                        CommonUtilities.roundOffToWholeNumber((CommonUtilities.convertStringToDouble(
                                item.getItemProperty("Proportion(%)").getValue().toString()) / 100) * 
                                CommonUtilities.convertStringToDouble(totalItems.getValue().trim())
                        )                    
                );
            }
        }
    }

    @Override
    public double calculateTotalProportion(Grid grid) {
        double proportion = 0;
        
        Collection c = grid.getContainerDataSource().getItemIds();
        Iterator iterator = c.iterator();
        while(iterator.hasNext()){
            Item item = grid.getContainerDataSource().getItem(iterator.next());
            
            if(item.getItemProperty("Proportion(%)").getValue() != null){
                proportion = proportion + CommonUtilities.convertStringToDouble(item.getItemProperty("Proportion(%)").getValue().toString());
            }
        }
                
        return (proportion > 100 ? 100 : proportion);
    }

    @Override
    public double calculateTotalMaxItems(Grid grid) {
        double maxItems = 0;
        
        Collection c = grid.getContainerDataSource().getItemIds();
        Iterator iterator = c.iterator();
        while(iterator.hasNext()){
            Item item = grid.getContainerDataSource().getItem(iterator.next());
            
            if(item.getItemProperty("Max Items").getValue() != null){
                maxItems = maxItems + CommonUtilities.convertStringToDouble(item.getItemProperty("Max Items").getValue().toString());        
            }
        }    
        return maxItems;
    }

    @Override
    public int getBloomsClassId(String bloomsClass) {
        return TQCoverageDAO.getBloomsClassId(bloomsClass);
    }    

    @Override
    public int getTotalForBloomsClassColumn(Grid grid, String propertyId) {
        int total = 0;
        
        Collection c = grid.getContainerDataSource().getItemIds();
        Iterator iterator = c.iterator();
        while(iterator.hasNext()){
            Item item = grid.getContainerDataSource().getItem(iterator.next());
            
            if(item.getItemProperty(propertyId).getValue() != null){
                total = total + CommonUtilities.convertStringToInt(item.getItemProperty(propertyId).getValue().toString());
            }
        }
                
        return total;
    }

    @Override
    public boolean isValueInTBNotZero(Item item, 
            String propertyId) {
        boolean result = false;
        if(item.getItemProperty(propertyId).getValue() != null){
            if(CommonUtilities.convertStringToInt(item.getItemProperty(propertyId).getValue().toString()) != 0){
                result = true;
            }
        }
        
        return result;
    }

    @Override
    public boolean isGreaterThanInTB(Item item, String propertyIdInTB, String inputValue) {
        boolean result = false;
        if(CommonUtilities.convertStringToInt(inputValue) > 
                CommonUtilities.convertStringToInt(item.getItemProperty(propertyIdInTB).getValue().toString())){
            result = true;
        }
        
        return result;
    }

    @Override
    public int calculateTotalPickItems(Grid grid, String propertyId) {
        int total = 0;
        
        Collection c = grid.getContainerDataSource().getItemIds();
        Iterator iterator = c.iterator();
        while(iterator.hasNext()){
            Item item = grid.getContainerDataSource().getItem(iterator.next());
            if(item.getItemProperty(propertyId).getValue() != null){
                total = total + CommonUtilities.convertStringToInt(item.getItemProperty(propertyId).getValue().toString());
            }
        }
                
        return total;
    }

    @Override
    public int calculateTotalPickItemsPerTopic(Grid grid, Object itemId) {
        String propertyId = null;
        int runningTotal = 0;
        
        Item item = grid.getContainerDataSource().getItem(itemId);
        Collection c = item.getItemPropertyIds();
        Iterator iterator = c.iterator();
        while(iterator.hasNext()){
            propertyId = iterator.next().toString();
            if(propertyId.contains("Pick")){
                if(item.getItemProperty(propertyId).getValue() != null){
                    runningTotal = runningTotal + (int)item.getItemProperty(propertyId).getValue();
                }       
            }
        }
        return runningTotal;
    }

    @Override
    public void revertAllInputItemsToZero(Grid grid, Object itemId) {
        String propertyId = null;
        int runningTotal = 0;
        
        Item item = grid.getContainerDataSource().getItem(itemId);
        Collection c = item.getItemPropertyIds();
        Iterator iterator = c.iterator();
        while(iterator.hasNext()){
            propertyId = iterator.next().toString();
            if(propertyId.contains("Pick")){
                if(item.getItemProperty(propertyId).getValue() != null){
                    item.getItemProperty(propertyId).setValue(0);
                }       
            }
        }
        item.getItemProperty("Running Total").setValue(0);
    }

    @Override
    public int calculateRunningTotal(Grid grid) {
        int runningTotal = 0;
        
        Collection c = grid.getContainerDataSource().getItemIds();
        Iterator iterator = c.iterator();
        while(iterator.hasNext()){
            Item item = grid.getContainerDataSource().getItem(iterator.next());
            if(item.getItemProperty("Running Total").getValue() != null){
                runningTotal = runningTotal + CommonUtilities.convertStringToInt(item.getItemProperty("Running Total").getValue().toString());
            }
        }
                
        return runningTotal;
    }
}
