package com.example.report.service.impl;

import com.example.report.dto.ReportStatsDto;
import com.example.report.dto.RequestReport;
import com.example.report.dto.SimpleResponse;
import com.example.report.dto.UserResponse;
import com.example.report.entities.*;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
        report.setUserId(userRepository.findById(requestReport.getUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!")));
        report.setReportDate(reportDate);
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
    public List<ReportResponse> getAllReports() {
        List<ReportResponse> getAll = new ArrayList<>();
        for (Report report : reportRepo.findAll()) {
            ReportResponse reportResponse = new ReportResponse();
            reportResponse.setId(report.getId());
            reportResponse.setText(report.getPublication().getText());
            reportResponse.setUser(userRepository.getUserById(report.getUserId().getId()));
            getAll.add(reportResponse);
        }
        return getAll;
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
        UserResponse userResponse = new UserResponse();
        User user = report.getUserId();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setImage(user.getImage());
        userResponse.setEmail(user.getEmail());
        reportResponse.setUser(userResponse);
        reportResponse.setText(report.getPublication().getText());
//        Duration duration = Duration.between(report.getReportDate().getCreateDate(), report.getReportDate().getFinishDate());
//        reportResponse.setTimeSolve(duration);
        return reportResponse;
    }

    @Override
    public ReportStatsDto getReportStatistics() {
        long uncheckedCount = reportRepo.countReportByReportStatus(ReportStatus.IS_AWAITING_A_DECISION);
        long pendingCount = reportRepo.countReportByReportStatus(ReportStatus.IN_PROGRESS);
        long completedCount = reportRepo.countReportByReportStatus(ReportStatus.RESOLVED);

        return new ReportStatsDto(uncheckedCount, pendingCount, completedCount);
    }

    @Override
    public SimpleResponse updateReport(Long reportId, Long userId, RequestReport requestReport, boolean like, boolean dislike) {
        Report report = reportRepo.findById(reportId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Feedback not found!"));
        Publication publication = new Publication();
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        if (user == report.getUserId()) {
            if (requestReport.getText() != null) {
                publication.setText(requestReport.getText());
                report.setPublication(publication);
            }

            if (requestReport.getRating() != 0) {
                report.setRating(requestReport.getRating());
            }
            if (requestReport.getImages() != null) {
                report.setImages(requestReport.getImages());
            }
        }
        if (like) {
            liking(reportId, user.getId());
        }
        if (dislike) {
            disLiking(reportId, user.getId());
        }
        reportRepo.save(report);
        return new SimpleResponse("Report successfully updated!");
    }

    private void liking(Long reportId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        Report report = reportRepo.findById(reportId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not found!"));
        if (report.getLikes().containsKey(user.getId())) {
            report.setLike(report.getLike() - 1);
            report.getLikes().remove(user.getId());
            reportRepo.save(report);
        } else {
            report.getLikes().put(user.getId(), true);
            report.setLike(report.getLike() + 1);
            reportRepo.save(report);
        }
    }

    private void disLiking(Long reportId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        Report report = reportRepo.findById(reportId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not found!"));
        if (report.getDislikes().containsKey(user.getId())) {
            report.setDislike(report.getDislike() - 1);
            report.getDislikes().remove(user.getId());
            reportRepo.save(report);
        } else {
            report.getDislikes().put(user.getId(), true);
            report.setDislike(report.getDislike() + 1);
            reportRepo.save(report);
        }
    }

}
