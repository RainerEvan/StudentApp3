// package com.example.demo.student;

// import java.time.LocalDate;
// import java.time.Month;
// import java.util.List;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class StudentConfig {
    
//     @Bean
//     CommandLineRunner commandLineRunner(StudentRepository repository){
//         return args -> {
//             Student rainer = new Student("Rainer", "rainer@gmail.com", LocalDate.of(2001, Month.MARCH, 26));
//             Student evan = new Student("Evan", "evan@gmail.com", LocalDate.of(1998, Month.OCTOBER, 26));

//             repository.saveAll(List.of(rainer,evan));
//         };
//     }
// }
