package com.example.studentapp.service;

import com.example.studentapp.model.Expense;
import com.example.studentapp.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    // Save expense
    public void saveExpense(Expense expense) {
        expenseRepository.save(expense);
    }

    // Update expense
    public void updateExpense(Expense expense) {
        expenseRepository.save(expense);
    }

    // Delete expense
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    // Get expense by id
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
    }

    // List all expenses
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    // ⭐ Get expenses by student id
    public List<Expense> getExpensesByStudentId(Long studentId) {
        return expenseRepository.findByStudentId(studentId);
    }
}