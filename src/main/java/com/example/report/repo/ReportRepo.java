package com.example.report.repo;

import com.example.report.entities.Report;
import com.example.report.entities.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepo extends JpaRepository<Report,Long> {
    long countReportByReportStatus(ReportStatus reportStatus);
    List<Report> findReportByReportStatus(ReportStatus reportStatus);

}
