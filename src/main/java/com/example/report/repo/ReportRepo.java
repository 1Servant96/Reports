package com.example.report.repo;

import com.example.report.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepo extends JpaRepository<Report,Long> {
}
