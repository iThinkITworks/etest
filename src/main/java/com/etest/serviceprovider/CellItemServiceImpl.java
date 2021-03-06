/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.serviceprovider;

import com.etest.dao.CellItemDAO;
import com.etest.model.CellItem;
import com.etest.service.CellItemService;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jetdario
 */
public class CellItemServiceImpl implements CellItemService {

    @Override
    public List<CellItem> getAllCellItem() {
        return CellItemDAO.getAllCellItem();
    }

    @Override
    public List<CellItem> getCellItemByCase(int cellCaseId) {
        return CellItemDAO.getCellItemByCase(cellCaseId);
    }

    @Override
    public CellItem getCellItemById(int cellItemId) {
        return CellItemDAO.getCellItemById(cellItemId);
    }

    @Override
    public boolean insertNewCellItem(CellItem ci) {
        return CellItemDAO.insertNewCellItem(ci);
    }

    @Override
    public boolean modifyCellItem(CellItem ci) {
        return CellItemDAO.modifyCellItem(ci);
    }

    @Override
    public boolean approveCellItem(int cellItemId) {
        return CellItemDAO.approveCellItem(cellItemId);
    }

    @Override
    public boolean removeCellItem(int cellItemId) {
        return CellItemDAO.removeCellItem(cellItemId);
    }    

    @Override
    public int getTotalCellItemByTopic(int bloomsClassId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getTotalUnanalyzeItem(int syllabusId, 
            int bloomsClassId) {
        return CellItemDAO.getTotalUnanalyzeItem(syllabusId, 
                bloomsClassId);
    }

    @Override
    public int getTotalAnalyzeItem(int syllabusId, 
            int bloomsClassId) {
        return CellItemDAO.getTotalAnalyzeItem(syllabusId, 
                bloomsClassId);
    }

    @Override
    public Map<String, Character> getOptionAnswer(int cellItemId) {
        return CellItemDAO.getOptionAnswer(cellItemId);
    }

    @Override
    public List<CellItem> getItemAnalysisResult(int tqCoverageId) {
        return CellItemDAO.getItemAnalysisResult(tqCoverageId);
    }

    @Override
    public int getCellItemAuthorById(int cellItemId) {
        return CellItemDAO.getCellItemAuthorById(cellItemId);
    }
}
