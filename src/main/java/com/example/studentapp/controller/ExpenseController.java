package com.example.studentapp.controller;

import com.example.studentapp.model.Student;
import com.example.studentapp.model.Expense;
import com.example.studentapp.service.ExpenseService;
import com.example.studentapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/expenses/list/{studentId}")
    public String listExpensesByStudent(@PathVariable Long studentId, Model model) {

        Student student = studentService.getStudentById(studentId);

        model.addAttribute("student", student);
        model.addAttribute("expenses", expenseService.getExpensesByStudentId(studentId));

        return "expense-list";
    }

    @GetMapping("/expenses/add/{studentId}")
    public String addExpenseForStudent(@PathVariable Long studentId, Model model) {
        Expense expense = new Expense();
        Student student = studentService.getStudentById(studentId);

        expense.setStudent(student);  // Auto-link student

        model.addAttribute("expense", expense);
        return "add-expense";
    }

    @PostMapping("/expenses/save")
    public String saveExpense(@ModelAttribute Expense expense) {

        Long studentId = expense.getStudent().getId();

        Student student = studentService.getStudentById(studentId);
        expense.setStudent(student);

        expenseService.saveExpense(expense);

        return "redirect:/expenses/list/" + studentId;   // ✅ Correct URL
    }


    @GetMapping("/expenses/edit/{id}")
    public String editExpenseForm(@PathVariable Long id, Model model) {
        Expense expense = expenseService.getExpenseById(id);
        model.addAttribute("expense", expense);
        return "edit-expense";   // <-- Yahi naam html file ka hona chahiye
    }


    @PostMapping("/expenses/update")
    public String updateExpense(@ModelAttribute Expense expense) {

        Long studentId = expense.getStudent().getId();  // link back to list

        expenseService.saveExpense(expense);            // save updated data

        return "redirect:/expenses/list/" + studentId;
    }

    @GetMapping("/expenses/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {

        Expense expense = expenseService.getExpenseById(id);
        Long studentId = expense.getStudent().getId();

        expenseService.deleteExpense(id);

        return "redirect:/expenses/list/" + studentId;
    }
}