package org.hades.sue.bean.smartcheck;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Hades on 2017/11/13.
 * 返回值按nodeType分为Question, Conclusion两种情况:

 nodeType = Question, 需要用户答题, 然后继续下一步问诊, 以下列出 3种题型的样例，
 以questionType（SingleSelection, MultiSelection, ValueEntry）进行区分。
 nodeType=Conclusion, 问诊结束, 给出结论
 情况一：nodeType=Question

 1. 单选题

 {
 "nodeType":"Question",
 "questionId": 3885,
 "questionText": “下颌或口腔有过受伤吗？例如跌落、猛击或其他击打导致疼痛。",
 "questionType": "SingleSelection"
 "answers": [
 {
 "answerId": 1,
 "answerText": "是"
 },
 {
 "answerId": 2,
 "answerText": "否"
 }
 ]
 }
 2. 多选题

 {
 "nodeType": "Question"
 "questionId": 292,
 "questionText": “存在任何下列可能的紧急症状吗？请选择所有符合的情况。",
 "questionType": "MultiSelection"
 "answers": [
 {
 "answerId": 1,
 "answerText": "声音低沉或无法完全张开嘴"
 },
 {
 "answerId": 2,
 "answerText": "呼吸困难"
 },
 {
 "answerId": 3,
 "answerText": "嘴唇、舌头或口腔突然肿胀"
 }
 ]
 }
 3. 问答题

 {
 "nodeType": "Question"
 "questionId": 292,
 "questionText": "近期大多数时候的收缩压值为多少（血压读数中的上值）？",
 "questionType": "ValueEntry",
 "entries":[ // 输入项列表
 {
 "key":"hbp",   // 输入项KEY
 "label":"收缩血压",   // 输入项标题
 "valueType":"number", // 输入项类型
 "value":"120" // 输入项默认值
 },
 ...
 ]
 }
 情况二：nodeType = Conclusion

 {
 "nodeType":"Conclusion",
 "verifyCode":"E20D23",// 用于医疗分诊后台的访问授权码
 "conclusions":[
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
 }
 */

public class Question implements Serializable{

    //nodeType = Question,
    //nodeType=Conclusion,

    public int currentStep;

    public Long createTime;
    public String speachText;
    public String speachSeparator;

    public String nodeType; //问题类型

    // ------- 问答属性域
    public int questionId;
    public String questionText; // 问题内容
    public String questionType; //题目类型
    public List<QuestionHelper.Answer> answers; //答案选择
    public List<QuestionHelper.Entrie> entries; //对答类型

    // -------- 结论域
    public String verifyCode;
    public List<QuestionHelper.Conclusion> conclusions;// 建议


    @Override
    public String toString() {
        return "Question{" +
                "nodeType='" + nodeType + '\'' +
                ", questionId=" + questionId +
                ", questionText='" + questionText + '\'' +
                ", questionType='" + questionType + '\'' +
                ", answers=" + answers +
                ", entries=" + entries +
                ", verifyCode='" + verifyCode + '\'' +
                ", conclusions=" + conclusions +
                '}';
    }
}
