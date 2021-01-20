package com.xinyu.admin.modules.subdom.service;

import com.xinyu.admin.modules.subdom.model.SubDomInfo;

import java.util.List;

public interface SubDomAdminService {
    List<SubDomInfo> getInfo(String url, Long id);
}
