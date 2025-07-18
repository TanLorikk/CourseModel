package com.example.coursemodel.controller;

import com.example.coursemodel.model.Professor;
import com.example.coursemodel.repository.ProfessorRepository;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/professors")
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorRepository repo;

    @GetMapping
    public List<Professor> all() { return repo.findAll(); }

    @PostMapping
    public Professor create(@RequestBody Professor p) { return repo.save(p); }

    @PutMapping("/{id}")
    public Professor update(@PathVariable Long id, @RequestBody Professor p) {
        p.setId(id);
        return repo.save(p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repo.deleteById(id); }
}