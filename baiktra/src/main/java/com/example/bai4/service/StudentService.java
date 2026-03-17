package com.example.bai4.service;

import com.example.bai4.model.Role;
import com.example.bai4.model.Student;
import com.example.bai4.repository.RoleRepository;
import com.example.bai4.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerStudent(String username, String password, String email) {
        Student student = new Student();
        student.setUsername(username);
        student.setPassword(passwordEncoder.encode(password));
        student.setEmail(email);

        Role studentRole = roleRepository.findByName("STUDENT")
                .orElseThrow(() -> new RuntimeException("Role STUDENT not found"));
        Set<Role> roles = new HashSet<>();
        roles.add(studentRole);
        student.setRoles(roles);

        studentRepository.save(student);
    }

    public boolean existsByUsername(String username) {
        return studentRepository.existsByUsername(username);
    }

    public Optional<Student> findByUsername(String username) {
        return studentRepository.findByUsername(username);
    }

    public Optional<Student> findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }
}
