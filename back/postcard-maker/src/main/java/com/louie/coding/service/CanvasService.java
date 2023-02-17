package com.louie.coding.service;

import com.louie.coding.dao.CanvasDao;
import com.louie.coding.entity.Canvas;
import com.louie.coding.entity.Element;
import com.louie.coding.entity.Project;
import com.louie.coding.entity.ProjectFolder;
import com.louie.coding.exception.BusinessException;
import com.louie.coding.exception.BusinessExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class CanvasService {
    @Resource
    private CanvasDao canvasDao;

    public Project getRecentProject(Long userId) {
        return canvasDao.getRecentProjectByUserId(userId);
    }

    public Map<String, Object> addProject(Project project, Long userId) {
        if(project.getCanvas() == null && (project.getElements() == null || project.getElements().size() ==0 ){
            throw new BusinessException(BusinessExceptionCode.ILLEGAL_ARGS);
        }
        Map<String, Object> ids = new HashMap<>();

        Date now = new Date();
        project.setUserId(userId);
        project.setCreateTime(now);
        canvasDao.addProject(project);

        Long projectId = project.getId();
        ids.put("projectId", projectId);
        // canvas
        if (project.getCanvas() != null) {
           Canvas canvas = project.getCanvas();
           canvas.setCreateTime(now);
           canvas.setProjectId(projectId);
           canvasDao.addCavas(canvas);
           ids.put("canvasId", canvas.getId());
        }else{
            Element element = project.getElements().get(0);
            element.setCreateTime(now);
            element.setProjectId(projectId);
            canvasDao.addElement(element);
            ids.put("eleId",element.getId());
        }
        return ids;
    }

    public void addProjectFolder(ProjectFolder projectFolder, Long userId) {
        projectFolder.setUserId(userId);
        projectFolder.setCreateTime(new Date());
        canvasDao.addProjectFolder(projectFolder);
    }

    public Long  addOrUpdateElements(Element element, Long userId) {
        Long projectId = element.getProjectId();
        Project project = canvasDao.getProjectByIdAndUsername(projectId, userId);
        if(project == null){
            throw new BusinessException(BusinessExceptionCode.PROJECT_NOT_EXISTS);
        }

        if(element.getId() == null ){
            elementDao.addElement(element);
        }else{
            elementDao.updateElement(element);
        }
        return element.getId();
    }
}
