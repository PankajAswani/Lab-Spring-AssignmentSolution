package com.greatlearning.studentManagement.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.greatlearning.studentManagement.entity.Student;
import com.greatlearning.studentManagement.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
	@Autowired
	private StudentService studentService;
	
	@RequestMapping("/list")
	public String listStudents(Model theModel) {
		List<Student> students = studentService.findAll();
		theModel.addAttribute("Students", students);
		
		return "student-list";
	}
	
	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		Student student = new Student();
		theModel.addAttribute("Student", student);
		
		return "student-form";
	}
	
	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("studentId") int stdentId, Model theModel) {
		Student student = studentService.findById(stdentId);
		theModel.addAttribute("Student", student);
		
		return "student-form";
	}
	
	@PostMapping("/save")
	public String saveStudent(@RequestParam("studentId") int studentId, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("course") String course, @RequestParam("country") String country) {
		Student student;
		
		if (studentId != 0) {
			student = studentService.findById(studentId);
		} else {
			student = new Student();
		}
		
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setCourse(course);
		student.setCountry(country);
		
		studentService.save(student);
		
		return "redirect:/student/list";
	}
	
	@RequestMapping("/delete")
	public String deleteStudent(@RequestParam("studentId") int studentId) {
		studentService.delete(studentId);
		return "redirect:/student/list";
	}
	
	@RequestMapping("/403")
	public ModelAndView accessDenied(Principal user) {
		ModelAndView model = new ModelAndView();
		
		if (user != null) {
			model.addObject("message", "Hi " + user.getName() + ", you do not have permission to access this page");
		} else {
			model.addObject("message", "You do not have permission to access this page");
		}
		
		model.setViewName("403");
		return model;
	}
}