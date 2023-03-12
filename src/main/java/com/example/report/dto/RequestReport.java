package com.example.report.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestReport {

    private Long userId;

    private String title;

    private String text;

    private String[] tag;

    private boolean like;

    private boolean dislike;

    private int rating;

    private List<String> images;

}
