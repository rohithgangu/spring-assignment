package com.example.demo.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Admin;
import com.example.demo.model.Answers;
import com.example.demo.model.Employee;
import com.example.demo.model.MapKey;
import com.example.demo.model.QueAns;
import com.example.demo.model.Status;
import com.example.demo.services.AdminService;
import com.example.demo.services.EmployeeService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class AdminEmployeeResource {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private AdminService adminService;
	@Autowired
	private EmployeeService employeeService;
	
	
	
	@PostMapping("/Admin/Login")
	@HystrixCommand(fallbackMethod = "fallbackAdminLogin")
	public ResponseEntity<Status> adminLogin(@RequestBody Admin admin) {
		return adminService.adminLogin(admin);
	}
	
	@PutMapping("/Admin/Unlock")
	public ResponseEntity<String> adminUnlock(@RequestBody QueAns queAns ,@RequestParam("adminId") int adminId){
		queAns.setAdminId(adminId);
		return adminService.adminUnlock(queAns);
	}
	
	@PutMapping("/Admin/Reset")
	public ResponseEntity<String> adminPassReset(@RequestBody QueAns queAns,@RequestParam("adminId") int adminId){
		queAns.setAdminId(adminId);
		return adminService.adminReset(queAns);
	}
	
	@PostMapping("/Admin/Register")
	public ResponseEntity<String> adminRegister(@RequestBody Admin admin) {
		return adminService.adminRegister(admin);
	}
	
	@PostMapping("/Admin/AddQuestions")
	public ResponseEntity<String> addQuestions(@RequestParam("adminId") int adminId,@RequestBody List<Answers> answers){
		return adminService.addQuestions(adminId, answers);
	}
	
	@DeleteMapping("/Admin/DeleteQuestions")
	public ResponseEntity<String> deleteQuestions(@RequestParam("adminId") int adminId,@RequestBody List<Integer> queId)
	{
		return adminService.deleteQuestions(adminId,queId);
	}
	
	//employees
	@GetMapping("/Admin/GetEmployees")
	public List<Employee> getEmployees(@RequestParam("adminId") int adminId)
	{
		return employeeService.getEmployees(adminId);
	}
	
	@PostMapping("/Admin/AddEmployees")
	public List<Employee> addEmployees(@RequestParam("adminId") int adminId,@RequestBody List<Employee> emps){
		return employeeService.addEmployess(adminId,emps);
	}
	@PutMapping("/Admin/UpdateEmployee")
	public Employee updateEmployees(@RequestParam("adminId") int adminId,@RequestBody Employee employee){
		return employeeService.updateEmployeess(adminId,employee);
	}
	
	@DeleteMapping("/Admin/DeleteEmployee")
	public ResponseEntity<String> deleteEmployee(@RequestParam("employeeId") int employeeId){
		return employeeService.deleteEmloyee(employeeId);
	}
	
	
	//fallback methods
	public ResponseEntity<Status> fallbackAdminLogin(@RequestBody Admin admin) {
		Status status = new Status();
		status.setMessage("server problem please try later");
		return new ResponseEntity<Status>(status,HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	/*@GetMapping("/Admins")
	public List<Admin> getAdmins(){
		return adminService.getAdmins();
	}
	
	@GetMapping("/AdminById/{adminId}")
	public Admin getAdminById(@PathVariable("adminId")int adminId) {
		return adminService.GetAdminById(adminId);
	}
	
	
	
	
	
	@PostMapping("/Admin/Register")
	public ResponseEntity<String> adminRegister(@RequestBody Admin admin) {
		return adminService.adminRegister(admin);
	}
	
	
	@PutMapping("/Admin/{adminId}/AddSecQue")
	public Admin addSecQue(@PathVariable("adminId") int adminId,@RequestBody List<Answers> answers)
	{
		return adminService.addSecQue(adminId,answers);
	}
	
	@PutMapping("/Admin/{adminId}/UnlockAccount")
	public ResponseEntity<String> unlockAccount(@PathVariable("adminId") int adminId,@RequestBody QueAns queAns){
		queAns.setAdminId(adminId);
		return adminService.unlockAdmin(queAns);
	}
	
	@PutMapping("/Admin/{adminId}/ResetPassword")
	public ResponseEntity<String> ResetPassword(@PathVariable("adminId") int adminId,@RequestBody QueAns queAns) {
		queAns.setAdminId(adminId);
		return adminService.resetPassword(queAns);
	}
	
	@DeleteMapping("/Admin/{adminId}/DeleteQuestions")
	public ResponseEntity<String> deleteQuestion(@PathVariable("adminId") int adminId,@RequestBody List<Integer> queId)
	{
		return adminService.deleteQuestions(adminId,queId);
	}
	
	
	
	//employee controller
	@GetMapping("/Admin/{adminId}/employees")
	public List<Employee> getAdminEmployees(@PathVariable("adminId")int adminId){
		return employeeService.getAdminEmployees(adminId);
	}*/
}