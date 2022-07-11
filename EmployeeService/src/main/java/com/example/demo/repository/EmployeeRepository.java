package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

	List<Employee> findAllByAdminId(int admin_id);

}
