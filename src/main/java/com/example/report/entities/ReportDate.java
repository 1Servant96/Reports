package com.example.report.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reports_date")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_gen")
    @SequenceGenerator(name = "product_gen", sequenceName = "product_seq", allocationSize = 1)
    private Long id;

    private LocalDate createDate;

    private LocalDate endDate;

    private LocalDate finishDate;
}
