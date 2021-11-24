package com.neosoftmail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import com.neosoftmail.model.Role;
import com.neosoftmail.model.Student;
import com.neosoftmail.repository.StudentRepository;import net.bytebuddy.utility.privilege.GetSystemPropertyAction;

@Service("studentService")
public class StudentSecurityServiceImpl implements  UserDetailsService,StudentService {
	
	@Autowired
	private StudentRepository studentRepo;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Student student = studentRepo.findByUsername(username);
		if(student == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(student.getUsername(), student.getPassword(), getAuthority(student));
	}

	
	private Set getAuthority(Student student) {
        Set authorities = new HashSet<>();
		student.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE" + ((Role) role).getName()));
		});
		return authorities;
	}

	@Override
	public List findAll() {
		List list = new ArrayList<>();
		studentRepo.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	public void delete(int id) {
		studentRepo.deleteById(id);
	}

	public Student findOne(String username) {
		return studentRepo.findByUsername(username);
	}

	@Override
	public Student findById(int id) {
		return studentRepo.findById(id).get();
	}
	@Override
	public Student save(Student student) {
		Student newStudent = new Student();
		newStudent.setStudId(student.getStudId());
		newStudent.setUsername(student.getUsername());
		newStudent.setPassword(bcryptEncoder.encode(student.getPassword()));
		newStudent.setFirstName(student.getFirstName());
		newStudent.setLastName(student.getLastName());
		newStudent.setMobNo(student.getMobNo());
		
        return studentRepo.save(newStudent);
    }

	

}
