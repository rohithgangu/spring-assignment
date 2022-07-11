package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.models.AdminQuestionAnswers;
import com.example.demo.models.QuestionAdminPK;

public interface AdminQuestionRepository extends JpaRepository<AdminQuestionAnswers, QuestionAdminPK>{

	@Query("select a.id.question_id,a.answer from AdminQuestionAnswers a where a.id.admin_id=?1")
	List<AdminQuestionAnswers> findAll(int adminId);


}
