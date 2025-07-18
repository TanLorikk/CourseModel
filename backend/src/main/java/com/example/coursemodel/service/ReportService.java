package com.example.coursemodel.service;

import com.example.coursemodel.repository.*;
import com.example.coursemodel.model.*;
import lombok.*;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.*;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ProfessorRepository profRepo;

    @Data @AllArgsConstructor
    public static class ReportRow {
        private String professor;
        private long studentsCount;
        private double avgGrade;
    }

    public List<ReportRow> buildReport() {
        return profRepo.findAll().stream()
            .map(p -> {
                var grades = p.getCourses().stream()
                    .flatMap(c -> c.getEnrollments().stream())
                    .map(Enrollment::getGrade)
                    .mapToDouble(Double::doubleValue);
                long distinctStudents = p.getCourses().stream()
                    .flatMap(c -> c.getEnrollments().stream())
                    .map(Enrollment::getStudent)
                    .distinct()
                    .count();
                return new ReportRow(p.getName(), distinctStudents, grades.average().orElse(0));
            })
            .collect(Collectors.toList());
    }
}