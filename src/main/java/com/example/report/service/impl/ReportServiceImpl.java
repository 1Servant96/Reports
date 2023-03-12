package com.example.report.service.impl;

import com.example.report.dto.ReportStatsDto;
import com.example.report.dto.RequestReport;
import com.example.report.dto.SimpleResponse;
import com.example.report.entities.Publication;
import com.example.report.entities.Report;
import com.example.report.entities.ReportDate;
import com.example.report.entities.ReportStatus;
import com.example.report.repo.PublicationRepo;
import com.example.report.repo.ReportDateRepo;
import com.example.report.repo.ReportRepo;
import com.example.report.repo.UserRepository;
import com.example.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final PublicationRepo publicationRepo;
    private final ReportDateRepo reportDateRepo;
    private final ReportRepo reportRepo;
    private final RequestReportRepoImpl requestReportRepo;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> save(RequestReport requestReport) {
        Publication publication = new Publication();
        publication.setText(requestReport.getText());
        publication = publicationRepo.save(publication);

        ReportDate reportDate = new ReportDate();
        reportDate.setCreateDate(LocalDate.now());
        Calendar calendar = Calendar.getInstance();
        calendar.set(2100, 0, 1);
        reportDate.setEndDate(LocalDate.now());
        reportDate = reportDateRepo.save(reportDate);

        Report report = new Report();
        report.setUserId(requestReport.getUserId());
        report.setReportDate(reportDate);
        report.setPublication(publication);
        reportRepo.save(report);

        return ResponseEntity.ok(requestReport);
    }

    @Override
    public ResponseEntity<?> update(Long reportId, RequestReport requestReport) {
        Report report = reportRepo.findById(reportId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Report not found!"));
        report.setUserId(requestReport.getUserId());
        Publication publication = new Publication();
        publication.setText(requestReport.getText());
        report.setPublication(publication);
        reportRepo.save(report);
        return ResponseEntity.ok(requestReport);
    }

    @Override
    public ResponseEntity<?> listReports() {
        List<RequestReport> requestReportList = requestReportRepo.getList();
        return ResponseEntity.ok(requestReportList);
    }

    @Override
    public SimpleResponse changeStatusOfComplaint(Long complaintId, String status) {
        Report complaint = reportRepo.findById(complaintId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Complaint not found!"));
        if (!status.isBlank()) {
            if (status.contentEquals("In progress")) {
                complaint.setReportStatus(ReportStatus.IN_PROGRESS);
            } else if (status.contentEquals("Resolved")) {
                complaint.setReportStatus(ReportStatus.RESOLVED);
            } else if (status.contentEquals("Is awaiting a decision")) {
                complaint.setReportStatus(ReportStatus.IS_AWAITING_A_DECISION);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Please write In progress, Resolved or Is awaiting a decision");
            }
        }
        reportRepo.save(complaint);
        return new SimpleResponse("Status successfully changed!");
    }

    @Override
    public ResponseEntity<?> delete(Long reportId) {
        reportRepo.deleteById(reportId);
        return ResponseEntity.ok("Successfully deleted!");
    }

    @Override
    public ReportResponse getById(Long reportId) {
        ReportResponse reportResponse = new ReportResponse();
        Report report = reportRepo.findById(reportId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Report not found!"));
        reportResponse.setReportStatus(report.getReportStatus().toString());
        reportResponse.setUser(userRepository.findById(report.getUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!")));
        reportResponse.setText(report.getPublication().getText());
        Duration duration = Duration.between(report.getReportDate().getCreateDate(), report.getReportDate().getFinishDate());
        reportResponse.setTimeSolve(duration);
        return reportResponse;
    }

    @Override
    public ReportStatsDto getReportStatistics() {
        long uncheckedCount = reportRepo.countReportByReportStatus(ReportStatus.IS_AWAITING_A_DECISION);
        long pendingCount = reportRepo.countReportByReportStatus(ReportStatus.IN_PROGRESS);
        long completedCount = reportRepo.countReportByReportStatus(ReportStatus.RESOLVED);

        return new ReportStatsDto(uncheckedCount, pendingCount, completedCount);
    }

}
