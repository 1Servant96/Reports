package com.example.report.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @OneToOne(optional = false)
    @JoinColumn(name = "post_id", nullable = false)

    private Post post;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    private boolean status = false;

}
