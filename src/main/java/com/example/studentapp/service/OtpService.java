package com.example.studentapp.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    private Map<String, String> otpStore = new HashMap<>();

    // Generate OTP
    public String generateOtp(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStore.put(email, otp);
        return otp;
    }

    // Validate OTP
    public boolean validateOtp(String email, String otp) {
        return otpStore.containsKey(email) && otpStore.get(email).equals(otp);
    }
}