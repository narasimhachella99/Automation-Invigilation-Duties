package com.example.inviligation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@ToString @Builder @Setter @Getter
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private Long phoneNumber;
    private String dept;
    private Integer roomNumber;
    private boolean status=false;

    private LocalDate date;
    private String fromTime;
    private String toTime;

}