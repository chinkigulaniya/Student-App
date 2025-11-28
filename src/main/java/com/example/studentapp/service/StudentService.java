package com.example.studentapp.service;

import com.example.studentapp.model.Student;
import java.util.List;

public interface StudentService {

    Student addStudent(Student student);

    Student getStudentById(Long id);

    List<Student> getAllStudents();

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);
}