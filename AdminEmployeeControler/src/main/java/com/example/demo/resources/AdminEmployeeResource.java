package com.example.demo.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Admin;
import com.example.demo.model.Answers;
import com.example.demo.model.AuthenticationRequest;
import com.example.demo.model.AuthenticationResponse;
import com.example.demo.model.Employee;
import com.example.demo.model.MapKey;
import com.example.demo.model.QueAns;
import com.example.demo.model.Status;
import com.example.demo.services.AdminService;
import com.example.demo.services.EmployeeService;
import com.example.demo.services.MyUserDetailService;
import com.example.demo.utility.JwtUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
public class AdminEmployeeResource {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private AdminService adminService;
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MyUserDetailService myUserDetailsService;
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	
	
	//JWT login
	/*@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
		try {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password");
			//return new ResponseEntity<>(new Status("password does not match"),HttpStatus.BAD_REQUEST);
			
		}
		final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}*/
	
	public String CreateJwt(String username,String password) throws Exception {
		AuthenticationRequest authenticationRequest = new AuthenticationRequest(username,password);
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
			}
			catch (BadCredentialsException e) {
				throw new Exception("Incorrect username or password");
				//return new ResponseEntity<>(new Status("password does not match"),HttpStatus.BAD_REQUEST);
				
			}
			final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
			final String jwt = jwtTokenUtil.generateToken(userDetails);
			
