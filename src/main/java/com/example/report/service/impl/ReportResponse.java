package com.example.report.service.impl;

import com.example.report.dto.UserResponse;
import com.example.report.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ReportResponse {
    private Long id;
    private String text;
    private String reportStatus;
    private UserResponse user;
    private Duration timeSolve;
}
