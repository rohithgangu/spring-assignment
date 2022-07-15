package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "employee_data")
public class Employee {
@Id
@GeneratedValue
@Column(name = "employee_id")
private int employeeId;
@Column(name = "employee_name")
private String employeeName;
@Column(name = "admin_id")
private int adminId;
public int getEmployeeId() {
	return employeeId;
}
public void setEmployeeId(int employeeId) {
	this.employeeId = employeeId;
}
public String getEmployeeName() {
	return employeeName;
}
public void setEmployeeName(String employeeName) {
	this.employeeName = employeeName;
}
public int getAdminId() {
	return adminId;
}
public void setAdminId(int adminId) {
	this.adminId = adminId;
}
public Employee(int employeeId, String employeeName, int adminId) {
	super();
	this.employeeId = employeeId;
	this.employeeName = employeeName;
	this.adminId = adminId;
}
public Employee() {
	super();
}




}
