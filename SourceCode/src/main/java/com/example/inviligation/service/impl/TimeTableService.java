package com.example.inviligation.service.impl;

import com.example.inviligation.model.TimeTable;
import com.example.inviligation.repository.ITimeTableRepository;
import com.example.inviligation.service.ITimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeTableService implements ITimeTableService {

    @Autowired
    private ITimeTableRepository tableRepository;

    @Override
    public List<TimeTable> all() {
        return tableRepository.findAll();
    }

    @Override
    public TimeTable getById(Long id) {
        return tableRepository.findById(id).get();
    }

    @Override
    public TimeTable add(TimeTable faculty) {
        return tableRepository.save(faculty);
    }

    @Override
    public TimeTable update(TimeTable faculty) {
        return tableRepository.save(faculty);
    }

    @Override
    public void delete(Long id) {
        tableRepository.deleteById(id);
    }

    @Override
    public TimeTable getByDateAndFromTimeAndToTime(String date, String fromTime, String toTime) {
        return tableRepository.findByDateAndFromTimeAndToTime(date, fromTime, toTime);
    }
}
