package com.example.studentapp.service;

import com.example.studentapp.model.Student;
import com.example.studentapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    // ----------- FIXED LOGIN METHOD 100% WORKING ----------
    @Override
    public Student findByNameAndId(String name, Long id) {

        if (name == null || id == null) return null;
      String normalized=name.toLowerCase().replaceAll("\\s+","");
        return studentRepository.findByNameAndIdNormalized(normalized, id);
    }


    @Override
    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }
}