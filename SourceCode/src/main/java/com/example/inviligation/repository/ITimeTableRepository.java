package com.example.inviligation.repository;

import com.example.inviligation.model.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITimeTableRepository extends JpaRepository<TimeTable,Long> {

        TimeTable findByDateAndFromTimeAndToTime(String date,String fromTime,String toTime);
}
