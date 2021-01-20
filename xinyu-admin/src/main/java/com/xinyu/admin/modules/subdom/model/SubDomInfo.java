package com.xinyu.admin.modules.subdom.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
/**
 * 子域名返回对象
 * @author xinyu
 * @date 2021/1/18 22:59
 */
public class SubDomInfo {

    private Long id;
    private String url;
}
