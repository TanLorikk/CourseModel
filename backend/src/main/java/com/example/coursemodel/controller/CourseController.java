package com.example.coursemodel.controller;

import com.example.coursemodel.model.Course;
import com.example.coursemodel.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseRepository repo;

    @GetMapping
    public List<Course> findAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Course getById(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> 
            new RuntimeException("Course not found: " + id));
    }

    @PostMapping
    public Course create(@RequestBody Course course) {
        return repo.save(course);
    }

    @PutMapping("/{id}")
    public Course update(@PathVariable Long id, @RequestBody Course course) {
        course.setId(id);
        return repo.save(course);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}