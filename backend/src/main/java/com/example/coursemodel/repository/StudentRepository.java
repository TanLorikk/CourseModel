package com.example.coursemodel.repository;

import com.example.coursemodel.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}