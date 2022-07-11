package com.example.demo.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Cleanup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Embeddable
public class QuestionAdminPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Column(name = "admin_id")
	int admin_id;
	@Column(name = "question_id")
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public QuestionAdminPK(int admin_id, int question_id) {
		super();
		this.admin_id = admin_id;
		this.question_id = question_id;
	}
	public QuestionAdminPK() {
		super();
	}
	@Override
	public int hashCode() {
		return Objects.hash(admin_id, question_id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestionAdminPK other = (QuestionAdminPK) obj;
		return admin_id == other.admin_id && question_id == other.question_id;
	}
	
	
	
	
}
