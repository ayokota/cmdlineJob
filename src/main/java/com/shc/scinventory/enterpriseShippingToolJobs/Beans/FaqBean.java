package com.shc.scinventory.enterpriseShippingToolJobs.Beans;

public class FaqBean {
    private Integer visibility;
    private Integer question_id;
    private Integer ans_order;
    private String answer;

    public FaqBean () {
        this.visibility = 0;
        this.question_id = 0;
        this.ans_order = 0;
        this.answer = "";
    }

    public FaqBean (Integer visibility,
                    Integer question_id,
                    Integer ans_order,
                    String answer ) {
        this.visibility = visibility;
        this.question_id = question_id;
        this.ans_order = ans_order;
        this.answer = answer;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getAns_order() {
        return ans_order;
    }

    public void setAns_order(Integer ans_order) {
        this.ans_order = ans_order;
    }
}