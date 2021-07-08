package com.freespace.springboot.demoone.controllers;

import com.freespace.springboot.demoone.models.Student;
import com.freespace.springboot.demoone.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = {"/api/v1/students", "/students"})
public class StudentController {

    private final StudentService studentService;

    @Autowired
    StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Student registerStudent(@RequestBody Student student) {
        List<Student> studentList = studentService.registerStudent(student);
        return studentList.get(0);
    }

    @RequestMapping(value = "{studentId}", method = RequestMethod.DELETE)
    public Student deleteStudent(@PathVariable("studentId") long studentId) {
        Optional<Student> students = studentService.deleteStudent(studentId);
        return students.get();
    }

    @RequestMapping(value = "{studentId}", method = RequestMethod.PUT)
    public Student updateStudent(@PathVariable("studentId") long studentId,@RequestBody Student newStudent) {
        Student student = studentService.updateStudent(studentId,newStudent);
        return student;
    }
}
