package com.example.studentapp.controller;

import com.example.studentapp.model.Student;
import com.example.studentapp.service.OtpService;
import com.example.studentapp.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private OtpService otpService;

    // ----- Teacher Service Added -----


    // ---- Login page ----
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // login.html (existing)
    }

    // ---- Normal Login ----
    @PostMapping("/login")
    public String processLogin(
            @RequestParam("username") String username,
            @RequestParam("id") long id,
            Model model) {

        if (username == null || username.trim().isEmpty()) {
            return "redirect:/login?error=empty";
        }

        String uname = username.trim().toLowerCase().replaceAll("\\s+", "");

        if (uname.equals("shincontrol") && id == 404) {
            return "redirect:/students"; // Admin dashboard
        }

        // --------- Teacher Check Added ----------

        // --------- End Teacher Check ----------

        Student student = studentService.findByNameAndId(uname, id);

        if (student != null) {
            return "redirect:/students"; // Student dashboard
        }

        return "redirect:/login?error=true"; // Invalid login
    }

    // ------------------ Forgot User ID OTP Flow ------------------

    // Step 1: Request OTP by Email
    @PostMapping("/send-otp")
    @ResponseBody
    public Map<String, Object> sendOtp(@RequestParam String email) {
        Map<String, Object> response = new HashMap<>();

        // Check if email exists in Student list
        Student student = studentService.findByEmail(email);
        if (student == null) {
            response.put("success", false);
            response.put("msg", "Email does not exist in our records");
            return response;
        }

        // Generate OTP
        String otp = otpService.generateOtp();

        // Send OTP email
        otpService.sendOtpEmail(email, otp);

        response.put("success", true);
        response.put("msg", "OTP sent to " + email);
        return response;
    }

    // Step 2: Verify OTP
    @PostMapping("/verify-otp")
    @ResponseBody
    public Map<String, Object> verifyOtp(@RequestParam("otp") String otp) {
        Map<String, Object> response = new HashMap<>();

        Boolean valid = otpService.verifyOtp(otp);
        if (!valid) {
            response.put("valid", false);
            response.put("msg", "Invalid or expired OTP");
            return response;
        }

        // Get email by OTP
        String email = otpService.getEmailByOtp(otp);
        Student student = studentService.findByEmail(email);

        response.put("valid", true);
        response.put("username", student.getName());
        response.put("userId", student.getId());

        return response;
    }
}