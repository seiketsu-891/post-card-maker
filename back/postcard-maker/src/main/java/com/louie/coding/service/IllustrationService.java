package com.louie.coding.service;

import com.louie.coding.dao.IllustrationDao;
import com.louie.coding.entity.Illustration;
import com.louie.coding.entity.PageResult;
import com.louie.coding.entityResp.IllustrationResp;
import com.louie.coding.util.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IllustrationService {
    @Resource
    private IllustrationDao illustrationDao;

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
}
