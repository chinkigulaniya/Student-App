package com.example.studentapp.controller;

import com.example.studentapp.model.Student;
import com.example.studentapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    //students.html

    @GetMapping("/students")
    public  String showStudentImage(){
        return "students";
    }

    // ----------------------------
    // 1️⃣ Student List Page
    // ----------------------------
    @GetMapping("/students/list")
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "student-list";  // -> student-list.html
    }

    // ----------------------------
    // 2️⃣ Add Student Page
    // ----------------------------
    @GetMapping("/students/add")
    public String showAddStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "add-student";  // -> add-student.html
    }

    // ----------------------------
    // 3️⃣ Save New Student
    // ----------------------------
    @PostMapping("/students/save")
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentService.save(student);
        return "redirect:/students";
    }

    // ----------------------------
    // 4️⃣ Edit Page
    // ----------------------------
    @GetMapping("/students/edit/{id}")
    public String editStudentPage(@PathVariable Long id, Model model) {
        Student s = studentService.getStudentById(id);
        model.addAttribute("student", s);
        return "edit-student";   // -> edit-student.html
    }

    // ----------------------------
    // 5️⃣ Update Student
    // ----------------------------

    @PostMapping("/students/update/{id}")
    public String updateStudent(@PathVariable Long id,
                                @ModelAttribute("student") Student studentForm) {

        Student existing = studentService.getStudentById(id);

        existing.setName(studentForm.getName());
        existing.setEmail(studentForm.getEmail());
        existing.setPhone(studentForm.getPhone());
        existing.setCourse(studentForm.getCourse());
        existing.setGender(studentForm.getGender());
        existing.setDob(studentForm.getDob()); // ✔ small letter
        existing.setEnrollmentDate(studentForm.getEnrollmentDate()); // ✔ small letter
        existing.setNotes(studentForm.getNotes());
        existing.setAddress(studentForm.getAddress());

        studentService.save(existing);

        return "redirect:/students";
    }


    // ----------------------------
    // 6️⃣ Delete Student
    // ----------------------------
    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}