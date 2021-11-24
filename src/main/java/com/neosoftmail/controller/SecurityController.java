package com.neosoftmail.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neosoftmail.model.Student;
import com.neosoftmail.service.StudentSecurityServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class SecurityController {
	@Autowired
    private StudentSecurityServiceImpl studentService;

    //@Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/student", method = RequestMethod.GET)
    public List listUser(){
        return studentService.findAll();
    }

    //@Secured("ROLE_USER")
    //@PreAuthorize("hasRole('USER')")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    @RequestMapping(value = "/student/{id}", method = RequestMethod.GET)
    public Student getOne(@PathVariable(value = "id")int id){
        return studentService.findById(id);
    }

    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public Student create(@RequestBody Student student){
        return studentService.save(student);
    }
	/*
	 * @PreAuthorize("hasRole('ADMIN')")
	 * 
	 * @RequestMapping(value="/user/{id}", method = RequestMethod.DELETE) public
	 * Student deleteUser(@PathVariable(value = "id") int id){
	 * studentService.delete(id); return new Student(id); }
	 */
	 

}
