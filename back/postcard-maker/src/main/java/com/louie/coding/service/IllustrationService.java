package com.louie.coding.service;

import com.louie.coding.constants.RedisConstants;
import com.louie.coding.constants.TaskConstants;
import com.louie.coding.controller.support.UserSupport;
import com.louie.coding.dao.IllustrationDao;
import com.louie.coding.entity.Illustration;
import com.louie.coding.entity.PageResult;
import com.louie.coding.entity.UserIllustration;
import com.louie.coding.entity.entityResp.IllustrationResp;
import com.louie.coding.util.CopyUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

@Service
public class IllustrationService {
    @Resource
    private IllustrationDao illustrationDao;
    @Resource
    private FileService fileService;
    @Resource
    private TaskService taskService;
    @Resource
    private RedisService redisService;
    @Resource
    private UserSupport userSupport;

    public PageResult<IllustrationResp> getIllustrations(Integer pageNum, Integer pageSize, Long albumId, String keyword) {
        Integer start = (pageNum - 1) * pageSize;
        Map<String, Object> params = new HashMap<>();

        params.put("start", start);
        params.put("size", pageSize);
        params.put("albumId", albumId);
        params.put("keyword", keyword);

        List<Illustration> list = new ArrayList<>();
        Integer count = illustrationDao.getCount(params);
        if (count > 0) {
            list = illustrationDao.getIllusWithPagination(params);
        }

        List<IllustrationResp> listResp = CopyUtil.copyList(list, IllustrationResp.class);

        // redis VIP info
        for (IllustrationResp illuResp : listResp) {
            String key = RedisConstants.keyPrefixAlbum + albumId + RedisConstants.keyPrefixPic + illuResp.getId();
            Boolean storedValue = redisService.getBooleanValue(key);
            if (!Objects.equals(storedValue, illuResp.getIsVip())) {
                redisService.setBooleanValue(key, illuResp.getIsVip());
            }
        }

        PageResult<IllustrationResp> res = new PageResult<>();
        res.setList(listResp);
        res.setTotal(count);

        return res;
    }

    public PageResult<UserIllustration> getUserPics(Integer pageNum, Integer pageSize, Long userId) {
        Integer start = (pageNum - 1) * pageSize;
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("size", pageSize);
        params.put("userId", userId);

        List<UserIllustration> list = new ArrayList<>();
        Integer count = illustrationDao.getUserPicCount(userId);
        if (count > 0) {
            list = illustrationDao.getUserPicsWithPagination(params);
        }
        PageResult<UserIllustration> res = new PageResult<>();
        res.setList(list);
        res.setTotal(count);
        return res;
    }

    public void uploadUserIllustration(MultipartFile file, Long userId) {
        String directory = userId + "/";
        String url = fileService.uploadFile(file, directory);
        UserIllustration ui = new UserIllustration();
        ui.setUrl(url);
        ui.setUserId(userId);
        ui.setCreateTime(new Date());
        illustrationDao.addUserIllustration(ui);

        // 添加任务完成情况
        taskService.completeTask(userId, TaskConstants.TASK_ID_UPLOAD_IMG);
    }

    private Boolean ifVipOnly(Long albumId, Long picId) {
        String key = RedisConstants.keyPrefixAlbum + albumId + RedisConstants.keyPrefixPic + picId;
        Boolean ifVipOnly = redisService.getBooleanValue(key);
        if (ifVipOnly == null) {
            ifVipOnly = illustrationDao.ifIsVip(picId);
            redisService.setBooleanValue(key, ifVipOnly);
        }
        return ifVipOnly;
    }

    public boolean checkIfResourceAccessable(Long albumId, Long userId) {
        boolean hasAuth = true;
        Boolean isUserVip = userSupport.getUserVipInfo();
        Boolean isPicVipOnly = this.ifVipOnly(albumId, userId);
        if (isPicVipOnly && !isUserVip) {
            hasAuth = false;
        }
        return hasAuth;
    }
}
