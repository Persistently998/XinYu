package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import utils.PortUtils;

/**
 * 端口扫描类型
 */
@Controller
public class PortScanController {


    public static void main(String[] args) {
        String ip="119.3.191.196";
        PortUtils portUtils=new PortUtils();
        portUtils.ip(ip);
    }


}
