package com.example.studentapp.service;

import com.example.studentapp.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private StudentService studentService;

    @Autowired
    private JavaMailSender mailSender;

    // Store OTP details
    private String savedEmail;
    private String savedOtp;
    private LocalDateTime expiryTime;

    // Generate OTP
    public String generateOtp() {
        savedOtp = String.valueOf(100000 + new Random().nextInt(900000));
        expiryTime = LocalDateTime.now().plusMinutes(5);
        return savedOtp;
    }

    // Send OTP Email
    public void sendOtpEmail(String email, String otp) {

        // Save email for verification
        savedEmail = email;

        // Send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp);

        mailSender.send(message);
    }

    // Verify OTP
    public boolean verifyOtp(String userEnteredOtp) {

        if (savedOtp == null) {
            return false;  // No OTP generated
        }

        if (!savedOtp.equals(userEnteredOtp)) {
            return false;  // Wrong OTP
        }

        if (LocalDateTime.now().isAfter(expiryTime)) {
            return false;  // Expired
        }

        return true;
    }

    // Return email for given OTP
    public String getEmailByOtp(String otp) {
        if (savedOtp != null && savedOtp.equals(otp)) {
            return savedEmail;
        }
        return null;
    }
}