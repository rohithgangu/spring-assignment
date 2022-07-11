package com.example.demo.services;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.models.Admin;
import com.example.demo.models.AdminQuestionAnswers;
import com.example.demo.models.QueAns;
import com.example.demo.models.QuestionUnlock;
import com.example.demo.models.Questions;
import com.example.demo.repository.AdminQuestionRepository;
import com.example.demo.repository.AdminRepository;

import ch.qos.logback.core.status.Status;

@Service
public class AdminService {

	@Autowired
	AdminRepository adminRepository;
	
	public AdminService(AdminRepository repository)
	{
		this.adminRepository=repository;
	}

	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AdminQuestionRepository adminQuestionRepository;

	
	/*
	//get all the admins
	public List<Admin> findadmin() {
		return adminRepository.findAll();
	}
	//get admin by ID
	public Admin getAdmin(int adminId) {
		return adminRepository.findById(adminId).orElse(null);
	}
	
	// used for login
	public ResponseEntity<String> getAdminPass(String admin_Name) {
		return new ResponseEntity<>(adminRepository.getAdminPassword(admin_Name),HttpStatus.ACCEPTED);
	}
	


	// add new admins only if he provide minimum 2 questions
	public ResponseEntity<String> addAdminwithques(Admin admin) {
		List<AdminQuestionAnswers> ques = admin.getQuestions();
		if(ques.size()<2) {
			return new ResponseEntity<>("provide minimum two questions",HttpStatus.BAD_REQUEST);
		}
		else {
			adminRepository.save(admin);
			return new ResponseEntity<String>("successfully registered",HttpStatus.OK);
		}
	}

	
	//used for admin login
	public ResponseEntity<String> adminLogin(Admin admin) {
		Admin exeAdmin = adminRepository.findById(admin.getAdminId()).orElse(null);
		int currentStatus = exeAdmin.getAdminStatus();
		if(currentStatus<3) {
		if(exeAdmin.getAdminPassword().equals(admin.getAdminPassword())) {
			exeAdmin.setAdminStatus(0);
			adminRepository.save(exeAdmin);

			return new ResponseEntity<>("correct password",HttpStatus.ACCEPTED);
		}
		else {
			exeAdmin.setAdminStatus(currentStatus+1);
			adminRepository.save(exeAdmin);
			if(exeAdmin.getAdminStatus()==3) {
				return new ResponseEntity<> ("you have entered wrong password and your account has been locked",HttpStatus.BAD_REQUEST);
			}
			else {
			return new ResponseEntity<String>("wrong password try again",HttpStatus.BAD_REQUEST);
			}
			}
		}
		else {
			return new ResponseEntity<>("your account has been locked please unlock to continue",HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	// used by unlock and forgotpassword methods to check security questions
	public int checkQuestions(List<QuestionUnlock> qU,List<AdminQuestionAnswers> aQA) {
		int flag = 0;
		for(int i=0;i<qU.size();i++) {
			for(int j=0;j<aQA.size();j++) {
				if(qU.get(i).getQuestionId()==aQA.get(j).getId().getQuestion_id() && qU.get(i).getAnswer().equals(aQA.get(j).getAnswer())) {
					flag++;
				}
			}
		}
		return flag;
	}
	
	
	
	
	//used to unlock admin 
	public ResponseEntity<String> unlockAdmin(QueAns queAns) {
		Admin admin = adminRepository.findById(queAns.getAdminId()).orElse(null);
		if(admin.getAdminStatus()>=3) {
		List<AdminQuestionAnswers> allCorrectAnswers = admin.getQuestions();
		List<QuestionUnlock> givenQns=queAns.getAnswersList();
		if(givenQns.size()>=2) {
		int flag = checkQuestions(givenQns, allCorrectAnswers);
		if (flag == givenQns.size()) {
			admin.setAdminStatus(0);
			adminRepository.save(admin);
			return new ResponseEntity<>("account unlocked",HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>("answer for the security question does not match",HttpStatus.BAD_REQUEST);
		}
		}
		else {
			return new ResponseEntity<>("please provide answers for atleast 2 security questions",HttpStatus.BAD_REQUEST);
		}}
		else {
			return new ResponseEntity<String>("your account is not locked please procedd to login",HttpStatus.BAD_REQUEST);
		}
	}
	
	
	//used to reset admin password
	public ResponseEntity<String> resetAdminPassword(QueAns queAns) {
		Admin admin = adminRepository.findById(queAns.getAdminId()).orElse(null);
		List<AdminQuestionAnswers> allCorrectAnswers = admin.getQuestions();
		List<QuestionUnlock> givenQns=queAns.getAnswersList();
		if(givenQns.size()>=2) {
		int flag = checkQuestions(givenQns, allCorrectAnswers);
		if (flag == givenQns.size()) {
			admin.setAdminPassword(queAns.getNewPassword());
			adminRepository.save(admin);
			return new ResponseEntity<>("password reset successfull",HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>("answer for the security question does not match",HttpStatus.BAD_REQUEST);
		}
		}
		else {
			return new ResponseEntity<>("please provide answers for atleast 2 security questions",HttpStatus.BAD_REQUEST);
		}
	}*/
	
	
	public Admin saveAdmin(Admin admin) {
		return adminRepository.save(admin);
	}


	public Admin getAdmin(int adminId) {
		return adminRepository.findById(adminId).orElse(null);
	}

	
}
