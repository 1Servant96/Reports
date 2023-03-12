package com.example.report.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "publications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Publication {

    @Id
    @SequenceGenerator(name = "publication_gen", sequenceName = "publication_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String text;
}
