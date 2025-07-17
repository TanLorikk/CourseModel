package com.example.coursemodel;

// Стартовый проект: Spring Boot + модели + CRUD + отчёт
// Автор: Лисейчиков Роман Олегович
// Email: farthouse12doge@gmail.com

// Предполагается, что ты используешь Maven или Gradle, Spring Boot 3.x
// Ниже — только основные классы и логика. Можно будет потом запустить и развернуть

// === application.properties ===
spring.datasource.url=jdbc:h2:mem:db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update

// === Стартовый класс ===
@SpringBootApplication
public class CourseModelApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseModelApplication.class, args);
    }
}

// === Модели ===
@Entity
public class Student {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private Integer recordBookNumber;
    private Float averageGrade;

    @OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments;
}

@Entity
public class Professor {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String address;
    private String phone;
    private Float salary;

    @OneToMany(mappedBy = "professor")
    private List<Course> courses;
}

@Entity
public class Course {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private Integer number;
    private Float cost;

    @ManyToOne
    private Professor professor;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments;
}

@Entity
public class Enrollment {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Course course;

    @ElementCollection
    private List<Float> grades;

    public float getCurrentAverage() {
        return grades == null || grades.isEmpty() ? 0f : (float) grades.stream().mapToDouble(Float::doubleValue).average().orElse(0);
    }

    public float getFinalGrade() {
        return getCurrentAverage(); // Или отдельная логика
    }
}

// === Репозитории ===
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {}
@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {}
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {}
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {}

// === Контроллеры ===
@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentRepository repo;
    public StudentController(StudentRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Student> getAll() { return repo.findAll(); }

    @PostMapping
    public Student create(@RequestBody Student s) { return repo.save(s); }
}

@RestController
@RequestMapping("/professors")
public class ProfessorController {
    private final ProfessorRepository repo;
    public ProfessorController(ProfessorRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Professor> getAll() { return repo.findAll(); }

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

// === Сервис отчёта ===
@Service
public class ReportService {
    private final ProfessorRepository profRepo;

    public ReportService(ProfessorRepository profRepo) {
        this.profRepo = profRepo;
    }

    public byte[] generateReport() throws IOException {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Professors");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ФИО профессора");
            header.createCell(1).setCellValue("Количество студентов");
            header.createCell(2).setCellValue("Средняя успеваемость");

            int rowIdx = 1;
            for (Professor p : profRepo.findAll()) {
                int totalStudents = 0;
                float avgGradeSum = 0;
                int gradeCount = 0;

                for (Course c : p.getCourses()) {
                    for (Enrollment e : c.getEnrollments()) {
                        totalStudents++;
                        avgGradeSum += e.getCurrentAverage();
                        gradeCount++;
                    }
                }

                float finalAvg = gradeCount == 0 ? 0 : avgGradeSum / gradeCount;

                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(p.getName());
                row.createCell(1).setCellValue(totalStudents);
                row.createCell(2).setCellValue(finalAvg);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            return out.toByteArray();
        }
    }
}

// === Контроллер для отчёта ===
@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;
    public ReportController(ReportService reportService) { this.reportService = reportService; }

    @GetMapping("/professors")
    public ResponseEntity<byte[]> getReport() throws IOException {
        byte[] xlsx = reportService.generateReport();
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=professors.xlsx")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(xlsx);
    }
}
