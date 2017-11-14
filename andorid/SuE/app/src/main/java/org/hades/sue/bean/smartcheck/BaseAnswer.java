package org.hades.sue.bean.smartcheck;

/**
 * Created by Hades on 2017/11/14.
 *
 * 回答问题的基类
 */

public class BaseAnswer {

    public int questionId;
    public String answer;
    public String answers;
    public EntryValues values;


    public static class EntryValues{
        public String key;
        public String value;
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
