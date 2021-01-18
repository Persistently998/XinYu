package com.xinyu.admin.utils;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

/**
 * 获取ip138网站的子域名
 */
@Component
public class IP138Utils implements PageProcessor {

    private String ip138url="https://site.ip138.com/";

    private String url="baidu.com";
    /**
     * 开始爬取信息
     *
     * @param url
     * @param id
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 100*10000)
    public void process(String url,Long id) {
        Spider.create(new IP138Utils())
                .addUrl(ip138url+url)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .thread(10)
                .run();
    }

    @Override
    public void process(Page page) {
        String html = page.getHtml().toString();
        System.out.println(html);
    }
    private Site site = Site.me()
            .setCharset("gbk")//设置编码
            .setTimeOut(10 * 1000)//设置超时时间
            .setRetrySleepTime(3000)//设置重试的间隔时间
            .setRetryTimes(3);//设置重试的次数

    @Override
    public Site getSite() {
        return site;
    }
}
