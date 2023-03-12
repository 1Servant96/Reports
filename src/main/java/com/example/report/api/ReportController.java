package com.example.report.api;

import com.example.report.dto.ReportStatsDto;
import com.example.report.dto.RequestReport;
import com.example.report.dto.SimpleResponse;
import com.example.report.service.ReportService;
import com.example.report.service.impl.ReportResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Report Api", description = "Report Api")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/save")
    public ResponseEntity<?> saveReport(@RequestBody RequestReport requestReport) {
        return ResponseEntity.ok(reportService.save(requestReport));
    }

    @GetMapping("/list")
    public  ResponseEntity<?> getListReports(){
        return ResponseEntity.ok(reportService.listReports());
    }

    @PostMapping("/{complaintId}")
    @Operation(summary = "Change status", description = "This endpoint changed status you can write 1) In progress 2) Resolved 3) Is awaiting a decision")
    public SimpleResponse changeStatusOfComplaint(@PathVariable Long complaintId, @RequestParam String status) {
        return reportService.changeStatusOfComplaint(complaintId, status);
    }

    @GetMapping("/{reportId}")
    public ReportResponse getById(@PathVariable Long reportId) {
        return reportService.getById(reportId);
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<?> deleteReport(@PathVariable Long reportId) {
        return reportService.delete(reportId);
    }

     @PutMapping("/{reportId}")
    public ResponseEntity<?> updateReport(@PathVariable Long reportId, @RequestBody RequestReport requestReport) {
        return reportService.update(reportId, requestReport);
     }
     @GetMapping("/statistics")
    public ReportStatsDto statistics (){
        return reportService.getReportStatistics();
     }


}
