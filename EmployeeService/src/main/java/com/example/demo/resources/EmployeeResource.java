package com.example.demo.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.demo.models.Employee;
import com.example.demo.service.EmployeeService;
@RestController
public class EmployeeResource {

	@Autowired
	EmployeeService employeeService;
	
	
	@GetMapping("/employee")
	public Employee getEmployee(@RequestParam("employeeId")int employeeId) {
		return employeeService.getEmployee(employeeId);
	}
	@GetMapping("/admin/employees")
	public List<Employee> getEmpByAdmin(@RequestParam("adminId") int adminId)
	{
		return employeeService.getEmpByAdmin(adminId);
	}
	
	
	@PostMapping("/admin/addEmployee")
	public Employee addEmployee(@RequestBody Employee employee) {
		return employeeService.addemp(employee);
	}
	@PostMapping("/admin/addEmployees")
	public List<Employee> addEmployees(@RequestBody List<Employee> employees)
	{
		return employeeService.addEmployees(employees);
	}
	
	
	@PutMapping("/admin/updateEmployee")
	public Employee updatEmployee(@RequestBody Employee employee) {
		return employeeService.updateEmployee(employee);
	}
	
	@DeleteMapping("/admin/deleteEmployee")
	public String deleteEmployee(@RequestParam("employeeId") int employeeId)
	{
		return employeeService.deleteEmployee(employeeId);
	}
	
}
