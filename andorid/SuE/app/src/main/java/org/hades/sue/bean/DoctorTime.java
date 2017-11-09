package org.hades.sue.bean;

/**
 * Created by Hades on 2017/11/9.
 *
 * 医生时间安排类
 */
public class DoctorTime {
    public int doctorId;
    public int sun;
    public int mon;
    public int tue;
    public int wed;
    public int thu;
    public int fri;
    public int sat;

    @Override
    public String toString() {
        return "DoctorTime{" +
                "doctorId=" + doctorId +
                ", sun=" + sun +
                ", mon=" + mon +
                ", tue=" + tue +
                ", wed=" + wed +
                ", thu=" + thu +
                ", fri=" + fri +
                ", sat=" + sat +
                '}';
    }
}
