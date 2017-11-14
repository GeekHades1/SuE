package org.hades.sue.bean.smartcheck;

/**
 * Created by Hades on 2017/11/8.
 */
public class BodyCheck {
    public String body;
    public String bodyId;
    public String gender;
    public String symptom;
    public String symptomId;

    @Override
    public String toString() {
        return "BodyCheck{" +
                "body='" + body + '\'' +
                ", bodyId='" + bodyId + '\'' +
                ", gender='" + gender + '\'' +
                ", symptom='" + symptom + '\'' +
                ", symptomId='" + symptomId + '\'' +
                '}';
    }
}
