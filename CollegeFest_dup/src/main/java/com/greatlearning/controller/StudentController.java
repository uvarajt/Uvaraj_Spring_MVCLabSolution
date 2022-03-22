package com.greatlearning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greatlearning.entities.Student;
import com.greatlearning.services.StudentService;

@Controller
@RequestMapping("/participants")
public class StudentController {

	@Autowired
	private StudentService studentService;


	@RequestMapping("/list")
	public String listStudents(Model model) {
		List<Student> students = studentService.findAll();

		model.addAttribute("Students", students);

		return "list";
	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {
		Student student = new Student();
		model.addAttribute("Student", student);

		return "form";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("studentId") int id, Model model) {
		Student student = studentService.findById(id);
		model.addAttribute("Student", student);

		return "form";
	}

	@PostMapping("/save")
	public String saveStudent(@RequestParam("id") int id, @RequestParam("name") String name,
			@RequestParam("dept") String dept, @RequestParam("country") String country) {
		
		System.out.println("The Id : " + id);
		Student student;
		if (id != 0) {
			student = studentService.findById(id);
			student.setName(name);
			student.setDept(dept);
			student.setCountry(country);
		}
		else {
			student = new Student(name, dept, country);
		}
		studentService.save(student);
		
		return "redirect:/participants/list";
	}
	
	@RequestMapping("/delete")
	public String deleteStudent(@RequestParam("studentId") int id) {
		studentService.deletById(id);
		
		return "redirect:/participants/list";
	}
	
}
