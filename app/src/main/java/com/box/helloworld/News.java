package com.box.helloworld;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by BOXinWORLD on 2018/3/17.
 */

public class News extends BmobObject implements Serializable {
    private String newsTitle;   //新闻标题
    private String newsUrl;     //新闻链接地址
    private String desc;        //新闻概要
    private String newsFrom;    //新闻来源
    private String newsType;    //新闻类别
    private String newsStyle;   //新闻风格

    public News(String newsTitle, String newsUrl, String desc, String newsTime,String newsType,String newsStyle) {
        this.newsTitle = newsTitle;
        this.newsUrl = newsUrl;
        this.desc = desc;
        this.newsFrom = newsTime;
        this.newsStyle= newsStyle;
        this.newsType= newsType;
    }
    public News(String newsTitle, String newsUrl, String desc, String newsTime){
        this.newsTitle = newsTitle;
        this.newsUrl = newsUrl;
        this.desc = desc;
        this.newsFrom = newsTime;
        this.newsStyle= "";
        this.newsType= "";

    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNewsFrom() {
        return newsFrom;
    }

    public void setNewsFrom(String newsFrom) {
        this.newsFrom = newsFrom;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String NewsType) {
        this.newsType = NewsType;
    }

    public String getNewsStyle() {
        return newsStyle;
    }

    public void setNewsStyle(String desc) {
        this.newsStyle = desc;
    }
}
