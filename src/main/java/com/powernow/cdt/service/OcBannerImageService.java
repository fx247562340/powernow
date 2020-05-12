package com.powernow.cdt.service;

import com.powernow.cdt.model.OcBannerImage;

import java.util.List;

public interface OcBannerImageService {


    List<OcBannerImage> selectByBannerIdAndLangId(Integer banner_id,Integer langId);
}
