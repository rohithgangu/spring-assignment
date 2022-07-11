package com.example.demo.model;




public class Employee {

private int employeeId;
private String employeeName;
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
