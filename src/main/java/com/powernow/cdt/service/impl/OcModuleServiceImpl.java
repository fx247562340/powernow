package com.powernow.cdt.service.impl;

import com.powernow.cdt.dao.OcModuleMapper;
import com.powernow.cdt.model.OcModule;
import com.powernow.cdt.service.OcModuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OcModuleServiceImpl implements OcModuleService {

    @Resource
    private OcModuleMapper ocModuleMapper;

    @Override
    public List<OcModule> selectAll() {
        return ocModuleMapper.selectAll();
    }


    @Override
    public List<OcModule> selectByCode(String code) {
        return ocModuleMapper.selectByCode(code);
    }

}
