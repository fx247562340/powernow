package com.powernow.cdt.controller;

import com.alibaba.fastjson.JSONObject;
import com.powernow.cdt.model.OcModule;
import com.powernow.cdt.service.OcModuleService;
import com.powernow.cdt.utils.CDTToken;
import com.powernow.cdt.utils.LogUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class OcModuleController {


    @Resource
    private OcModuleService ocModuleService;


    @ResponseBody
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getAll(@RequestParam(name = "accessToken") String accessToken){

        Integer userId = CDTToken.decodeToken(accessToken);
        LogUtils.log.info("userId:"+userId);
        List<OcModule> ocModuleList = ocModuleService.selectAll();

        return JSONObject.toJSONString(ocModuleList);
    }

}
