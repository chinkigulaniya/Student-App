
package com.example.studentapp.repository;

import com.example.studentapp.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByStudentId(Long studentId);
}