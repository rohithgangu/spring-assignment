package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.models.Admin;
import com.example.demo.models.AdminQuestionAnswers;
import com.example.demo.models.QueAns;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

	@Query("select a.adminPassword from Admin a where a.adminName=?1")
	String getAdminPassword(String admin_Name);

	/*@Query("select q.question,a.answer from AdminQuestionAnswers a join Questions q on a.id.question_id=q.queId join Admin ad on a.id.admin_id=ad.adminId where ad.adminId=?1")
	List<QueAns> getAdminQuestions(int adminId);*/
	
	
	Admin findByAdminName(String adminName);

	Admin getAdminByAdminName(String adminName);
	
	Admin deleteByAdminName(String adminName);
}
