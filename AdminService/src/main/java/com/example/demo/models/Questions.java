package com.example.demo.models;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "questions_data")
public class Questions {
	@Id
	@Column(name = "question_Id")
	private int queId;
	@Column(name = "question")
	private String question;
	
	@OneToMany(targetEntity = AdminQuestionAnswers.class,mappedBy = "id.question_id",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	List<AdminQuestionAnswers> questions;

	

	
	
}
