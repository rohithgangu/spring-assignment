package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class Answers {

	private MapKey id;
	
	private String answer;

	public MapKey getId() {
		return id;
	}

	public void setId(MapKey id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}



	public Answers(MapKey id, 
			String answer) {
		super();
		this.id = id;
		
		this.answer = answer;
	}

	public Answers() {
		super();
	}

	
	

}
