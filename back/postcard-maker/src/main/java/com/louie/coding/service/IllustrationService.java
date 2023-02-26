package com.louie.coding.service;

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
    }
}
