package com.powernow.cdt.controller;


import com.alibaba.fastjson.JSONObject;
import com.powernow.cdt.model.OcBannerImage;
import com.powernow.cdt.model.OcModule;
import com.powernow.cdt.service.OcBannerImageService;
import com.powernow.cdt.service.OcModuleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class IndexController extends BaseController {

    @Resource
    private OcModuleService ocModuleService;
    
    @Resource
    private OcBannerImageService ocBannerImageService;

    @ResponseBody
    @RequestMapping(value = "/index", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String index(){

        List<OcModule> ocModuleList = ocModuleService.selectByCode("banner");
        for(OcModule ocModule : ocModuleList){
            String setting = ocModule.getSetting();
            JSONObject settingObject = JSONObject.parseObject(setting);
            int banner_id = settingObject.getIntValue("banner_id");
            int langId = language_id();
            List<OcBannerImage> ocBannerImages = ocBannerImageService.selectByBannerIdAndLangId(banner_id, langId);
            
        }



        return JSONObject.toJSONString(ocModuleList);
    }


}
