package org.hades.sue.bean.smartcheck;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Hades on 2017/11/14.
 *
 * 回答问题的基类
 */

public class BaseAnswer {

    public int questionId;
    public String answer;
    public String answers;
    public List<EntryValues> values;


    public static class EntryValues implements Serializable{
        public String key;
        public String value;


        @Override
        public String toString() {
            return "EntryValues{" +
                    "key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BaseAnswer{" +
                "questionId=" + questionId +
                ", answer='" + answer + '\'' +
                ", answers='" + answers + '\'' +
                ", values=" + values +
                '}';
    }
}
