package com.example.demo.services;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.model.Admin;
import com.example.demo.model.Answers;
import com.example.demo.model.AuthenticationRequest;
import com.example.demo.model.AuthenticationResponse;
import com.example.demo.model.QueAns;
import com.example.demo.model.QuestionUnlock;
import com.example.demo.model.Status;
import com.example.demo.utility.JwtUtil;
import com.google.inject.Key;

import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class AdminService {

	@Autowired
	private RestTemplate restTemplate;


	
	
	public int checkQuestions(List<QuestionUnlock> qU,List<Answers> aQA) {
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

	public ResponseEntity<Status> adminUnlock(QueAns queAns) {
		String url = "http://Admin-service/GetAdmin";
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("adminId", "{adminId}").encode().toUriString();
		Map<String, Integer> params = new HashMap<String,Integer>();
		params.put("adminId", queAns.getAdminId());
		Admin admin = restTemplate.getForObject(urlTemplate, Admin.class,params);
		Status status = new Status();
		
		if(admin.getAdminStatus()>=3) {
		List<Answers> allCorrectAnswers = admin.getQuestions();
		List<QuestionUnlock> givenQns=queAns.getAnswersList();
		if(givenQns.size()>=2) {
		int flag = checkQuestions(givenQns, allCorrectAnswers);
		if (flag == givenQns.size()) {
			admin.setAdminStatus(0);
			//adminRepository.save(admin);
			restTemplate.postForObject("http://Admin-service/AddAdmin", admin, Admin.class);
			status.setMessage("account unlocked");
			return new ResponseEntity<>(status,HttpStatus.OK);
		}
		else {
			status.setMessage("answer for the security question does not match");
			return new ResponseEntity<>(status,HttpStatus.BAD_REQUEST);
		}
		}
		else {
			status.setMessage("please provide answers for atleast 2 security questions");
			return new ResponseEntity<>(status,HttpStatus.BAD_REQUEST);
		}}
		else {
			status.setMessage("your account is not locked please procedd to login");
			return new ResponseEntity<>(status,HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<Status> adminReset(QueAns queAns) {
		String url = "http://Admin-service/GetAdmin";
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("adminId", "{adminId}").encode().toUriString();
		Map<String, Integer> params = new HashMap<String,Integer>();
		params.put("adminId", queAns.getAdminId());
		Admin admin = restTemplate.getForObject(urlTemplate, Admin.class,params);
		List<Answers> allCorrectAnswers = admin.getQuestions();
		List<QuestionUnlock> givenQns=queAns.getAnswersList();
		Status status = new Status();
		if(givenQns.size()>=2) {
		int flag = checkQuestions(givenQns, allCorrectAnswers);
		if (flag == givenQns.size()) {
			admin.setAdminPassword(queAns.getNewPassword());
			restTemplate.postForObject("http://Admin-service/AddAdmin", admin, Admin.class);
			status.setMessage("password reset successfull");
			return new ResponseEntity<>(status,HttpStatus.ACCEPTED);
		}
		else {
			status.setMessage("answer for the security question does not match");
			return new ResponseEntity<>(status,HttpStatus.BAD_REQUEST);
		}
		}
		else {
			status.setMessage("please provide answers for atleast 2 security questions");
			return new ResponseEntity<>(status,HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<Status> adminRegister(Admin admin) {
		Status status = new Status();
		if(admin.getQuestions().size()<2) {
			status.setMessage("please provide atleast two security questions");
			return new ResponseEntity<>(status,HttpStatus.BAD_REQUEST);

		}
		else {
			for(int i=0;i<admin.getQuestions().size();i++) {
				admin.getQuestions().get(i).getId().setAdmin_id(admin.getAdminId());
			}
			 restTemplate.postForEntity("http://Admin-service/AddAdmin", admin, String.class);
			 status.setMessage("succesfully registered");
			 return new ResponseEntity<>(status,HttpStatus.CREATED);
		}
	}

	public ResponseEntity<Status> addQuestions(int adminId,List<Answers> answers) {
		String url = "http://Admin-service/AddQuestions";
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("adminId", "{adminId}").encode().toUriString();
		Map<String, Integer> params = new HashMap<String,Integer>();
		params.put("adminId",adminId );
		for(int i=0;i<answers.size();i++) {
			answers.get(i).getId().setAdmin_id(adminId);
		}
		restTemplate.put(urlTemplate, answers,params);
		Status status = new Status();
		status.setMessage("questions added successfully");
		return new ResponseEntity<>(status,HttpStatus.CREATED);
	}

	public ResponseEntity<Status> deleteQuestions(int adminId, List<Integer> queId) {
		String url = "http://Admin-service/DeleteQuestion";
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("adminId", "{adminId}").encode().toUriString();
		Map<String, Integer> params = new HashMap<String,Integer>();
		params.put("adminId",adminId );
		for(int i=0;i<queId.size();i++){
			HttpEntity<Integer> request = new HttpEntity<Integer>(queId.get(i));
			restTemplate.exchange(urlTemplate, HttpMethod.DELETE,request,String.class,params);
		}
		Status status = new Status();
		status.setMessage("questions deleted");
		return new ResponseEntity<>(status,HttpStatus.OK);
	}


	public Admin getAdmin(int adminId) {
		String url = "http://Admin-service/GetAdmin";
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("adminId", "{adminId}").encode().toUriString();
		Map<String, Integer> params = new HashMap<String,Integer>();
		params.put("adminId", adminId);
		return restTemplate.getForObject(urlTemplate,Admin.class,params);
	
	}


	public List<Admin> getAdmins() {
		String url = "http://Admin-service/GetAdmins";
		return restTemplate.getForObject(url,List.class);
	}
	
	public Admin getAdminauth(String adminName) {
		String url = "http://Admin-service/AdminByName";
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("adminName", "{adminName}").encode().toUriString();
		Map<String, String> params = new HashMap<String,String>();
		params.put("adminName", adminName);
		Admin admin = restTemplate.getForObject(urlTemplate, Admin.class,params);
		return admin;

	}


	public ResponseEntity<Status> deleteAdmin(int adminId) {
		String url = "http://Admin-service/DeleteAdmin";
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("adminId", "{adminId}").encode().toUriString();
		Map<String, Integer> params = new HashMap<String,Integer>();
		params.put("adminId", adminId);
		restTemplate.delete(urlTemplate, params);
		Status status = new Status();
		status.setMessage("succesfully deleted Admin");
		return new ResponseEntity<Status>(status,HttpStatus.OK);
	}


	
	
	
	//new login
	public ResponseEntity<Status> loginAdmin(Admin admin) {
		String url = "http://Admin-service/AdminLogin";
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("adminId", "{adminId}").queryParam("adminName", "{adminName}").encode().toUriString();
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("adminId", admin.getAdminId());
		params.put("adminName", admin.getAdminName());
		Admin existingAdmin = restTemplate.getForObject(urlTemplate,Admin.class,params);
		int currentStatus = existingAdmin.getAdminStatus();
		Status status = new Status();
		if(currentStatus<3) 
		{
			if(existingAdmin.getAdminPassword().equals(admin.getAdminPassword())) 
			{
				existingAdmin.setAdminStatus(0);
				//adminRepository.save(existingAdmin);
				restTemplate.postForObject("http://Admin-service/AddAdmin", existingAdmin, Admin.class);
				
				status.setMessage("correct password");
				return new ResponseEntity<>(status,HttpStatus.OK);
		}
		else {
			existingAdmin.setAdminStatus(currentStatus+1);
			restTemplate.postForObject("http://Admin-service/AddAdmin", existingAdmin, Admin.class);
			if(existingAdmin.getAdminStatus()==3) {
				status.setMessage("you have entered wrong password and your account has been locked");
				return new ResponseEntity<> (status,HttpStatus.BAD_REQUEST);
			}
			else {
				status.setMessage("wrong password try again");
			return new ResponseEntity<>(status,HttpStatus.BAD_REQUEST);
			}
			}
		}
		else {
			status.setMessage("your account has been locked please unlock to continue");
			return new ResponseEntity<>(status,HttpStatus.BAD_REQUEST);
		}
	}
	

	
	
	
	
	
	
	

}
