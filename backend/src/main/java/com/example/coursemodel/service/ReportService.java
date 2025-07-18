package com.example.coursemodel.service;

import com.example.coursemodel.model.Enrollment;
import com.example.coursemodel.repository.ProfessorRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ProfessorRepository profRepo;

    @Data
    @AllArgsConstructor
    public static class ReportRow {
        private String professor;
        private long studentsCount;
        private double avgGrade;
    }

    public List<ReportRow> buildReport() {
        return profRepo.findAll().stream()
            .map(p -> {
                // 1. Считаем уникальных студентов
                long studentsCount = p.getCourses().stream()
                    .flatMap(c -> c.getEnrollments().stream())
                    .map(Enrollment::getStudent)
                    .distinct()
                    .count();

                // 2. Собираем все оценки из всех enrollments
                DoubleSummaryStatistics stats = p.getCourses().stream()
                    .flatMap(c -> c.getEnrollments().stream())
                    .flatMap(e -> e.getGrades().stream())
                    .mapToDouble(Double::doubleValue)
                    .summaryStatistics();

                double avg = stats.getCount() > 0
                    ? stats.getAverage()
                    : 0.0;

                return new ReportRow(p.getName(), studentsCount, avg);
            })
            .collect(Collectors.toList());
    }
}