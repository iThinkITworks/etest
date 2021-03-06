/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.service;

import com.etest.model.Curriculum;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jetdario
 */
public interface CurriculumService {
 
    public boolean insertNewCurriculum(Curriculum curriculum);
    
    public boolean updateCurriculum(Curriculum curriculum);
    
    public List<Curriculum> getAllCurriculum();
    
    public boolean removeCurriculum(int curriculumId);
    
    public Curriculum getCurriculumById(int curriculumId);
    
    public Map<Integer, String> getSubjectsFromCurriculum();
}
