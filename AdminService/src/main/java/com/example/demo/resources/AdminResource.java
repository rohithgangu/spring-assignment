package com.example.demo.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.AdminRepository;
import com.example.demo.services.AdminService;
import com.example.demo.services.QuestionService;

import net.bytebuddy.asm.Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName;

import com.example.demo.models.*;
import java.util.List;
@RestController
public class AdminResource {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private QuestionService questionService;
	
	//new apis
	@PostMapping("/AddAdmin")
	public Admin addAdmin(@RequestBody Admin admin) {
		return adminService.saveAdmin(admin);
	}
	@GetMapping("/GetAdmin")
	public Admin getAdmin(@RequestParam("adminId") int adminId) {
		return adminService.getAdmin(adminId);
	}
	@PutMapping("/AddQuestions")
	public Admin addQuestions(@RequestParam("adminId") int adminId,@RequestBody List<AdminQuestionAnswers> questions) {
		Admin admin = adminService.getAdmin(adminId);
		questionService.addQuestions(questions);
		admin.getQuestions().addAll(questions);
		return admin;		
	}
	@DeleteMapping("/DeleteQuestion")
	public ResponseEntity<String> deleteQuestions(@RequestParam("adminId") int adminId,@RequestBody int quesId) {
		return questionService.deleteQuestion(new QuestionAdminPK(adminId,quesId));
	}
	
	/*@PostMapping("/AddAdmins")
	public List<Admin> addAdmins()*/
	
	
	
	/*
	
	//Get methods
	@GetMapping("/Admin")
	public List<Admin> getAdmin() {
		return adminService.findadmin();
	}
	
	@GetMapping("/GetAdmin")
	public Admin getAdminById(@RequestParam("adminId") int adminId) {
		return adminService.getAdmin(adminId);
	}
	
	
	
	@PostMapping("/AdminLogin")
	public ResponseEntity<String> adminLogin(@RequestBody Admin admin) {
		return adminService.adminLogin(admin);
	}
	
	//not working
	@GetMapping("/Admin/{adminId}/SecQue")
	public List<AdminQuestionAnswers> getQuestions(@PathVariable("adminId")int adminId){
	
		return questionService.getquestions(adminId);
	}
	
	
	
	
	
	
	//working
	@PostMapping("/AddAdmin")
	public ResponseEntity<String> addAminsques(@RequestBody Admin admin) {
		return adminService.addAdminwithques(admin);
	}
	
	
	
	//Put methods
	
	@PutMapping("/Admin/{adminId}/AddQuestions")
	public Admin addQuestions(@PathVariable("adminId") int adminId,@RequestBody List<AdminQuestionAnswers> questions) {
		Admin admin = adminService.getAdmin(adminId);
		questionService.addQuestions(questions);
		admin.getQuestions().addAll(questions);
		return admin;		
	}
	
	
	@PutMapping("/Admin/{adminId}/UnlockAccount")
	public ResponseEntity<String> unlockAccount(@PathVariable("adminId") int adminId,@RequestBody QueAns queAns) {
		queAns.setAdminId(adminId);
		return adminService.unlockAdmin(queAns);
	}
	
	@PutMapping("/Admin/{adminId}/ResetPassword")
	public ResponseEntity<String> reserPassword(@PathVariable("adminId") int adminId,@RequestBody QueAns queAns) {
		queAns.setAdminId(adminId);
		return adminService.resetAdminPassword(queAns);
	}


	
	//Delete Methods
	@DeleteMapping("/Admin/{adminId}/DeleteQuestions")
	public ResponseEntity<String> deleteQuestions(@PathVariable("adminId") int adminId,@RequestBody List<Integer> queId)
	{
		ResponseEntity<String> result = null;
		for(int i=0;i<queId.size();i++) {
			result=questionService.deleteQuestion(new QuestionAdminPK(adminId,queId.get(i)));
		}
		return result;
	}
	*/
}
