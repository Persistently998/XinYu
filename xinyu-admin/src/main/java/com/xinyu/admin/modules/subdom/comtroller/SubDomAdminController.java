package com.xinyu.admin.modules.subdom.comtroller;

import com.xinyu.admin.common.api.CommonResult;
import com.xinyu.admin.modules.subdom.model.SubDomInfo;
import com.xinyu.admin.modules.subdom.service.SubDomAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author xinyu
 * @date 2021/1/18 22:39
 */
@Controller
@Api(tags = "SubDomAdminController", description = "子域名管理")
@RequestMapping("/subdo")
public class SubDomAdminController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private SubDomAdminService subDomAdminService;

    @ApiOperation("通过url开始爬取相关子域名")
    @RequestMapping("/getSubDo")
    @ResponseBody
    public CommonResult<List<SubDomInfo>> getSubDoInfo(@RequestParam(value = "url", required = false) String url,
                                                       @RequestParam(value = "id", required = false) Long id) {

        List<SubDomInfo> subDomInfos = subDomAdminService.getInfo(url, id);
        System.out.println(url);
        if (subDomInfos!=null&&subDomInfos.size()>0){
            return CommonResult.success(subDomInfos);
        }else {
            return CommonResult.failed();
        }
    }

}
