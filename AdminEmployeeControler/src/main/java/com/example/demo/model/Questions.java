package com.example.demo.model;

import java.util.List;


public class Questions {
	
	private int queId;
	private String question;
	
	private List<Answers> questions;

	public Questions(int queId, String question, List<Answers> questions) {
		super();
		this.queId = queId;
		this.question = question;
		this.questions = questions;
	}

	public int getQueId() {
		return queId;
	}

	public void setQueId(int queId) {
		this.queId = queId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<Answers> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Answers> questions) {
		this.questions = questions;
	}

	public Questions() {
		super();
	}

	
}
