package com.example.report.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

import static javax.persistence.CascadeType.*;

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

    @ManyToOne(cascade = {REFRESH, MERGE, DETACH})
    private User userId;

    private int rating;

    @Column(name = "likes")
    private Long like;
    @Column(name = "dislike")
    private Long dislike;
    @ElementCollection
    private Map<Long, Boolean> likes;
    @ElementCollection
    private Map<Long, Boolean> dislikes;
    @ElementCollection
    private List<String> images;
}
