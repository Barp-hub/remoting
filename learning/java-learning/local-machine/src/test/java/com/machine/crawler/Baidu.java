package com.machine.crawler;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.Html;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl = "https://www.baidu.com/", pipelines = "consolePipeline")
public class Baidu implements HtmlBean {

    @Html
    @HtmlField(cssPath = ".s-lite .title")
    private String submit;

    public String getSubmit() {
        return submit;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
    }
}
