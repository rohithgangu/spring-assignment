package com.example.demo.models;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "answers_data")
public class AdminQuestionAnswers {
	@EmbeddedId
	QuestionAdminPK id;
	
	@ManyToOne
	@MapsId("adminId")
	@JoinColumn(name = "admin_id")
	@JsonIgnore
	Admin admin;
	
	@ManyToOne
	@MapsId("queId")
	@JoinColumn(name = "question_id")
	@JsonIgnore
	Questions questions;
	
	private String answer;

	public QuestionAdminPK getId() {
		return id;
	}

	public void setId(QuestionAdminPK id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Questions getQuestions() {
		return questions;
	}

	public void setQuestions(Questions questions) {
		this.questions = questions;
	}

	
	public AdminQuestionAnswers(QuestionAdminPK id, Admin admin, Questions questions, String answer) {
		super();
		this.id = id;
		this.admin = admin;
		this.questions = questions;
		this.answer = answer;
	}

	public AdminQuestionAnswers() {
		super();
	}

	@Override
	public String toString() {
		return "AdminQuestionAnswers [id=" + id + ", admin=" + admin + ", questions=" + questions + ", answer="
				+ answer + "]";
	}
	
	
}
