package com.louie.coding.service;

import com.louie.coding.constants.CanvasConstants;
import com.louie.coding.dao.CanvasDao;
import com.louie.coding.entity.Canvas;
import com.louie.coding.entity.Element;
import com.louie.coding.entity.PageResult;
import com.louie.coding.entity.Project;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class CanvasService {
    @Resource
    private CanvasDao canvasDao;

    public Project getRecentProject(Long userId) {
        return canvasDao.getRecentProjectByUserId(userId);
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

    //todo 是否返回project待前端测试后再决定
    public void addOrUpdateElements(Element element, Long userId) {
        // todo 验证（特别是projectid需要存在）
        Date now = new Date();
        if (element.getId() == null) {
            // 新建element
            element.setCreateTime(now);
            canvasDao.addElement(element);
        } else {
            element.setUpdateTime(now);
            System.out.println("update");
            canvasDao.updateElement(element);
        }
        Long projectId = element.getProjectId();
        canvasDao.updateProjectTimeByProjectIdAndUserId(now, projectId, userId);
    }

    public void updateCanvas(Canvas canvas, Long userId) {
        Date now = new Date();
        canvas.setUpdateTime(now);
        Long projectId = canvas.getProjectId();
        canvasDao.updateCanvas(canvas);
        canvasDao.updateProjectTimeByProjectIdAndUserId(now, projectId, userId);
    }

    public void updateProjectInfo(Project project, Long userId) {
        // todo 除了校验外，需要验证project id是否存在
        Date now = new Date();
        project.setUserId(userId);
        project.setUpdateTime(now);
        canvasDao.updateProjectInfoByProjectIdAndUserId(project);
    }

    public PageResult<Project> getProjectList(Integer pageNum, Integer pageSize, Long userId) {
        Integer start = (pageNum - 1) * pageSize;
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("size", pageSize);
        params.put("userId", userId);

        List<Project> list = new ArrayList<>();
        Integer count = canvasDao.getProjectCount(userId);
        if (count > 0) {
            list = canvasDao.getProjectsWithPagination(params);
        }

        PageResult<Project> res = new PageResult<>();
        res.setList(list);
        res.setTotal(count);
        return res;
    }

}
