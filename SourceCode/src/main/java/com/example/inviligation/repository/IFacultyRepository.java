package com.example.inviligation.repository;

import com.example.inviligation.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFacultyRepository extends JpaRepository<Faculty,Long> {

    Faculty findByEmailAndPassword(String email,String password);
    @Query(value = "select * from automation.faculty where name like %:keyword% or date like %:keyword% "
            , nativeQuery = true)
    List<Faculty> findByKeyword(@Param("keyword") String keyword);
}
