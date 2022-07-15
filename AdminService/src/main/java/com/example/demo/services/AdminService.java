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

	
	
	
	public Admin saveAdmin(Admin admin) {
		return adminRepository.save(admin);
	}


	public Admin getAdmin(int adminId) {
		return adminRepository.findById(adminId).orElse(null);
	}


	public List<Admin> getAdmins() {
		return adminRepository.findAll();
	}


	public void deleteAdmin(int adminId) {
		adminRepository.deleteById(adminId);
	}


	public Admin getAdmin(String adminName) {
		return adminRepository.findByAdminName(adminName);
	}

	
}
