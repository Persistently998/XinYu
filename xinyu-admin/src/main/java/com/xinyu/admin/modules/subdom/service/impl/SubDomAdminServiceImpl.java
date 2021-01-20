package com.xinyu.admin.modules.subdom.service.impl;

import com.xinyu.admin.modules.subdom.model.SubDomInfo;
import com.xinyu.admin.modules.subdom.service.SubDomAdminService;
import com.xinyu.admin.modules.subdom.utlis.IPoneUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubDomAdminServiceImpl implements SubDomAdminService {
    @Autowired
    IPoneUtils iPoneUtils;
    @Override
    public List<SubDomInfo> getInfo(String url, Long id) {
        iPoneUtils.process(url, id);
        return null;
    }
}
