package com.powernow.cdt.service;

import com.powernow.cdt.model.OcModule;

import java.util.List;

public interface OcModuleService {

    List<OcModule> selectAll();

    List<OcModule> selectByCode(String code);
}
