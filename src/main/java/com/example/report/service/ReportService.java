package com.example.report.service;

import com.example.report.dto.RequestReport;
import org.springframework.http.ResponseEntity;

public interface ReportService {

    ResponseEntity<?> save(RequestReport requestReport);

    ResponseEntity<?> listReports();
}