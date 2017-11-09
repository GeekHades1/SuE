package org.hades.sue.bean;

/**
 * Created by Hades on 2017/11/9.
 *
 * 医生类
 */
public class DoctorBean {
    public int doctorId;
    public String name;
    public String img;
    public String department;
    public String job;
    public String position;
    public String hospital;
    public String city;

    @Override
    public String toString() {
        return "DoctorBean{" +
                "doctorId=" + doctorId +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", department='" + department + '\'' +
                ", job='" + job + '\'' +
                ", position='" + position + '\'' +
                ", hospital='" + hospital + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
