package com.example.inviligation.service;

import com.example.inviligation.model.Faculty;
import com.example.inviligation.model.TimeTable;

import java.util.List;

public interface ITimeTableService {

    List<TimeTable> all();
    TimeTable getById(Long id);
    TimeTable add(TimeTable faculty);
    TimeTable update(TimeTable faculty);
    void delete(Long id);

    TimeTable getByDateAndFromTimeAndToTime(String date,String fromTime,String toTime);
}
