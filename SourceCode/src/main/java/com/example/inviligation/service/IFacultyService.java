package com.example.inviligation.service;

import com.example.inviligation.model.Faculty;

import java.util.List;

public interface IFacultyService {
    List<Faculty> all();
    Faculty getById(Long id);
    Faculty add(Faculty faculty);
    Faculty update(Faculty faculty);

    void delete(Long id);
    Faculty getByEmailAndPassword(String email,String password);

    List<Faculty> getByKeyword(String keyword);
}
