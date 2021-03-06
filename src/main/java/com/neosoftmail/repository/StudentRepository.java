package com.neosoftmail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neosoftmail.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByUsername(String username);

}