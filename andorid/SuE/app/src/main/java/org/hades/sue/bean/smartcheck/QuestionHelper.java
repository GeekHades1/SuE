package org.hades.sue.bean.smartcheck;

import java.util.List;

/**
 * Created by Hades on 2017/11/13.
 */

public class QuestionHelper {
    /**
     * 类型是 问题
     */
    public static final String NODETYPE_QUESTION = "Question";

    /**
     * 类型是结论
     */
    public static final String NODETYPE_CONCLUSION = "Conclusion";

    /**
     * 单选题
     */
    public static final String QUESTION_TYPE_SINGLE = "SingleSelection";

    /**
     * 多选题
     */
    public static final String QUESTION_TYPE_MULTI = "MultiSelection";

    /**
     * 问答题
     */
    public static final String QUESTION_TYPE_ENTRY = "ValueEntry";

    /**
     * 行动类型：在家静养
     */
    public static final String ACTION_HOME = "home";

    /**
     * 行动类型：咨询专业护士
     */
    public static final String ACTION_NURSE = "nurse";

    /**
     * 行动类型：去医院就诊
     */
    public static final String ACTION_HOSPITAL = "hospital";

    /**
     * 行动类型：急救
     */
    public static final String ACTION_EMERG = "emergency";

    /**
     * 问题答案
     */
    public class Answer{
        public int answerId;
        public String answerText;

        @Override
        public String toString() {
            return "Answer{" +
                    "answerId=" + answerId +
                    ", answerText='" + answerText + '\'' +
                    '}';
        }
    }

    /**
     * "key":"hbp",   // 输入项KEY
     "label":"收缩血压",   // 输入项标题
     "valueType":"number", // 输入项类型
     "value":"120" // 输入项默认值
     */
    public class Entrie{
        public String key;
        public String label;
        public String valueType;
        public String value;

        @Override
        public String toString() {
            return "Entrie{" +
                    "key='" + key + '\'' +
                    ", label='" + label + '\'' +
                    ", valueType='" + valueType + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    /**
     * "conclusions":[
     {
     "actionType":"hospital",// 建议的行动类型，home:
     在家静养、nurse: 咨询专业护士、hospital:
     去医院就诊、emergency：急救
     "actionTime":"24h", // 建议的采取行动的时间, 例如24小时内
     "actionLabel":"请在24小时内前往社区医院就医", // 建议采取行动
     "emergencyLevel": "8", // 紧急程度（最高紧急程度为1）
     "departInfo": "内科", // 建议挂号科室
     "title": "可能是百日咳", // 结论标题
     "content": "百日咳的典型症状是一阵长时间的咳嗽，接着大口喘气，听起来就像是在呐喊。
     一段长时间的咳嗽后也可能会出现呕吐。百日咳是一种在起初时很像普通的感冒的病毒性感染
     ，但一至两周后咳嗽变得湿润，并有浓厚的粘液。长时间的咳嗽让人十分痛苦。如果不进行治疗，
     感染会持续至少6周，且可能需要3个月才能完全消除。 如果能及早使用抗生素治疗可能会有所帮助
     ，并且可能缩短病程。处方药也可用于治疗咳嗽。",// 结论描述
     "suggets":["让别人开车送你去医院。","如果没人开车送你去医院，
     建议拨打120或当地急救电话寻求帮助","在向医生进行咨询之前，不要吃或喝任何东西。
     ","如果可能，请带上所有服用的药物清单（包括非处方药、维生素或补充剂）；
     如果没有药物清单，也可以带上药瓶。"]// 补充建议
     },
     ...
     ]
     */
    public class Conclusion{
        public String actionType; //ACTION_TYPE
        public String actionTime;
        public String actionLabel;
        public String emergencyLevel; //最高1级
        public String departInfo;
        public String title;
        public String content;
        public List<Suggets> suggets;

        @Override
        public String toString() {
            return "Conclusion{" +
                    "actionType='" + actionType + '\'' +
                    ", actionTime='" + actionTime + '\'' +
                    ", actionLabel='" + actionLabel + '\'' +
                    ", emergencyLevel='" + emergencyLevel + '\'' +
                    ", departInfo='" + departInfo + '\'' +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", suggets=" + suggets +
                    '}';
        }
    }

    /**
     * 补充建议
     */
    public class Suggets{
        public String sugget;

        @Override
        public String toString() {
            return "Suggets{" +
                    "sugget='" + sugget + '\'' +
                    '}';
        }
    }
}
