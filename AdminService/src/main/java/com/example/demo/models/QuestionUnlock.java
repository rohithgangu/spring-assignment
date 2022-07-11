package com.example.demo.models;

public class QuestionUnlock {

	private int questionId;
	private String answer;
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public QuestionUnlock(int questionId, String answer) {
		super();
		this.questionId = questionId;
		this.answer = answer;
	}
	public QuestionUnlock() {
		super();
	}
	
	
}
