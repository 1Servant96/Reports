package com.example.report.service.impl;

import com.example.report.dto.RequestReport;
import com.example.report.entities.Publication;
import com.example.report.entities.Report;
import com.example.report.entities.ReportDate;
import com.example.report.repo.PublicationRepo;
import com.example.report.repo.ReportDateRepo;
import com.example.report.repo.ReportRepo;
import com.example.report.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private final PublicationRepo publicationRepo;
    private  final ReportDateRepo reportDateRepo;
    private final ReportRepo reportRepo;
    private final RequestReportRepoImpl requestReportRepo;

    public ReportServiceImpl(PublicationRepo publicationRepo, ReportDateRepo reportDateRepo, ReportRepo reportRepo,
                             RequestReportRepoImpl requestReportRepo) {
        this.publicationRepo = publicationRepo;
        this.reportDateRepo = reportDateRepo;
        this.reportRepo = reportRepo;

        this.requestReportRepo = requestReportRepo;
    }

    @Override
    public ResponseEntity<?> save(RequestReport requestReport) {
        Publication publication = new Publication();
        publication.setText(requestReport.getText());
        publication = publicationRepo.save(publication);

        ReportDate reportDate = new ReportDate();
        reportDate.setCreateDate(requestReport.getCreatedDate());
        Calendar calendar = Calendar.getInstance();
        calendar.set(2100,0,1);
        reportDate.setEndDate(LocalDate.now());
        reportDate = reportDateRepo.save(reportDate);

        Report report = new Report();
        report.setUserId(requestReport.getUserId());
        report.setReportDate(reportDate);
        report.setPublication(publication);
        report = reportRepo.save(report);


        requestReport.setReportId(report.getId());
        requestReport.setCreatedDate(reportDate.getCreateDate());

        return ResponseEntity.ok(requestReport);
    }

    @Override
    public ResponseEntity<?> listReports() {
        List<RequestReport> requestReportList = requestReportRepo.getList();
        return ResponseEntity.ok(requestReportList);
    }
}
