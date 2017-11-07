package org.hades.sue.bean;

/**
 * Created by Hades on 2017/9/25.
 */

public class HospitalBean {
    public String name;
    public String photo;
    public String tel;
    public int distance; //距离 m为单位

    public HospitalBean(String name) {
        this.name = name;
    }
}
