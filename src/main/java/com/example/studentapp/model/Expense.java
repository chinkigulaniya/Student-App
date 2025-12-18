package com.example.studentapp.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Data
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private double amount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String category;
    private  String note;

    @ManyToOne
    @JoinColumn(name ="student_id")
    private  Student student;
}