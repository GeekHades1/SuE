package org.hades.sue.bean;

/**
 * Created by Hades on 2017/9/25.
 *
 * 医生业务Bean
 */

public class DoctorBean {
    public String name;
    public String prof;
    public String office;

    public DoctorBean(String name) {
        this.name = name;
    }

    public DoctorBean(String name, String prof, String office) {
        this.name = name;
        this.prof = prof;
        this.office = office;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