			return jwt;
	}
	
	
	
	//get admin 
	@GetMapping("/Admin")
	@HystrixCommand(fallbackMethod = "fallbackGetAdmin")
	public Admin getAdmin(@RequestParam("adminId") int adminId) {
		return adminService.getAdmin(adminId);
	}
	public Admin fallbackGetAdmin(@RequestParam("adminId") int adminId) {
		Admin admin = new Admin("Admin API service is not working");
		return admin;
	}
	
	
	@GetMapping("/AdminByName")
	@HystrixCommand(fallbackMethod = "fallbackGetAdminByName")
	public Admin getAdminByName(@RequestHeader("Authorization") String jwt) {
		jwt = jwt.substring(7);
		String adminName = jwtTokenUtil.extractUsername(jwt);
		return adminService.getAdminauth(adminName);
	}
	
	public Admin fallbackGetAdminByName(@RequestHeader("Authorization") String jwt) {
		Admin admin = new Admin("Admin API service is not working");
		return admin;
	}
	
	//get all admins
	@GetMapping("/Admins")
	@HystrixCommand(fallbackMethod = "fallbackGetAdmins")
	public List<Admin> getAdmins(){
		return adminService.getAdmins();
	}

	public List<Admin> fallbackGetAdmins(){
		List<Admin> list =  new ArrayList<>();
		list.add(new Admin("Employee API service not working"));		
		return list;
	}
	
	@DeleteMapping("/DeleteAdmin")
	@HystrixCommand(fallbackMethod = "fallbackDeleteAdmin")
	public ResponseEntity<Status> deleteAdmin(@RequestHeader("Authorization") String jwt){
		jwt = jwt.substring(7);
		String adminName = jwtTokenUtil.extractUsername(jwt);
		return adminService.deleteAdmin(adminName);
	}
	public ResponseEntity<Status> fallbackDeleteAdmin(@RequestHeader("Authorization") String jwt){
		Status status = new Status("Admin API service not working");
		return new ResponseEntity<Status>(status,HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	//admin login
	/*@PostMapping("/Admin/Login")
	@HystrixCommand(fallbackMethod = "fallbackAdminLogin")
	public ResponseEntity<Status> adminLogin(@RequestBody Admin admin) {
		return adminService.adminLogin(admin);
	}*/
	
	
	//unloack account
	@PutMapping("/Admin/Unlock")
	@HystrixCommand(fallbackMethod = "fallbackAdminUnlock")
	public ResponseEntity<Status> adminUnlock(@RequestBody QueAns queAns ,@RequestParam("adminName") String adminName){
		Admin admin = adminService.getAdminauth(adminName);
		queAns.setAdminId(admin.getAdminId());
		return adminService.adminUnlock(queAns);
	}
	public ResponseEntity<Status> fallbackAdminUnlock(@RequestBody QueAns queAns ,@RequestParam("adminName") String adminName){
		Status status = new Status("Employee API service not working");
		return new ResponseEntity<Status>(status,HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	//reset password
	@PutMapping("/Admin/Reset")
	@HystrixCommand(fallbackMethod = "fallbackAdminReset")
	public ResponseEntity<Status> adminPassReset(@RequestBody QueAns queAns,@RequestParam("adminName") String adminName){
		Admin admin = adminService.getAdminauth(adminName);
		queAns.setAdminId(admin.getAdminId());
		return adminService.adminReset(queAns);
	}
	public ResponseEntity<Status> fallbackAdminReset(@RequestBody QueAns queAns ,@RequestParam("adminName") String adminName){
		Status status = new Status("Employee API service not working");
		return new ResponseEntity<Status>(status,HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	//admin register
	@PostMapping("/Admin/Register")
	@HystrixCommand(fallbackMethod = "fallbackAdminRegister")
	public ResponseEntity<Status> adminRegister(@RequestBody Admin admin) {
		return adminService.adminRegister(admin);
	}
	public ResponseEntity<Status> fallbackAdminRegister(@RequestBody Admin admin) {
		Status status = new Status("Employee API service not working");
		return new ResponseEntity<Status>(status,HttpStatus.SERVICE_UNAVAILABLE);	}
	
	//add security questions
	@PostMapping("/Admin/AddQuestions")
	@HystrixCommand(fallbackMethod = "fallbackAddQuestions")
	public ResponseEntity<Status> addQuestions(@RequestHeader("Authorization") String jwt,@RequestBody List<Answers> answers){
		jwt = jwt.substring(7);
		String adminName = jwtTokenUtil.extractUsername(jwt);
		Admin admin = adminService.getAdminauth(adminName);
		return adminService.addQuestions(admin.getAdminId(), answers);
	}
	public ResponseEntity<Status> fallbackAddQuestions(@RequestHeader("Authorization") String jwt,@RequestBody List<Answers> answers){
		Status status = new Status("Employee API service not working");
		return new ResponseEntity<Status>(status,HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	// delete security questions
	@DeleteMapping("/Admin/DeleteQuestions")
	@HystrixCommand(fallbackMethod = "fallbackDeleteQuestions")
	public ResponseEntity<Status> deleteQuestions(@RequestHeader("Authorization") String jwt,@RequestBody List<Integer> queId)
	{
		jwt = jwt.substring(7);
		String adminName = jwtTokenUtil.extractUsername(jwt);
		Admin admin = adminService.getAdminauth(adminName);
		return adminService.deleteQuestions(admin.getAdminId(),queId);
	}
	public ResponseEntity<Status> fallbackDeleteQuestions(@RequestHeader("Authorization") String jwt,@RequestBody List<Integer> queId){
		Status status = new Status("Employee API service not working");
		return new ResponseEntity<Status>(status,HttpStatus.SERVICE_UNAVAILABLE);
		}
	
	
	//get admin employees
	@GetMapping("/Admin/GetEmployees")
	@HystrixCommand(fallbackMethod = "fallbackGetEmployees")
	public List<Employee> getEmployees(@RequestHeader("Authorization") String jwt)
	{
		jwt = jwt.substring(7);
		String adminName = jwtTokenUtil.extractUsername(jwt);
		Admin admin = adminService.getAdminauth(adminName);
		return employeeService.getEmployees(admin.getAdminId());
	}
	
	public List<Employee> fallbackGetEmployees(@RequestHeader("Authorization") String jwt){
		List<Employee> list =  new ArrayList<>();
		list.add(new Employee("Employee API service not working"));		
		return list;
	}
	
	// add employees
	@PostMapping("/Admin/AddEmployees")
	@HystrixCommand(fallbackMethod = "fallbackAddEmployees")
	public List<Employee> addEmployees(@RequestHeader("Authorization") String jwt,@RequestBody List<Employee> emps){
		jwt = jwt.substring(7);
		String adminName = jwtTokenUtil.extractUsername(jwt);
		Admin admin = adminService.getAdminauth(adminName);
		return employeeService.addEmployess(admin.getAdminId(),emps);
	}
	public List<Employee> fallbackAddEmployees(@RequestHeader("Authorization") String jwt,@RequestBody List<Employee> emps){
		List<Employee> list =  new ArrayList<>();
		list.add(new Employee("Employee API service not working"));		
		return list;
	}
	
	
	
	//update employee details
	@PutMapping("/Admin/UpdateEmployee")
	@HystrixCommand(fallbackMethod = "fallbackUpdateEmployees")
	public ResponseEntity<Status> updateEmployees(@RequestHeader("Authorization") String jwt,@RequestBody Employee employee){
		jwt = jwt.substring(7);
		String adminName = jwtTokenUtil.extractUsername(jwt);
		Admin admin = adminService.getAdminauth(adminName);
		return employeeService.updateEmployeess(admin.getAdminId(),employee);
	}
	public ResponseEntity<Status> fallbackUpdateEmployees(@RequestHeader("Authorization") String jwt,@RequestBody Employee employee){
		Status status = new Status("Employee API service not working");
		return new ResponseEntity<Status>(status,HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	
	
	//delete employee
	@DeleteMapping("/Admin/DeleteEmployee")
	@HystrixCommand(fallbackMethod = "fallbackDeleteEmployee")
	public ResponseEntity<Status> deleteEmployee(@RequestParam("employeeId") int employeeId){
		return employeeService.deleteEmloyee(employeeId);
	}
	public ResponseEntity<Status> fallbackDeleteEmployee(@RequestParam("employeeId") int employeeId){
		Status status = new Status("Employee API service not working");
		return new ResponseEntity<Status>(status,HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	//login with id and name
	@PostMapping("/Admin/Login")
	@HystrixCommand(fallbackMethod = "fallbackAdminLogin")
	public ResponseEntity<Status> loginAdmin(@RequestBody Admin admin) throws Exception{
		ResponseEntity<Status> responseEntity = adminService.loginAdmin(admin);
		HttpStatus httpStatus = responseEntity.getStatusCode();
		if (httpStatus==HttpStatus.OK) {
			Status status = new Status();
			String jwt = CreateJwt(admin.getAdminName(), admin.getAdminPassword());
			status.setMessage(jwt);
			return new ResponseEntity<>(status,HttpStatus.OK);
		}
		Status status = new Status();
		status.setMessage(responseEntity.getBody().getMessage());
		return new ResponseEntity<Status>(status,httpStatus);
	}
	
	
	
	//fallback methods
	public ResponseEntity<Status> fallbackAdminLogin(@RequestBody Admin admin) {
		Status status = new Status("Admin API service not working");
		return new ResponseEntity<Status>(status,HttpStatus.SERVICE_UNAVAILABLE);
	}
	


	
	

	
	
	

	
	
	


	
	
	
	
	

	
}

	
