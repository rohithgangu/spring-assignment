package com.example.demo.services;

import java.util.List;

import org.aspectj.weaver.ast.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.models.Admin;
import com.example.demo.models.AdminQuestionAnswers;
import com.example.demo.models.QueAns;
import com.example.demo.models.QuestionAdminPK;
import com.example.demo.models.Questions;
import com.example.demo.repository.AdminQuestionRepository;
@Service
public class QuestionService {
	
	@Autowired
	private AdminQuestionRepository adminQuestionRepository;
	public QuestionService(AdminQuestionRepository repository)
	{
		this.adminQuestionRepository=repository;
	}

	
	public AdminQuestionAnswers addQuestion(AdminQuestionAnswers question)
	{
		return  adminQuestionRepository.save(question);
	}

	public List<AdminQuestionAnswers>  addQuestions(List<AdminQuestionAnswers> question)
	{
		return adminQuestionRepository.saveAll(question);
	}


	//used
	public ResponseEntity<String> deleteQuestion(QuestionAdminPK key) {
		
		adminQuestionRepository.deleteById(key);
		return new ResponseEntity<>("Question deleted successfully",HttpStatus.ACCEPTED);
	}


	//used
	public List<AdminQuestionAnswers> getquestions(int adminId) {
		return adminQuestionRepository.findAll(adminId);
	}

	

}
