package org.hades.sue.bean;

import java.io.Serializable;

public class HeathNews implements Serializable{
    public int id;
    public String link;
    public String detail;
    public String title;
    public String date;
    public String md5;

    public HeathNews(String link, String detail, String title, String date, String md5) {
        this.link = link;
        this.detail = detail;
        this.title = title;
        this.date = date;
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "HeathNews{" +
                "link='" + link + '\'' +
                ", detail='" + detail + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", md5='" + md5 + '\'' +
                '}';
    }
}
