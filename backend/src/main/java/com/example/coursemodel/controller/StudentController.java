package com.example.coursemodel.controller;

import com.example.coursemodel.model.Student;
import com.example.coursemodel.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentRepository repo;

    @GetMapping
    public List<Student> findAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Student getById(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> 
            new RuntimeException("Student not found: " + id));
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return repo.save(student);
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @RequestBody Student student) {
        student.setId(id);
        return repo.save(student);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}