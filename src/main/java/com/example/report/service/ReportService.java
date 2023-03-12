package com.example.report.service;

import com.example.report.dto.ReportStatsDto;
import com.example.report.dto.RequestReport;
import com.example.report.dto.SimpleResponse;
import com.example.report.service.impl.ReportResponse;
import org.springframework.http.ResponseEntity;

public interface ReportService {

    ResponseEntity<?> update(Long reportId, RequestReport requestReport);

    ResponseEntity<?> save(RequestReport requestReport);

    ResponseEntity<?> listReports();

    SimpleResponse changeStatusOfComplaint(Long complaintId, String status);

    ResponseEntity<?> delete(Long reportId);

    ReportResponse getById(Long reportId);

    ReportStatsDto getReportStatistics();
}