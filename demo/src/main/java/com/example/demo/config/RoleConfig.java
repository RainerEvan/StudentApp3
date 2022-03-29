// package com.example.demo.config;

// import java.util.List;

// import com.example.demo.entity.ERole;
// import com.example.demo.entity.Role;
// import com.example.demo.repository.RoleRepository;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class RoleConfig {
//     @Bean
//     CommandLineRunner commandLineRunner(RoleRepository repository){
//         return args -> {
//             Role user = new Role(ERole.ROLE_USER);
//             Role admin = new Role(ERole.ROLE_ADMIN);

//             repository.saveAll(List.of(user,admin));
//         };
//     }
// }
