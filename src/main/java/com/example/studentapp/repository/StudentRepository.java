package com.example.studentapp.repository;

import com.example.studentapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Correct method name for logi

        @Query("SELECT s FROM Student s WHERE " +
                "LOWER(REPLACE(s.name, ' ', '')) = :name " +
                "AND s.id = :id")
        Student findByNameAndIdNormalized(
                @Param("name") String name,
                @Param("id") long id
        );

    Student findByEmail(String email);
}