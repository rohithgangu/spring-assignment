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
	
	//get admin
	@GetMapping("/Admin")
	public Admin getAdmin(@RequestParam("adminId") int adminId) {
		return adminService.getAdmin(adminId);
	}
	//get all admins
	@GetMapping("/Admins")
	public List<Admin> getAdmins(){
		return adminService.getAdmins();
	}
	
	@DeleteMapping("/DeleteAdmin")
	public ResponseEntity<Status> deleteAdmin(@RequestParam("adminId") int adminId){
		return adminService.deleteAdmin(adminId);
	}
	
	//admin login
	@PostMapping("/Admin/Login")
	@HystrixCommand(fallbackMethod = "fallbackAdminLogin")
	public ResponseEntity<Status> adminLogin(@RequestBody Admin admin) {
		return adminService.adminLogin(admin);
	}
	
	
	//unloack account
	@PutMapping("/Admin/Unlock")
	public ResponseEntity<Status> adminUnlock(@RequestBody QueAns queAns ,@RequestParam("adminId") int adminId){
		queAns.setAdminId(adminId);
		return adminService.adminUnlock(queAns);
	}
	
	
	//reset password
	@PutMapping("/Admin/Reset")
	public ResponseEntity<Status> adminPassReset(@RequestBody QueAns queAns,@RequestParam("adminId") int adminId){
		queAns.setAdminId(adminId);
		return adminService.adminReset(queAns);
	}
	
	
	//admin register
	@PostMapping("/Admin/Register")
	public ResponseEntity<Status> adminRegister(@RequestBody Admin admin) {
		return adminService.adminRegister(admin);
	}
	
	//add security questions
	@PostMapping("/Admin/AddQuestions")
	public ResponseEntity<Status> addQuestions(@RequestParam("adminId") int adminId,@RequestBody List<Answers> answers){
		return adminService.addQuestions(adminId, answers);
	}
	
	
	// delete security questions
	@DeleteMapping("/Admin/DeleteQuestions")
	public ResponseEntity<Status> deleteQuestions(@RequestParam("adminId") int adminId,@RequestBody List<Integer> queId)
	{
		return adminService.deleteQuestions(adminId,queId);
	}
	
	//get admin employees
	@GetMapping("/Admin/GetEmployees")
	public List<Employee> getEmployees(@RequestParam("adminId") int adminId)
	{
		return employeeService.getEmployees(adminId);
	}
	
	// add employees
	@PostMapping("/Admin/AddEmployees")
	public List<Employee> addEmployees(@RequestParam("adminId") int adminId,@RequestBody List<Employee> emps){
		return employeeService.addEmployess(adminId,emps);
	}
	
	//update employee details
	@PutMapping("/Admin/UpdateEmployee")
	public Employee updateEmployees(@RequestParam("adminId") int adminId,@RequestBody Employee employee){
		return employeeService.updateEmployeess(adminId,employee);
	}
	
	//delete employee
	@DeleteMapping("/Admin/DeleteEmployee")
	public ResponseEntity<Status> deleteEmployee(@RequestParam("employeeId") int employeeId){
		return employeeService.deleteEmloyee(employeeId);
	}
	
	
	//fallback methods
	public ResponseEntity<Status> fallbackAdminLogin(@RequestBody Admin admin) {
		Status status = new Status();
		status.setMessage("server problem please try later");
		return new ResponseEntity<Status>(status,HttpStatus.BAD_REQUEST);
	}
	
	
	
	
}