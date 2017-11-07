package com.machine.crawler;

import com.geccocrawler.gecco.GeccoEngine;
import org.junit.Test;

public class CrawlerTest {

    @Test
    public void crawler() {
        GeccoEngine.create()
                //工程的包路径
                .classpath("com.machine.crawler")
                //开始抓取的页面地址
                .start("https://www.baidu.com/")
                //开启几个爬虫线程
                .thread(1)
                //单个爬虫每次抓取完一个请求后的间隔时间
                .interval(20)
                //循环抓取
                .loop(false)
                //使用pc端userAgent
                .mobile(false)
                //非阻塞方式运行
                .start();

    }

}
