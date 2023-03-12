package com.example.report.dto;

import lombok.*;

import java.time.Duration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReportStatsDto {
    private long uncheckedCount;
    private long pendingCount;
    private long completedCount;
//    private long rejectedCount;
//    private double averageTime;

//    private int reportCount;
//    private Long currentPeriodPerDay;
//    private Long previousPeriodPerDay;
//    private Long currentPeriodPerMonth;
//    private Long previousPeriodPerMonth;
//    private Long currentPeriodPerYear;
//    private Long previousPeriodPerYear;
}
