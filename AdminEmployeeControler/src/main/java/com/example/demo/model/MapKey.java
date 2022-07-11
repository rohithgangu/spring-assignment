package com.example.demo.model;

import java.util.Objects;

public class MapKey {
	int admin_id;
	int question_id;
	public int getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(int admin_id) {
		this.admin_id = admin_id;
	}
	public int getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}
	
	public MapKey(int admin_id, int question_id) {
		super();
		this.admin_id = admin_id;
		this.question_id = question_id;
	}
	public MapKey() {
		super();
	}
	
}
