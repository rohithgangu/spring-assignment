package com.example.demo.model;

import java.util.List;


public class QueAns {
	private int adminId;
	private List<QuestionUnlock> answersList;
	private String newPassword;
	
	public QueAns(int adminId, List<QuestionUnlock> answersList, String newPassword) {
		super();
		this.adminId = adminId;
		this.answersList = answersList;
		this.newPassword = newPassword;
	}
	public QueAns() {
		super();
	}
	
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public List<QuestionUnlock> getAnswersList() {
		return answersList;
	}
	public void setAnswersList(List<QuestionUnlock> answersList) {
		this.answersList = answersList;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	
	
	
}
