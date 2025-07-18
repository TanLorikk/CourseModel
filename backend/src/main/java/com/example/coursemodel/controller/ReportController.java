package com.example.coursemodel.controller;

import com.example.coursemodel.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<byte[]> downloadReport() throws IOException {
        var rows = reportService.buildReport();
        var wb = new XSSFWorkbook();
        var sheet = wb.createSheet("Report");
        var header = sheet.createRow(0);
        header.createCell(0).setCellValue("Professor");
        header.createCell(1).setCellValue("Students Count");
        header.createCell(2).setCellValue("Average Grade");

        for (int i = 0; i < rows.size(); i++) {
            var r = rows.get(i);
            var row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(r.getProfessor());
            row.createCell(1).setCellValue(r.getStudentsCount());
            row.createCell(2).setCellValue(r.getAvgGrade());
        }

        try (var bos = new ByteArrayOutputStream()) {
            wb.write(bos);
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bos.toByteArray());
        }
    }
}