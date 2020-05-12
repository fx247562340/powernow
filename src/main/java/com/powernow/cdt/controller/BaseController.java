package com.powernow.cdt.controller;

import com.powernow.cdt.model.OcLanguage;
import com.powernow.cdt.service.OcLanguageService;
import com.powernow.cdt.utils.CDTToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OcLanguageService ocLanguageService;


    public Integer userId(){

        String accessToken = request.getParameter("access_token");
        if(StringUtils.isBlank(accessToken)){
            return -1;
        }
        Integer userId = CDTToken.decodeToken(accessToken);
        return userId;
    }

    public Integer language_id(){
        String lang = request.getParameter("lang");
        OcLanguage ocLanguage = ocLanguageService.selectByCode(lang);
        if(ocLanguage != null){
            return ocLanguage.getLanguageId();
        }
        return -1;
    }

}
