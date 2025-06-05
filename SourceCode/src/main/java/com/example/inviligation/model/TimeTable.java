package com.example.inviligation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class TimeTable implements Comparable<TimeTable> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String facultyName;
    private String date;
    private Integer roomNumber;
    private String fromTime;
    private String toTime;

    @Override
    public int compareTo(TimeTable o) {
        return roomNumber.compareTo(o.roomNumber);
    }
}
