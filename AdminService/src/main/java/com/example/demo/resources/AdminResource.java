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
	
	@GetMapping("/GetAdmins")
	public List<Admin> getAdmins(){
		return adminService.getAdmins();
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
	
	@DeleteMapping("/DeleteAdmin")
	public void deleteAdmin(@RequestParam("adminId") int adminId) {
		adminService.deleteAdmin(adminId);
	}
	

}
