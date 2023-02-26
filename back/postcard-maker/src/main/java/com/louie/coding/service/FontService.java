package com.louie.coding.service;

import com.louie.coding.dao.FontDao;
import com.louie.coding.entity.Font;
import com.louie.coding.entity.entityResp.FontResp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class FontService {
    @Resource
    private FontDao fontDao;

    public List<FontResp> getFonts() {
        List<Font> fonts = fontDao.getFonts();
        List<FontResp> res = new ArrayList<>();
        for (Font font : fonts) {
            FontResp fontResp = new FontResp();
            fontResp.setLabel(font.getName());
            fontResp.setValue(font.getId());
            res.add(fontResp);
        }
        return res;
    }
}
