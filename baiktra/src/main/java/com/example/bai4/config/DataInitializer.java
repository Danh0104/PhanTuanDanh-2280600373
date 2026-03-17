package com.example.bai4.config;

import com.example.bai4.model.Role;
import com.example.bai4.model.Student;
import com.example.bai4.repository.RoleRepository;
import com.example.bai4.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (roleRepository.findByName("ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);
        }
        if (roleRepository.findByName("STUDENT").isEmpty()) {
            Role studentRole = new Role();
            studentRole.setName("STUDENT");
            roleRepository.save(studentRole);
        }

        if (studentRepository.findByUsername("admin").isEmpty()) {
            Student admin = new Student();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@example.com");
            Role adminRole = roleRepository.findByName("ADMIN").get();
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            admin.setRoles(roles);
            studentRepository.save(admin);
        }
    }
}
