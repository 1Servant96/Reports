package com.example.report.repo;

import com.example.report.entities.ReportDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportDateRepo extends JpaRepository<ReportDate,Long> {
}
