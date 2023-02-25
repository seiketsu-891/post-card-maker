package com.louie.coding.service;

import com.louie.coding.constants.CanvasConstants;
import com.louie.coding.dao.CanvasDao;
import com.louie.coding.entity.Canvas;
import com.louie.coding.entity.Element;
import com.louie.coding.entity.Project;
import com.louie.coding.entity.ProjectFolder;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CanvasService {
    @Resource
    private CanvasDao canvasDao;

    public Project getRecentProject(Long userId) {
        return canvasDao.getRecentProjectByUserId(userId);
    }

    public void addProjectFolder(ProjectFolder projectFolder, Long userId) {
        projectFolder.setUserId(userId);
        projectFolder.setCreateTime(new Date());
        canvasDao.addProjectFolder(projectFolder);
    }

    public Project addProject(Project project, Long userId) {
        try {
            // 新增project条目
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = dateFormat.format(now);
            if (StringUtil.isNullOrEmpty(project.getName())) {
                project.setName(CanvasConstants.PROJECT_DEFAULT_NAME + dateString);
            }
            project.setCreateTime(now);
            project.setUpdateTime(now);
            project.setUserId(userId);
            canvasDao.addProject(project);

            Long projectId = project.getId();

            // 新增canvas条目
            Canvas canvas = project.getCanvas();
            canvas.setProjectId(projectId);
            canvas.setCreateTime(now);

            canvasDao.addCanvas(canvas);

            // todo 参数校验
            // 新增elements
            List<Element> elements = project.getElements();
            for (Element ele : elements) {
                ele.setProjectId(projectId);
                ele.setCreateTime(now);
            }
            canvasDao.addElements(elements);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return project;
    }
//
//    public Long  addOrUpdateElements(Element element, Long userId) {
//        Long projectId = element.getProjectId();
//        Project project = canvasDao.getProjectByIdAndUsername(projectId, userId);
//        if(project == null){
//            throw new BusinessException(BusinessExceptionCode.PROJECT_NOT_EXISTS);
//        }
//
//        if(element.getId() == null ){
//            elementDao.addElement(element);
//        }else{
//            elementDao.updateElement(element);
//        }
//        return element.getId();
//    }
}
