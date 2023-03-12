package com.example.report.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Publication publication;

    @OneToOne
    private ReportDate reportDate;

    @Enumerated(value = EnumType.STRING)
    private ReportStatus reportStatus = ReportStatus.IS_AWAITING_A_DECISION;

    private Long userId;
}
