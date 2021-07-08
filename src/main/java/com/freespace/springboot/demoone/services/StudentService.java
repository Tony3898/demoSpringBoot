package com.freespace.springboot.demoone.services;

import com.freespace.springboot.demoone.models.Student;
import com.freespace.springboot.demoone.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public List<Student> registerStudent(Student student) {
        Optional<Student> optionalStudent = studentRepository.findStudentByEmail(student.getEmail());
        if (optionalStudent.isPresent())
            throw new IllegalStateException("Email already exists!!!");
        return List.of(studentRepository.save(student));
    }

    public Optional<Student> deleteStudent(long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        Optional<Student> student = studentRepository.findById(studentId);
        if (!exists)
            throw new IllegalStateException("Student with ID " + studentId + " does not exist!!!");
        studentRepository.deleteById(studentId);
        return student;
    }

    @Transactional
    public Student updateStudent(long studentId, Student newStudent) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("Student with id " + studentId + "does not exists!!!"));
        if (newStudent.getName() != null && newStudent.getName().trim().length() > 0) {
            student.setName(newStudent.getName());
        }
        if (newStudent.getEmail() != null && newStudent.getEmail().trim().length() > 0) {
            Optional<Student> optionalStudent = studentRepository.findStudentByEmail(newStudent.getEmail());
            if(optionalStudent.isPresent()) throw new IllegalStateException("Student this email " + newStudent.getEmail() +" is already present");
            student.setEmail(newStudent.getEmail());
        }
        if (newStudent.getDob() != null) {
            student.setDob(newStudent.getDob());
        }
        studentRepository.save(student);
        return student;
    }
}
