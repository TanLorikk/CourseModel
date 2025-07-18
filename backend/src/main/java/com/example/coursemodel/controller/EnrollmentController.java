package com.example.coursemodel.controller;

import com.example.coursemodel.model.Enrollment;
import com.example.coursemodel.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentRepository repo;

    @GetMapping
    public List<Enrollment> findAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Enrollment getById(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> 
            new RuntimeException("Enrollment not found: " + id));
    }

    @PostMapping
    public Enrollment create(@RequestBody Enrollment enrollment) {
        return repo.save(enrollment);
    }

    @PutMapping("/{id}")
    public Enrollment update(@PathVariable Long id, @RequestBody Enrollment enrollment) {
        enrollment.setId(id);
        return repo.save(enrollment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}