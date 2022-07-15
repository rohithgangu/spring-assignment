package com.example.demo.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.model.Employee;
import com.example.demo.model.Status;

@Service
public class EmployeeService {
	
	@Autowired
	private RestTemplate restTemplate;

	public List<Employee> getEmployees(int adminId) {
		//String url = "http://localhost:8084/admin/employees";
		String url = "http://Employee-Service/admin/employees";
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("adminId", "{adminId}").encode().toUriString();
		Map<String, Integer> params = new HashMap<String,Integer>();
		params.put("adminId",adminId );
		return restTemplate.getForObject(urlTemplate,List.class, params);
	}

	public List<Employee> addEmployess(int adminId, List<Employee> emps) {
		String url = "http://Employee-Service/admin/addEmployees";
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("adminId", "{adminId}").encode().toUriString();
		Map<String, Integer> params = new HashMap<String,Integer>();
		params.put("adminId",adminId );
		for(int i =0;i<emps.size();i++) {
			emps.get(i).setAdminId(adminId);
		}
		return restTemplate.postForObject(urlTemplate,emps,List.class, params);
	}

	public ResponseEntity<Status> updateEmployeess(int adminId, Employee employee) {
		String url = "http://Employee-Service/employee";
		String url2 = "http://Employee-Service/admin/updateEmployee";
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("employeeId", "{employeeId}").encode().toUriString();
		Map<String, Integer> params = new HashMap<String,Integer>();
		employee.setAdminId(adminId);
		params.put("employeeId",employee.getEmployeeId() );
		Employee currEmployee = restTemplate.getForObject(urlTemplate,Employee.class,params);
		currEmployee.setEmployeeName(employee.getEmployeeName());
		currEmployee.setEmployeeId(employee.getEmployeeId());
		restTemplate.put(url2, currEmployee);
		restTemplate.getForObject(urlTemplate, Employee.class,params);
		Status status = new Status();
		status.setMessage("employee successfully updated");
		return new ResponseEntity<>(status,HttpStatus.OK);
	}

	public ResponseEntity<Status> deleteEmloyee(int employeeId) {
		String url = "http://Employee-Service/admin/deleteEmployee";
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("employeeId", "{employeeId}").encode().toUriString();
		Map<String, Integer> params = new HashMap<String,Integer>();
		params.put("employeeId",employeeId );
		restTemplate.delete(urlTemplate, params);
		Status status = new Status();
		status.setMessage("employee successfully deleated");
		return new ResponseEntity<>(status,HttpStatus.OK);
	}

}
