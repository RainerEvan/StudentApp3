package com.example.demo.controller;

import java.util.List;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping()
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    @GetMapping(path = "/{studentId}")
    public Student getStudentById(@PathVariable("studentId") Long studentId){
        return studentService.findStudentById(studentId);
    }

    @GetMapping(path = "/images/{studentId}")
    public byte[] getPhoto(@PathVariable("studentId") Long studentId) throws Exception{
        return studentService.getPhoto(studentId);
    }

    @PostMapping(path = "/new")
    public void registerNewStudent(@RequestParam("imageUrl") MultipartFile file, @RequestParam("student") String student) throws JsonMappingException, JsonProcessingException{
        studentService.addNewStudent(file, student);
    }

    @PutMapping(path ="/update")
    public void updateStudent(@RequestBody Student student){
        studentService.updateStudent(student);
    }

    @DeleteMapping(path = "/del/{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }
}
