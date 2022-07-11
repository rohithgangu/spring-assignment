package com.example.demo.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admin_data")
public class Admin {
	@Id
	@Column(name="admin_id")
	int adminId;
	@Column(name="admin_name",unique = true)
	String adminName;
	@Column(name="admin_pass")
	String adminPassword;
	@Column(name="admin_status",columnDefinition = "integer default 0")
	int adminStatus;
	
	@OneToMany(targetEntity = AdminQuestionAnswers.class,mappedBy = "id.admin_id",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	List<AdminQuestionAnswers> questions;

	public Admin(int adminId, String adminName, String adminPassword, int adminStatus,
			List<AdminQuestionAnswers> questions) {
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

	public List<AdminQuestionAnswers> getQuestions() {
		return questions;
	}

	public void setQuestions(List<AdminQuestionAnswers> questions) {
		this.questions = questions;
	}

	
	
}
