package com.example.demo.student;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public Student findStudentById(Long studentId){
        return studentRepository.findStudentById(studentId).orElseThrow(()->new IllegalStateException("student with id "+ studentId +" does not exist"));
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        if(studentOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }
        
        studentRepository.save(student);
    }

    public Student updateStudent(Student student) {
        Student currStudent = studentRepository.findById(student.getId()).orElseThrow(()->new IllegalStateException("student with id "+ student.getId() +" does not exist"));

        if(student.getName() != null && student.getName().length()>0 && !Objects.equals(currStudent.getName(), student.getName())){
            currStudent.setName(student.getName());
        }
        
        if(student.getEmail()!= null && student.getEmail().length() > 0 && !Objects.equals(currStudent.getEmail(), student.getEmail())){
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
            if(studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            currStudent.setEmail(student.getEmail());
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
