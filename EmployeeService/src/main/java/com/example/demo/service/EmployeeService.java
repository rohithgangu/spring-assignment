package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	EmployeeRepository employeeRepository;
	
	public Employee addemp(Employee emp) {
		return employeeRepository.save(emp);
	}
	public List<Employee> addEmployees(List<Employee> emp){
		return employeeRepository.saveAll(emp);
	}
	public Employee getEmployee(int employeeId) {
		return employeeRepository.findById(employeeId).orElse(null);
	}
	public List<Employee> getEmpByAdmin(int admin_id){
		return employeeRepository.findAllByAdminId(admin_id);
	}
	public String deleteEmployee(int employeeId) {
		employeeRepository.deleteById(employeeId);
		return "Employee deleted";
	}
	/*public Employee updateEmployee(Employee newEmployee) {
		Employee currentEmployee = employeeRepository.findById(newEmployee.getEmployeeId()).orElse(null);
		currentEmployee.setEmployeeName(newEmployee.getEmployeeName());
		currentEmployee.setEmployeeId(newEmployee.getEmployeeId());
		return employeeRepository.save(currentEmployee);
	}*/
	
	public Employee updateEmployee(Employee newEmployee) {
		return employeeRepository.save(newEmployee);
	}
}
