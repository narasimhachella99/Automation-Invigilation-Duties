package com.example.inviligation.service.impl;

import com.example.inviligation.model.Faculty;
import com.example.inviligation.repository.IFacultyRepository;
import com.example.inviligation.service.IFacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService implements IFacultyService {
    @Autowired
    private IFacultyRepository facultyRepository;

    @Override
    public List<Faculty> all() {
        return facultyRepository.findAll();
    }

    @Override
    public Faculty getById(Long id) {
        return facultyRepository.findById(id).get();
    }

    @Override
    public Faculty add(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty update(Faculty faculty) {
        return facultyRepository.save(faculty);
    }



    @Override
    public void delete(Long id) {
            facultyRepository.deleteById(id);
    }

    @Override
    public Faculty getByEmailAndPassword( String email,String password) {
        return facultyRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public List<Faculty> getByKeyword(String keyword) {
        return facultyRepository.findByKeyword(keyword);
    }

}
