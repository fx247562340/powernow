package com.powernow.cdt.service.impl;

import com.powernow.cdt.dao.OcBannerImageMapper;
import com.powernow.cdt.model.OcBannerImage;
import com.powernow.cdt.service.OcBannerImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OcBannerImageServiceImpl implements OcBannerImageService {

    @Resource
    private OcBannerImageMapper ocBannerImageMapper;


    @Override
    public List<OcBannerImage> selectByBannerIdAndLangId(Integer banner_id, Integer langId) {
        return ocBannerImageMapper.selectByBannerIdAndLangId(banner_id,langId);
    }
}
