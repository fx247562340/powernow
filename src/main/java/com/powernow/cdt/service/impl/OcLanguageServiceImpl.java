package com.powernow.cdt.service.impl;

import com.powernow.cdt.model.OcLanguage;
import com.powernow.cdt.service.OcLanguageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OcLanguageServiceImpl implements OcLanguageService {

    @Resource
    private OcLanguageService ocLanguageService;

    @Override
    public OcLanguage selectByCode(String code) {
        return ocLanguageService.selectByCode(code);
    }
}
