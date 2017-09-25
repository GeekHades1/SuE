package org.hades.sue.bean;

/**
 * Created by Hades on 2017/9/25.
 *
 * 医生业务Bean
 */

public class DoctorBean {
    private String name;

    public DoctorBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
