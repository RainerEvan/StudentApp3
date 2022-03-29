package com.example.demo.service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.ServletContext;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ServletContext context;

    @Autowired
    public StudentService(StudentRepository studentRepository, ServletContext context) {
        this.studentRepository = studentRepository;
        this.context = context;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public byte[] getPhoto(Long id) throws IOException{
        Student student = studentRepository.findById(id).get();
        return Files.readAllBytes(Paths.get(context.getRealPath("/Images/")+student.getImageUrl()));
    }

    public Student findStudentById(Long studentId){
        return studentRepository.findStudentById(studentId).orElseThrow(()->new IllegalStateException("Student with id "+ studentId +" does not exist"));
    }

    public void addNewStudent(MultipartFile file, String studentString) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Student student = mapper.readValue(studentString, Student.class);
        boolean isExist = new File(context.getRealPath("/Images/")).exists();

        if(!isExist){
            new File(context.getRealPath("/Images/")).mkdir();
        }

        String filename = file.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
        File serverFile = new File(context.getRealPath("/Images/"+File.separator+newFileName));

        try {
            FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        if(studentOptional.isPresent()){
            throw new IllegalStateException("Email taken");
        }
        
        student.setImageUrl(newFileName);
        studentRepository.save(student);
    }

    public Student updateStudent(Student student) {
        Student currStudent = studentRepository.findById(student.getId()).orElseThrow(()->new IllegalStateException("Student with id "+ student.getId() +" does not exist"));

        if(student.getName() != null && student.getName().length()>0 && !Objects.equals(currStudent.getName(), student.getName())){
            currStudent.setName(student.getName());
        }
        
        if(student.getEmail()!= null && student.getEmail().length() > 0 && !Objects.equals(currStudent.getEmail(), student.getEmail())){
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
            if(studentOptional.isPresent()){
                throw new IllegalStateException("Email taken");
            }
            currStudent.setEmail(student.getEmail());
        }

        if(student.getDob()!=null && !Objects.equals(currStudent.getDob(), student.getDob())){
            currStudent.setDob(student.getDob());
        }
        
        return studentRepository.save(currStudent);
    }
    
    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            throw new IllegalStateException("Student with id "+studentId+" does not exists");
        }
        studentRepository.deleteById(studentId);
    }

}
