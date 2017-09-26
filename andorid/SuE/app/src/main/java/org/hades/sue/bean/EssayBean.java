package org.hades.sue.bean;

/**
 * Created by Hades on 2017/9/25.
 */

public class EssayBean {
    public boolean isHot;
    public String title;
    public String url;

    public EssayBean() {

    }

    public EssayBean(boolean isHot, String title, String url) {
        this.isHot = isHot;
        this.title = title;
        this.url = url;
    }
}
