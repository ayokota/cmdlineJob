package com.shc.scinventory.enterpriseShippingToolJobs.Bindings;

import java.util.List;

public class FAQInstance {
	private String question;
	private List<String> unitTypes;
	private String answer;
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public List<String> getUnitTypes() {
		return unitTypes;
	}
	public void setUnitTypes(List<String> unitTypes) {
		this.unitTypes = unitTypes;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
