package com.example.coursemodel.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private Integer number;

    private Double cost;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @OneToMany(
        mappedBy = "course",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Enrollment> enrollments = new ArrayList<>();

    public long countUniqueStudents() {
        return enrollments.stream()
            .map(Enrollment::getStudent)
            .distinct()
            .count();
    }
}