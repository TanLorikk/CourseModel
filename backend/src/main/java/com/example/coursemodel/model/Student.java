package com.example.coursemodel.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;

    private String phone;

    private String email;

    @Column(name = "record_number")
    private Integer recordNumber;

    private Double averagePerformance;

    @OneToMany(
        mappedBy = "student",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Enrollment> enrollments = new ArrayList<>();

    public void recalcAveragePerformance() {
        this.averagePerformance = enrollments.stream()
            .mapToDouble(Enrollment::getAverageGrade)
            .average()
            .orElse(0.0);
    }
}