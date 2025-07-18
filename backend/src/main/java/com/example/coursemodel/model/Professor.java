package com.example.coursemodel.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "professors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;

    private String phone;

    private Double salary;

    @OneToMany(
        mappedBy = "professor",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Course> courses = new ArrayList<>();
}