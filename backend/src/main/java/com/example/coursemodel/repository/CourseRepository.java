package com.example.coursemodel.repository;

import com.example.coursemodel.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}