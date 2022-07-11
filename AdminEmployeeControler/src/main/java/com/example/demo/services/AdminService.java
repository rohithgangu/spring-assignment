package com.example.demo.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.model.Admin;
import com.example.demo.model.Answers;
import com.example.demo.model.QueAns;
import com.example.demo.model.QuestionUnlock;
import com.example.demo.model.Status;

import org.springframework.http.*;
@Service
public class AdminService {

	@Autowired
	private RestTemplate restTemplate;

	/*public ResponseEntity<String> adminLogin(Admin admin){
		String url = "http://Admin-service/GetAdmin";
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("adminId", "{adminId}").encode().toUriString();
		Map<String, Integer> params = new HashMap<String,Integer>();
		params.put("adminId", admin.getAdminId());
		Admin existingAdmin = restTemplate.getForObject(urlTemplate,Admin.class,params);
		int currentStatus = existingAdmin.getAdminStatus();
		if(currentStatus<3) 
		{
			
			if(existingAdmin.getAdminPassword().equals(admin.getAdminPassword())) 
			{
				existingAdmin.setAdminStatus(0);
				//adminRepository.save(existingAdmin);
				restTemplate.postForObject("http://Admin-service/AddAdmin", existingAdmin, Admin.class);
				return new ResponseEntity<>("correct password",HttpStatus.ACCEPTED);
		}
		else {
			existingAdmin.setAdminStatus(currentStatus+1);
			restTemplate.postForObject("http://Admin-service/AddAdmin", existingAdmin, Admin.class);
			if(existingAdmin.getAdminStatus()==3) {
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
	}*/
	
	
	public ResponseEntity<Status> adminLogin(Admin admin){
		String url = "http://Admin-service/GetAdmin";
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("adminId", "{adminId}").encode().toUriString();
		Map<String, Integer> params = new HashMap<String,Integer>();
		params.put("adminId", admin.getAdminId());
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
				return new ResponseEntity<>(status,HttpStatus.ACCEPTED);
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

	public ResponseEntity<String> adminUnlock(QueAns queAns) {
		String url = "http://Admin-service/GetAdmin";
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("adminId", "{adminId}").encode().toUriString();
		Map<String, Integer> params = new HashMap<String,Integer>();
		params.put("adminId", queAns.getAdminId());
		//Admin admin = adminRepository.findById(queAns.getAdminId()).orElse(null);
		Admin admin = restTemplate.getForObject(urlTemplate, Admin.class,params);
		if(admin.getAdminStatus()>=3) {
		List<Answers> allCorrectAnswers = admin.getQuestions();
		List<QuestionUnlock> givenQns=queAns.getAnswersList();
		if(givenQns.size()>=2) {
		int flag = checkQuestions(givenQns, allCorrectAnswers);
		if (flag == givenQns.size()) {
			admin.setAdminStatus(0);
			//adminRepository.save(admin);
			restTemplate.postForObject("http://Admin-service/AddAdmin", admin, Admin.class);
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

	public ResponseEntity<String> adminReset(QueAns queAns) {
		String url = "http://Admin-service/GetAdmin";
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("adminId", "{adminId}").encode().toUriString();
		Map<String, Integer> params = new HashMap<String,Integer>();
		params.put("adminId", queAns.getAdminId());
		Admin admin = restTemplate.getForObject(urlTemplate, Admin.class,params);
		List<Answers> allCorrectAnswers = admin.getQuestions();
		List<QuestionUnlock> givenQns=queAns.getAnswersList();
		if(givenQns.size()>=2) {
		int flag = checkQuestions(givenQns, allCorrectAnswers);
		if (flag == givenQns.size()) {
			admin.setAdminPassword(queAns.getNewPassword());
			restTemplate.postForObject("http://Admin-service/AddAdmin", admin, Admin.class);
			return new ResponseEntity<>("password reset successfull",HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>("answer for the security question does not match",HttpStatus.BAD_REQUEST);
		}
		}
		else {
			return new ResponseEntity<>("please provide answers for atleast 2 security questions",HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<String> adminRegister(Admin admin) {
		if(admin.getQuestions().size()<2) {
			return new ResponseEntity<String>("please provide atleast two security questions",HttpStatus.BAD_REQUEST);

		}
		else {
			 restTemplate.postForEntity("http://Admin-service/AddAdmin", admin, String.class);
			 return new ResponseEntity<String>("succesfully registered",HttpStatus.ACCEPTED);
		}
	}

	public ResponseEntity<String> addQuestions(int adminId,List<Answers> answers) {
		String url = "http://Admin-service/AddQuestions";
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("adminId", "{adminId}").encode().toUriString();
		Map<String, Integer> params = new HashMap<String,Integer>();
		params.put("adminId",adminId );
		restTemplate.put(urlTemplate, answers,params);
		return new ResponseEntity<String>("questions added successfully",HttpStatus.ACCEPTED);
	}

	public ResponseEntity<String> deleteQuestions(int adminId, List<Integer> queId) {
		String url = "http://Admin-service/DeleteQuestion";
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("adminId", "{adminId}").encode().toUriString();
		Map<String, Integer> params = new HashMap<String,Integer>();
		params.put("adminId",adminId );
		for(int i=0;i<queId.size();i++){
			HttpEntity<Integer> request = new HttpEntity<Integer>(queId.get(i));
			restTemplate.exchange(urlTemplate, HttpMethod.DELETE,request,String.class,params);
		}
		return new ResponseEntity<String>("questions deleted",HttpStatus.ACCEPTED);
	}
	
	/*public ResponseEntity<String> adminLogin(Admin admin) {
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8083/AdminLogin",admin,String.class);
		//return response;
		
		return new ResponseEntity<String>(response.getBody(),response.getStatusCode());  
	}

	public ResponseEntity<String> adminRegister(Admin admin) {
		return  restTemplate.postForEntity("http://localhost:8083/AddAdmin", admin, String.class);
	
	}

	public List<Admin> getAdmins() {
		return restTemplate.getForObject("http://localhost:8083/Admin",List.class);
	}

	public Admin GetAdminById(int adminId) {
		return restTemplate.getForObject("http://localhost:8083/AdminById/"+adminId, Admin.class);
	}

	public Admin addSecQue(int adminId,List<Answers> answers) {
		restTemplate.put("http://localhost:8083/Admin/"+adminId+"/AddQuestions",answers);
		return GetAdminById(adminId);
	}

	
	public ResponseEntity<String> unlockAdmin(QueAns queAns) {
		String url= "http://localhost:8083/Admin/"+queAns.getAdminId()+"/UnlockAccount";
		HttpEntity<QueAns> request = new HttpEntity<>(queAns);
		return restTemplate.exchange(url, HttpMethod.PUT,request,String.class);
	}

	
	public ResponseEntity<String> resetPassword(QueAns queAns){
		String url = "http://localhost:8083/Admin/"+ queAns.getAdminId()+"/ResetPassword";
		HttpEntity<QueAns> request = new HttpEntity<>(queAns);
		return restTemplate.exchange(url, HttpMethod.PUT,request,String.class);
		

	}

	public ResponseEntity<String> deleteQuestions(int adminId, List<Integer> queId) {
		String url = "http://localhost:8083/Admin/"+adminId+"/DeleteQuestions";
		HttpEntity<List<Integer>> request = new HttpEntity<List<Integer>>(queId);
		return restTemplate.exchange(url, HttpMethod.DELETE,request,String.class);
	}
	*/
	
	
	//new mehtods
	
	
	
	
	
	

}
