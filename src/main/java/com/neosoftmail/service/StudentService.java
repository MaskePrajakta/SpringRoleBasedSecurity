package com.neosoftmail.service;

import java.util.List;

import com.neosoftmail.model.Student;

public interface StudentService {

    Student save(Student student);
    List<Student> findAll();
    void delete(int id);
    Student findOne(String username);
	Student findById(int id);
}
