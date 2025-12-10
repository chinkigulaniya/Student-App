package com.example.studentapp.service;

import com.example.studentapp.model.Student;
import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();

    Student getStudentById(Long id);

    Student save(Student student);

    void deleteStudent(Long id);

    Student findByNameAndId(String name,Long id);
    Student findByEmail(String email);
}