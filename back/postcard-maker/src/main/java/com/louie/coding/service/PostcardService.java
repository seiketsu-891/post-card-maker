package com.louie.coding.service;

import com.louie.coding.constants.CanvasConstants;
import com.louie.coding.dao.PostcardContentDao;
import com.louie.coding.dao.PostcardDao;
import com.louie.coding.entity.Postcard;
import com.louie.coding.entity.PostcardContent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class PostcardService {
    @Resource
    private PostcardDao postcardDao;
    @Resource
    private PostcardContentDao postcardContentDao;
//    public Postcard addPostcard(Postcard postcard, Long userId) {
//        try {
//            // 新增project条目
//            Date now = new Date();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            String dateString = dateFormat.format(now);
//            if (StringUtil.isNullOrEmpty(postcard.getName())) {
//                postcard.setName(CanvasConstants.PROJECT_DEFAULT_NAME + dateString);
//            }
//            postcard.setCreateTime(now);
//            postcard.setUpdateTime(now);
//            postcard.setUserId(userId);
//            postcardDao.addProject(postcard);
//
//            Long projectId = postcard.getId();
//
//            // 新增canvas条目
//            Canvas canvas = postcard.getCanvas();
//            canvas.setProjectId(projectId);
//            canvas.setCreateTime(now);
//
//            postcardDao.addCanvas(canvas);
//
//            // todo 参数校验
//            // 新增elements
//            List<Element> elements = postcard.getElements();
//            for (Element ele : elements) {
//                ele.setProjectId(projectId);
//                ele.setCreateTime(now);
//            }
//            postcardDao.addElements(elements);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return postcard;
//    }

//    public void updateCanvas(Canvas canvas, Long userId) {
//        Date now = new Date();
//        canvas.setUpdateTime(now);
//        Long projectId = canvas.getProjectId();
//        postcardDao.updateCanvas(canvas);
//        postcardDao.updateProjectTimeByProjectIdAndUserId(now, projectId, userId);
//    }

//    public void updateProjectInfo(Postcard postcard, Long userId) {
//        // todo 除了校验外，需要验证project id是否存在
//        Date now = new Date();
//        postcard.setUserId(userId);
//        postcard.setUpdateTime(now);
//        postcardDao.updateProjectInfoByProjectIdAndUserId(postcard);
//    }

//    public PageResult<Postcard> getPostcardList(Integer pageNum, Integer pageSize, Long userId) {
//        Integer start = (pageNum - 1) * pageSize;
//        Map<String, Object> params = new HashMap<>();
//        params.put("start", start);
//        params.put("size", pageSize);
//        params.put("userId", userId);
//
//        List<Postcard> list = new ArrayList<>();
//        Integer count = postcardDao.getPostcardCount(userId);
//        if (count > 0) {
//            list = postcardDao.getPostCardsWithPagination(params);
//        }
//
//        PageResult<Postcard> res = new PageResult<>();
//        res.setList(list);
//        res.setTotal(count);
//        return res;
//    }

    public void addOrUpdatePostcard(Long userId, PostcardContent postcardContent) {
        Long postcardId = postcardContent.getPostcardId();
        Date now = new Date();
        Postcard postcardDb = null;

        if (postcardId != null) {
            postcardDb = postcardDao.getByIdAndUserId(postcardId, userId);

        }

        if (postcardId == null || postcardDao == null) {
            // create a new postcard entry
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = dateFormat.format(now);
            Postcard postcard = new Postcard();
            postcard.setName(CanvasConstants.PROJECT_DEFAULT_NAME + dateString);
            postcard.setCreateTime(now);
            postcard.setUpdateTime(now);
            postcard.setUserId(userId);
            postcard.setCurrVersion(1L);
            postcardDao.addPostcard(postcard);
            postcardContent.setPostcardId(postcard.getId());
            postcardContent.setVersion(1L);
        } else {
            // update the existed postcard;
            Long maxVersion = postcardContentDao.getMaxVersionByPostcardId(postcardId);
            Long currVersion = maxVersion + 1;
            postcardDb.setCurrVersion(currVersion);
            postcardDb.setUpdateTime(now);
            postcardDao.updatePostcard(postcardDb);
            postcardContent.setVersion(currVersion);
        }

        postcardContent.setSnapshot("placeholder");
        postcardContent.setCreateTime(now);
        // todo snapshot
        postcardContentDao.addPostcardContent(postcardContent);
    }
    
    public Postcard getPostcard(Long userId, Long id) {
        Integer count = postcardDao.getPostcardCountByUserId(userId);
        if (count == 0) {
            return null;
        }
        if (id == null) {
            // get RecentPostcard
            return postcardDao.getRecentUpdatedPostcardWithContentByUserId(userId);
        }
        // get postcard by postcardId
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("id", id);
        return postcardDao.getPostcardWithContentByUserIdAndId(params);
    }
}
