package com.xinyu.admin.modules.subdom.utlis;

import com.xinyu.admin.common.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * ip137获取
 * @author xinyu
 * @date 2021/1/18 23:08
 */
@Component
public class IPoneUtils implements PageProcessor {
    @Autowired
    private RedisService redisService;
    private String ipone = "https://chaziyu.com/";

    /**
     * 开始爬取信息
     * @param url
     * @param id
     */
    public void process(String url, Long id) {
        Spider.create(new IPoneUtils())
                .addUrl(ipone + url)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000)))
                .thread(10)
                .run();
    }
    @Override
    public void process(Page page) {
        String html = page.getHtml().toString();
        List<Selectable> list = page.getHtml().css("div.c-bd td a").nodes();
        for (Selectable selectable:list){
            System.out.println("循环的网址-----"+selectable.css("a","text").toString());
        }
        Html html1=page.getHtml();

        String text = html1.css("div.c-bd td a", "text").toString();
        System.out.println("获得的地址为"+text);

    }

    private Site site=Site.me()
            .setCharset("utf8")//设置编码
            .setTimeOut(10*1000)//设置超时时间
            .setRetryTimes(3000)//设置重试间隔
            .setRetryTimes(3);//设置重试次数
    @Override
    public Site getSite() {
        return site;
    }
}
