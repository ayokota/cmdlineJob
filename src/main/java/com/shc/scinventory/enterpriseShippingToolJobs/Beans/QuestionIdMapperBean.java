package com.shc.scinventory.enterpriseShippingToolJobs.Beans;

public class QuestionIdMapperBean {
    private Integer id;
    private String question_text;

    public QuestionIdMapperBean() {
        this.id = id;
        this.question_text = question_text;
    }

    public QuestionIdMapperBean (Integer id, String question_text) {
        this.id = id;
        this.question_text = question_text;

    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }
}
