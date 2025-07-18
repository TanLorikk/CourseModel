package com.example.coursemodel.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "enrollments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    
    @ElementCollection
    @CollectionTable(
        name = "enrollment_grades",
        joinColumns = @JoinColumn(name = "enrollment_id")
    )
    @Column(name = "grade")
    private List<Double> grades = new ArrayList<>();

   
    @Transient
    public double getAverageGrade() {
        return grades.stream()
            .mapToDouble(Double::doubleValue)
            .average()
            .orElse(0.0);
    }

    
    @Transient
    public double getFinalGrade() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        return grades.get(grades.size() - 1);
    }
}