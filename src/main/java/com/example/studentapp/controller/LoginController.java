package com.example.studentapp.controller;

import com.example.studentapp.model.Student;
import com.example.studentapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private StudentService studentService;

    // ---- Login page ----
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";   // login.html
    }

    // ---- Login submit ----
    @PostMapping("/login")
    public String processLogin(
            @RequestParam("username") String username,
            @RequestParam("id") long id,
            Model model) {

        // ---------- EMPTY CHECK ----------
        if (username == null || username.trim().isEmpty()) {
            return "redirect:/login?error=empty";
        }

        // 🔥 NEW: Normalize — remove spaces + lowercase
        String uname = username.trim().toLowerCase().replaceAll("\\s+", "");

        // ---------- DEFAULT ADMIN LOGIN ----------
        if (uname.equals("admin") && id == 12345) {
            return "redirect:/students";   // Admin dashboard
        }

        // ---------- STUDENT LOGIN ----------
        Student student = studentService.findByNameAndId(uname, id);

        if (student != null) {
            return "redirect:/students";    // Student dashboard
        }

        // ---------- INVALID LOGIN ----------
        return "redirect:/login?error=true";
    }
}