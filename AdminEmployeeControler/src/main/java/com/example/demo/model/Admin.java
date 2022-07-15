package com.example.demo.model;

import java.util.List;

public class Admin {
	
	private int adminId;
	private String adminName;
	private String adminPassword;
	private int adminStatus;
	

	private List<Answers> questions;


	public int getAdminId() {
		return adminId;
	}


	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}


	public String getAdminName() {
		return adminName;
	}


	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}


	public String getAdminPassword() {
		return adminPassword;
	}


	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}


	public int getAdminStatus() {
		return adminStatus;
	}


	public void setAdminStatus(int adminStatus) {
		this.adminStatus = adminStatus;
	}


	public List<Answers> getQuestions() {
		return questions;
	}


	public void setQuestions(List<Answers> questions) {
		this.questions = questions;
	}


	public Admin(int adminId, String adminName, String adminPassword, int adminStatus, List<Answers> questions) {
		super();
		this.adminId = adminId;
		this.adminName = adminName;
		this.adminPassword = adminPassword;
		this.adminStatus = adminStatus;
		this.questions = questions;
	}


	public Admin() {
		super();
	}


	public Admin(String adminName) {
		super();
		this.adminName = adminName;
	}

	
}
