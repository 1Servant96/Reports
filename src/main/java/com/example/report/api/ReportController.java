package com.example.report.api;

import com.example.report.dto.RequestReport;
import com.example.report.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveReport(@RequestBody RequestReport requestReport) {
        return ResponseEntity.ok(reportService.save(requestReport));
    }

    @GetMapping("/list")
    public  ResponseEntity<?> getListReports(){
        return ResponseEntity.ok(reportService.listReports());
    }

}
