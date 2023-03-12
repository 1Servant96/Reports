package com.example.report.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RequestReport {
    Long reportId;
    Long userId;
    String text;
    LocalDate createdDate;

}
