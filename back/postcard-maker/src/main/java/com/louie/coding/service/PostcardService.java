package com.louie.coding.service;

import com.louie.coding.constants.CanvasConstants;
import com.louie.coding.dao.PostcardContentDao;
import com.louie.coding.dao.PostcardDao;
import com.louie.coding.entity.Postcard;
import com.louie.coding.entity.PostcardContent;
import com.louie.coding.exception.BusinessException;
import com.louie.coding.exception.BusinessExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Service
@Transactional
public class PostcardService {
    final Integer MAX_HISTORY_VERSION_NUMBER = 5;

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


//    public void updateProjectInfo(Postcard postcard, Long userId) {
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

            String[] undoHistory = new String[MAX_HISTORY_VERSION_NUMBER];
            undoHistory[0] = "1";
            for (int i = 1; i < undoHistory.length; i++) {
                undoHistory[i] = "0";
            }

            String[] redoHistory = new String[MAX_HISTORY_VERSION_NUMBER];
            Arrays.fill(redoHistory, "0");

            postcard.setUndoHistory(Arrays.toString(undoHistory));
            postcard.setRedoHistory(Arrays.toString(redoHistory));
            postcardDao.addPostcard(postcard);

            postcardContent.setPostcardId(postcard.getId());
            postcardContent.setVersion(1L);

        } else {
            // update the existed postcard;
            Long maxVersion = postcardContentDao.getMaxVersionByPostcardId(postcardId);
            Long currVersion = maxVersion + 1;
            postcardDb.setCurrVersion(currVersion);
            postcardDb.setUpdateTime(now);
            String[] undoVersions = this.getVersionArrays(postcardDb.getUndoHistory());
            addArrayItem(undoVersions, String.valueOf(currVersion));
            postcardDb.setUndoHistory(Arrays.toString(undoVersions));
            postcardDao.updatePostcard(postcardDb);
            postcardContent.setVersion(currVersion);
        }

        postcardContent.setSnapshot("placeholder");
        postcardContent.setCreateTime(now);
        
        // todo snapshot
        postcardContentDao.addPostcardContent(postcardContent);
    }


    private String[] getVersionArrays(String strVersions) {
        String[] str = strVersions.replaceAll("\\[", "")
                .replaceAll("]", "")
                .split(",");
        for (int i = 0; i < str.length; i++) {
            str[i] = str[i].trim();
        }
        return str;
    }

    private void addArrayItem(String[] arr, String target) {
        boolean shiftForward = true;

        for (int i = 0; i < arr.length; i++) {
            if (Objects.equals(arr[i], "0")) {
                shiftForward = false;
                arr[i] = target;
                break;
            }
        }

        if (shiftForward) {
            for (int i = 0; i < arr.length - 1; i++) {
                arr[i] = arr[i + 1];
            }
            arr[arr.length - 1] = target;
        }
    }

    public Postcard getPostcard(Long userId, Long id) {
        Postcard result = null;
        Integer count = postcardDao.getPostcardCountByUserId(userId);
        if (count == 0) {
            return null;
        }
        if (id == null) {
            // get RecentPostcard
            result = postcardDao.getRecentUpdatedPostcardWithContentByUserId(userId);
        } else {
            result = postcardDao.getPostcardWithContentByUserIdAndId(id, userId);
        }
        String[] undoVersions = this.getVersionArrays(result.getUndoHistory());
        String[] redoVersions = this.getVersionArrays(result.getRedoHistory());

        for (String undoVersion : undoVersions) {
            if (!undoVersion.trim().equals("0")) {
                result.setUndoFlag(true);
                break;
            }
        }
        for (String redoVersion : redoVersions) {
            if (!redoVersion.trim().equals("0")) {
                result.setRedoFlag(true);
                break;
            }
        }

        result.setUndoHistory(null);
        result.setRedoHistory(null);

        return result;
    }

    // undo [2,3,4,5],
    // redo的话，设置为[9, 8, 7,6]这样的样子，把它想像成是竖起来的。

    // 当进行undo操作，把5设置为0，然后把5添加了redo最后一项
    // 当进行redo操作，把6设置为0，然后把6添加到undo最后一项。
    public Postcard performUndoRedo(Long userId, Long id, String operationType) {
        Postcard postcardResult;
        Postcard postcardDb = postcardDao.getByIdAndUserId(id, userId);
        if (postcardDb == null) {
            throw new BusinessException(BusinessExceptionCode.POSTCARD_NOT_EXISTS);
        }

        String[] undoVersions = this.getVersionArrays(postcardDb.getUndoHistory());
        String[] redoVersions = this.getVersionArrays(postcardDb.getRedoHistory());


        Long restoreVersionNo = null;
        Long currVersionNo = postcardDb.getCurrVersion();
        // 获取要恢复的版本号
        String[] targetArrs = operationType.equals(CanvasConstants.OPERATION_TYPE_UNDO) ? undoVersions : redoVersions;
        for (int i = MAX_HISTORY_VERSION_NUMBER - 1; i >= 0; i--) {
            if (Long.parseLong(targetArrs[i]) != 0) {
                restoreVersionNo = Long.valueOf(targetArrs[i]);
                targetArrs[i] = "0";
                break;
            }
        }

        if (restoreVersionNo == null) {
            throw new BusinessException(BusinessExceptionCode.ILLEGAL_ARGS);
        }

        if (operationType.equals(CanvasConstants.OPERATION_TYPE_UNDO)) { // undo
            addArrayItem(redoVersions, String.valueOf(currVersionNo));
        } else { // redo
            addArrayItem(undoVersions, String.valueOf(currVersionNo));
        }

        // 更新postcard信息
        postcardDb.setUndoHistory(Arrays.toString(undoVersions));
        postcardDb.setRedoHistory(Arrays.toString(redoVersions));
        postcardDb.setCurrVersion(restoreVersionNo);
        postcardDb.setUpdateTime(new Date());
        postcardDao.updatePostcard(postcardDb);

        // 获取版本
        postcardResult = this.fetchHistoryPostcard(id, userId, restoreVersionNo);

        for (String undoVersion : undoVersions) {
            if (!undoVersion.trim().equals("0")) {
                postcardResult.setUndoFlag(true);
            }
        }

        for (String redoVersion : redoVersions) {
            if (!redoVersion.trim().equals("0")) {
                postcardResult.setRedoFlag(true);
            }
        }

        return postcardResult;
    }

    private Postcard fetchHistoryPostcard(Long id, Long userId, Long version) {
        return postcardDao.getPostcardWithContentBySpecificVersion(id, userId, version);
    }
}
